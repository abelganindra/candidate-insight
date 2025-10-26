import React, { useState, useEffect, useCallback } from "react";
import { X } from "lucide-react";

import api from "../api/api"; // Axios instance
import EntityForm from "../components/EntityForm";
import EntityTable from "../components/EntityTable";
import EntityUpload from "../components/EntityUpload";


const DepartmentPage = () => {
  // Entity metadata
  const entity = {
    name: "Department",
    endpoint: "/departments",
    fields: [
      { name: "departmentcode", label: "Department Code", required: true },
      { name: "departmentname", label: "Department Name", required: true },
    ],
  };

  // State
  const [departments, setDepartments] = useState([]);
  const [editing, setEditing] = useState(null);
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [message, setMessage] = useState(null);

  // Show temporary messages
  const showMessage = (text, type = "success") => {
    setMessage({ text, type });
    setTimeout(() => setMessage(null), 3000);
  };

  // Fetch departments from API
  const fetchDepartments = useCallback(async () => {
    setIsLoading(true);
    try {
      const res = await api.get(entity.endpoint);
      setDepartments(res.data);
    } catch (err) {
      console.error("Fetch error:", err);
      showMessage("Failed to fetch departments.", "error");
    } finally {
      setIsLoading(false);
    }
  }, [entity.endpoint]);

  useEffect(() => {
    fetchDepartments();
  }, [fetchDepartments]);

  // Form submit handler
  const handleSubmit = async (data) => {
    setIsLoading(true);
    try {
      if (editing) {
        await api.put(`${entity.endpoint}/${editing.departmentcode}`, data);
        showMessage(`Department ${editing.departmentcode} updated successfully!`);
      } else {
        await api.post(entity.endpoint, data);
        showMessage("New department created successfully!");
      }
      setEditing(null);
      setIsFormVisible(false);
      fetchDepartments();
    } catch (err) {
      console.error("Save error:", err);
      showMessage("Failed to save department.", "error");
    } finally {
      setIsLoading(false);
    }
  };

  // Edit a row
  const handleEdit = (row) => {
    setEditing(row);
    setIsFormVisible(true);
  };

  // Show form for creating a new department
  const handleAddNew = () => {
    setEditing(null);
    setIsFormVisible(true);
  };

  // Cancel form
  const handleCancelForm = () => {
    setEditing(null);
    setIsFormVisible(false);
  };

  // Delete a department
  const handleDelete = async (row) => {
    if (!window.confirm(`Are you sure you want to delete department ${row.departmentname} (${row.departmentcode})?`))
      return;

    setIsLoading(true);
    try {
      await api.delete(`${entity.endpoint}/${row.departmentcode}`);
      showMessage(`Department ${row.departmentcode} deleted successfully.`);
      fetchDepartments();
    } catch (err) {
      console.error("Delete error:", err);
      showMessage("Failed to delete department.", "error");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-4 sm:p-6 lg:p-10 font-sans">
      <div className="max-w-7xl mx-auto space-y-8">
        {/* Header */}
        <header className="py-4 border-b border-gray-200">
          <h1 className="text-3xl sm:text-4xl font-extrabold text-gray-900 tracking-tight">
            🏢 Department Management System
          </h1>
          <p className="mt-2 text-lg text-gray-500">
            Create, manage, and track your departments.
          </p>
        </header>

        {/* Loading Indicator */}
        {isLoading && (
          <div className="flex items-center justify-center p-4 bg-yellow-100 border border-yellow-300 text-yellow-800 rounded-lg">
            <svg
              className="animate-spin -ml-1 mr-3 h-5 w-5"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                className="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                strokeWidth="4"
              ></circle>
              <path
                className="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
              ></path>
            </svg>
            Processing request...
          </div>
        )}

        {/* Message Banner */}
        {message && (
          <div
            className={`p-4 rounded-lg shadow-lg flex justify-between items-center ${
              message.type === "success"
                ? "bg-green-100 border border-green-400 text-green-700"
                : "bg-red-100 border border-red-400 text-red-700"
            }`}
          >
            <p className="font-medium">{message.text}</p>
            <button
              onClick={() => setMessage(null)}
              className={`ml-4 ${
                message.type === "success"
                  ? "text-green-700 hover:text-green-900"
                  : "text-red-700 hover:text-red-900"
              }`}
            >
              <X size={20} />
            </button>
          </div>
        )}

        {/* Main Content */}
        <div className="mt-8">
          <EntityUpload
              entity={{
                name: "departement", // lowercase name for filename validation
                endpoint: "/departments",
                fields: [
                  { name: "departmentcode", label: "Department Code", required: true },
                  { name: "departmentname", label: "Department Name", required: true },
                ],
              }}
              onSuccess={() => fetchDepartments()}
            />

          {isFormVisible ? (
            <div className="fixed inset-0 z-50 bg-black bg-opacity-30 backdrop-blur-sm flex items-center justify-center p-4">
              <EntityForm
                entity={entity}
                onSubmit={handleSubmit}
                initialData={editing}
                onCancel={handleCancelForm}
              />
            </div>
          ) : (
            <EntityTable
              data={departments}
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

export default DepartmentPage;
