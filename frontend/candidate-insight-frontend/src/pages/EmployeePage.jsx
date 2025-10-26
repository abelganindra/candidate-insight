import React, { useState, useEffect, useCallback } from "react";
import { X } from "lucide-react";

import api from "../api/api"; // Axios instance
import EntityForm from "../components/EntityForm";
import EntityTable from "../components/EntityTable";
import EntityUpload from "../components/EntityUpload";

const EmployeePage = () => {
  const entity = {
    name: "Employee",
    endpoint: "/employees",
    fields: [
      { name: "empno", label: "Employee No", required: true },
      { name: "empname", label: "Employee Name", required: true },
      { name: "tiercode", label: "Tier", type: "select", required: true },
      { name: "locationcode", label: "Location", type: "select", required: true },
      { name: "departmentcode", label: "Department", type: "select", required: true },
      { name: "supervisorcode", label: "Supervisor No" },
      { name: "salary", label: "Salary", type: "number" },
      { name: "entrydate", label: "Entry Date", type: "date" },
    ],
  };

  const [employees, setEmployees] = useState([]);
  const [editing, setEditing] = useState(null);
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [message, setMessage] = useState(null);

  // Dropdown data
  const [tiers, setTiers] = useState([]);
  const [locations, setLocations] = useState([]);
  const [departments, setDepartments] = useState([]);

  const showMessage = (text, type = "success") => {
    setMessage({ text, type });
    setTimeout(() => setMessage(null), 3000);
  };

  // Fetch dropdowns
  const fetchDropdowns = async () => {
    try {
      const [tierRes, locRes, deptRes] = await Promise.all([
        api.get("/tiers"),
        api.get("/locations"),
        api.get("/departments"),
      ]);
      setTiers(tierRes.data);
      setLocations(locRes.data);
      setDepartments(deptRes.data);
    } catch (err) {
      console.error("Dropdown fetch error:", err);
      showMessage("Failed to load dropdown data.", "error");
    }
  };

  // Fetch employees
  const fetchEmployees = useCallback(async () => {
    setIsLoading(true);
    try {
      const res = await api.get(entity.endpoint);
      setEmployees(res.data);
    } catch (err) {
      console.error("Fetch employees error:", err);
      showMessage("Failed to fetch employees.", "error");
    } finally {
      setIsLoading(false);
    }
  }, [entity.endpoint]);

  useEffect(() => {
    fetchDropdowns();
    fetchEmployees();
  }, [fetchEmployees]);

  const handleSubmit = async (data) => {
    setIsLoading(true);
    try {
      if (editing) {
        await api.put(`${entity.endpoint}/${editing.empno}`, data);
        showMessage(`Employee ${editing.empno} updated successfully!`);
      } else {
        await api.post(entity.endpoint, data);
        showMessage("New employee created successfully!");
      }
      setEditing(null);
      setIsFormVisible(false);
      fetchEmployees();
    } catch (err) {
      console.error("Save employee error:", err);
      showMessage("Failed to save employee.", "error");
    } finally {
      setIsLoading(false);
    }
  };

  const handleEdit = (row) => {
    setEditing(row);
    setIsFormVisible(true);
  };

  const handleAddNew = () => {
    setEditing(null);
    setIsFormVisible(true);
  };

  const handleCancelForm = () => {
    setEditing(null);
    setIsFormVisible(false);
  };

  const handleDelete = async (row) => {
    if (!window.confirm(`Are you sure you want to delete employee ${row.empname} (${row.empno})?`))
      return;

    setIsLoading(true);
    try {
      await api.delete(`${entity.endpoint}/${row.empno}`);
      showMessage(`Employee ${row.empno} deleted successfully.`);
      fetchEmployees();
    } catch (err) {
      console.error("Delete employee error:", err);
      showMessage("Failed to delete employee.", "error");
    } finally {
      setIsLoading(false);
    }
  };

  // Enhance fields with dropdown options
  const fieldsWithOptions = entity.fields.map((f) => {
    if (f.name === "tiercode") return { ...f, options: tiers.map(t => ({ value: t.tiercode, label: t.tiername })) };
    if (f.name === "locationcode") return { ...f, options: locations.map(l => ({ value: l.locationcode, label: l.locationname })) };
    if (f.name === "departmentcode") return { ...f, options: departments.map(d => ({ value: d.departmentcode, label: d.departmentname })) };
    return f;
  });

  return (
    <div className="min-h-screen bg-gray-100 p-4 sm:p-6 lg:p-10 font-sans">
      <div className="max-w-7xl mx-auto space-y-8">
        {/* Header */}
        <header className="py-4 border-b border-gray-200">
          <h1 className="text-3xl sm:text-4xl font-extrabold text-gray-900 tracking-tight">
            👨‍💼 Employee Management System
          </h1>
          <p className="mt-2 text-lg text-gray-500">
            Create, manage, and track your employees.
          </p>
        </header>

        {isLoading && (
          <div className="flex items-center justify-center p-4 bg-yellow-100 border border-yellow-300 text-yellow-800 rounded-lg">
            Loading...
          </div>
        )}

        {message && (
          <div
            className={`p-4 rounded-lg shadow-lg flex justify-between items-center ${
              message.type === "success"
                ? "bg-green-100 border border-green-400 text-green-700"
                : "bg-red-100 border border-red-400 text-red-700"
            }`}
          >
            <p className="font-medium">{message.text}</p>
            <button onClick={() => setMessage(null)} className="ml-4 text-gray-700 hover:text-gray-900">
              <X size={20} />
            </button>
          </div>
        )}

        <div className="mt-8">
            <EntityUpload
                entity={{
                    name: "employee", // lowercase for filename validation
                    endpoint: "/employees",
                    fields: [
                    { name: "empno", label: "Employee No", required: true },
                    { name: "empname", label: "Employee Name", required: true },
                    { name: "tiercode", label: "Tier Code", required: true },
                    { name: "locationcode", label: "Location Code", required: true },
                    { name: "departmentcode", label: "Department Code", required: true },
                    { name: "supervisorcode", label: "Supervisor Code" },
                    { name: "salary", label: "Salary", required: true },
                    { name: "entrydate", label: "Entry Date", required: true },
                    ],
                }}
                onSuccess={() => fetchEmployees()}
                />

          {isFormVisible ? (
            <div className="fixed inset-0 z-50 bg-black bg-opacity-30 backdrop-blur-sm flex items-center justify-center p-4">
              <EntityForm
                entity={{ ...entity, fields: fieldsWithOptions }}
                onSubmit={handleSubmit}
                initialData={editing}
                onCancel={handleCancelForm}
              />
            </div>
          ) : (
            <EntityTable
              data={employees}
              fields={entity.fields}
              onEdit={handleEdit}
              onDelete={handleDelete}
              onAddNew={handleAddNew}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default EmployeePage;
