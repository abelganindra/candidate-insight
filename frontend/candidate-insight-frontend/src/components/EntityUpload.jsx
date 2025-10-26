import React, { useState } from "react";
import Papa from "papaparse";
import api from "../api/api";
import { Upload } from "lucide-react";

const EntityUpload = ({ entity, onSuccess }) => {
  const [message, setMessage] = useState(null);
  const [isUploading, setIsUploading] = useState(false);

  const showMessage = (text, type = "success") => {
    setMessage({ text, type });
    setTimeout(() => setMessage(null), 4000);
  };

  const handleCsvUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const filenameWithoutExt = file.name.replace(/\.[^/.]+$/, ""); // remove extension
    if (filenameWithoutExt !== entity.name.toLowerCase()) {
    showMessage(`Filename must be "${entity.name.toLowerCase()}.csv"`, "error");
    return;
    }

    Papa.parse(file, {
      header: true,
      skipEmptyLines: true,
      complete: async (results) => {
        const rows = results.data;
        if (!rows || rows.length === 0) {
          showMessage("CSV is empty", "error");
          return;
        }

        setIsUploading(true);
        let successCount = 0;

        try {
          for (const row of rows) {
            const payload = {};
            entity.fields.forEach((f) => {
              payload[f.name] = row[f.name]?.trim() || "";
            });

            // Skip invalid rows
            const missingRequired = entity.fields.some(
              (f) => f.required && !payload[f.name]
            );
            if (missingRequired) continue;

            await api.post(entity.endpoint, payload);
            successCount++;
          }

          showMessage(`Uploaded ${successCount} rows successfully!`);
          if (onSuccess) onSuccess(); // refresh table
        } catch (err) {
          console.error(err);
          showMessage("Failed to upload CSV.", "error");
        } finally {
          setIsUploading(false);
        }
      },
      error: (err) => {
        console.error(err);
        showMessage("Failed to parse CSV.", "error");
      },
    });
  };

  return (
    <div className="flex items-center space-x-2">
      <label className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 flex items-center cursor-pointer">
        <Upload size={16} className="mr-1" /> Upload CSV
        <input
          type="file"
          accept=".csv"
          onChange={handleCsvUpload}
          className="hidden"
        />
      </label>

      {isUploading && <p className="text-sm text-gray-700 ml-2">Uploading...</p>}

      {message && (
        <div
          className={`p-2 rounded ml-2 ${
            message.type === "success" ? "bg-green-200" : "bg-red-200"
          }`}
        >
          {message.text}
        </div>
      )}
    </div>
  );
};

export default EntityUpload;
