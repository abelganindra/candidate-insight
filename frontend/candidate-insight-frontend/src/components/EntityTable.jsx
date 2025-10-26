import React, { useState, useEffect, useMemo } from "react";
import { 
  X,        // Close icon for EntityForm
  Plus,     // Add new item in EntityTable
  Edit3,    // Edit button
  Trash2,   // Delete button
  Search    // Search icon
} from "lucide-react";

const EntityTable = ({ data, onEdit, onDelete, fields, onAddNew }) => {
  const [search, setSearch] = useState("");

  const headers = fields.map(f => f.label);
  const dataKeys = fields.map(f => f.name);

  // Filtered data logic
  const filteredData = useMemo(() => {
    if (!data) return [];
    if (!search) return data;

    const lowerSearch = search.toLowerCase();
    return data.filter((row) =>
      dataKeys.some((key) =>
        String(row[key]).toLowerCase().includes(lowerSearch)
      )
    );
  }, [data, search, dataKeys]);

  return (
    <div className="bg-white p-6 md:p-8 rounded-xl shadow-2xl space-y-6">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div className="relative w-full md:max-w-sm">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
          <input
            type="text"
            placeholder="Search all tiers..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="border border-gray-300 rounded-lg pl-10 pr-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 w-full transition"
          />
        </div>
        <button
            onClick={onAddNew}
            className="flex items-center gap-2 bg-green-500 text-white font-semibold py-2.5 px-5 rounded-lg hover:bg-green-600 transition duration-300 shadow-md transform hover:scale-[1.02] active:scale-95"
        >
            <Plus size={20} />
            
        </button>
      </div>

      <div className="overflow-x-auto">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-blue-50">
            <tr>
              {headers.map((label, index) => (
                <th
                  key={index}
                  className="px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider"
                >
                  {label}
                </th>
              ))}
              <th className="px-6 py-3 text-right text-xs font-bold text-gray-600 uppercase tracking-wider">
                Actions
              </th>
            </tr>
          </thead>

          <tbody className="bg-white divide-y divide-gray-200">
            {filteredData.length > 0 ? (
                filteredData.map((row, idx) => (
                <tr
                    key={row.tiercode || idx}
                    className={`hover:bg-blue-50 transition-colors`}
                >
                    {dataKeys.map((key) => (
                    <td key={key} className="px-6 py-4 whitespace-nowrap text-sm text-gray-800">
                        {row[key]}
                    </td>
                    ))}
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                      <button
                        onClick={() => onEdit(row)}
                        className="text-blue-600 hover:text-blue-900 p-2 rounded-full hover:bg-blue-100 transition"
                        title="Edit"
                      >
                        <Edit3 size={18} />
                      </button>
                      <button
                        onClick={() => onDelete(row)}
                        className="text-red-600 hover:text-red-900 p-2 rounded-full hover:bg-red-100 transition"
                        title="Delete"
                      >
                        <Trash2 size={18} />
                      </button>
                    </td>
                </tr>
                ))
            ) : (
                <tr>
                    <td colSpan={headers.length + 1} className="px-6 py-8 text-center text-gray-500">
                        {search ? "No tiers match your search criteria." : "No tiers available. Use the button above to add one."}
                    </td>
                </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};
export default EntityTable
;