import React, { useState, useEffect } from "react";
import { X } from "lucide-react";

const EntityForm = ({ entity, onSubmit, initialData = {}, onCancel }) => {
  const [formData, setFormData] = useState({});
  const [errors, setErrors] = useState({});

  const primaryKey = entity.fields[0]?.name;
  const isEditing = !!initialData?.[primaryKey];

  // Initialize formData safely
  useEffect(() => {
    if (!entity?.fields) return;
    const data = {};
    entity.fields.forEach((f) => {
      data[f.name] =
        initialData?.[f.name] !== undefined && initialData?.[f.name] !== null
          ? initialData[f.name]
          : "";
    });
    setFormData(data);
    setErrors({});
  }, [entity, initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const validate = () => {
    const newErrors = {};
    entity.fields.forEach((f, index) => {
      const value = formData[f.name];
      const trimmedValue =
        value !== undefined && value !== null ? String(value).trim() : "";

      // Primary key required on create
      if (index === 0 && !isEditing && !trimmedValue) {
        newErrors[f.name] = `${f.label} is required`;
      }

      // Other required fields
      if (f.required && !trimmedValue) {
        newErrors[f.name] = `${f.label} is required`;
      }
    });
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    onSubmit(formData);
  };

  return (
    <div className="bg-white p-6 md:p-8 rounded-xl shadow-2xl space-y-6 max-w-lg w-full max-h-[90vh] overflow-y-auto">
      <div className="flex justify-between items-center border-b pb-3 mb-4">
        <h2 className="text-2xl font-bold text-gray-800">
          {isEditing
            ? `Edit ${entity.name}: ${initialData[primaryKey]}`
            : `Create New ${entity.name}`}
        </h2>
        <button
          onClick={onCancel}
          className="text-gray-500 hover:text-red-500 transition"
        >
          <X size={24} />
        </button>
      </div>

      <form onSubmit={handleSubmit} className="space-y-5">
        {entity.fields.map((field, index) => (
          <div key={field.name} className="flex flex-col">
            <label
              htmlFor={field.name}
              className="mb-1 text-sm font-semibold text-gray-700"
            >
              {field.label}
              {field.required && <span className="text-red-500"> *</span>}
            </label>

            {field.type === "select" ? (
              <select
                id={field.name}
                name={field.name}
                value={formData[field.name] || ""}
                onChange={handleChange}
                disabled={index === 0 && isEditing}
                className={`border rounded-lg px-4 py-2.5 transition duration-200
                  focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                    errors[field.name] ? "border-red-500" : "border-gray-300"
                  } ${index === 0 && isEditing ? 'bg-gray-100 cursor-not-allowed' : 'bg-white'}`}
              >
                <option value="">Select {field.label}</option>
                {field.options?.map((opt) => (
                  <option key={opt.value} value={opt.value}>
                    {opt.label}
                  </option>
                ))}
              </select>
            ) : field.multiline ? (
              <textarea
                id={field.name}
                name={field.name}
                value={formData[field.name] || ""}
                onChange={handleChange}
                rows={3}
                placeholder={`Enter ${field.label.toLowerCase()}`}
                className={`border rounded-lg px-4 py-2.5 transition duration-200
                  focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                    errors[field.name] ? "border-red-500" : "border-gray-300"
                  }`}
              />
            ) : (
              <input
                id={field.name}
                name={field.name}
                type={field.type || "text"}
                value={formData[field.name] || ""}
                onChange={handleChange}
                placeholder={`Enter ${field.label.toLowerCase()}`}
                disabled={index === 0 && isEditing}
                className={`border rounded-lg px-4 py-2.5 transition duration-200
                  focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                    errors[field.name] ? "border-red-500" : "border-gray-300"
                  } ${index === 0 && isEditing ? 'bg-gray-100 cursor-not-allowed' : 'bg-white'}`}
              />
            )}

            {errors[field.name] && (
              <p className="text-red-500 text-xs mt-1">{errors[field.name]}</p>
            )}
          </div>
        ))}

        <button
          type="submit"
          className="w-full bg-blue-600 text-white font-semibold py-3 px-4 rounded-lg hover:bg-blue-700 transition duration-300 shadow-md transform hover:scale-[1.01] active:scale-95"
        >
          {isEditing ? `Update ${entity.name}` : `Create ${entity.name}`}
        </button>
      </form>
    </div>
  );
};

export default EntityForm;
