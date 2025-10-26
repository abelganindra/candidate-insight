import { Link, useLocation } from "react-router-dom";

export default function Navbar() {
  const location = useLocation();

  const links = [
    { path: "/tiers", label: "Tiers" },
    { path: "/locations", label: "Locations" },
    { path: "/departments", label: "Departments" },
    { path: "/employees", label: "Employees" },
  ];

  return (
    <nav className="bg-blue-600 text-white p-4 shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <div className="text-xl font-bold">Candidate Insight</div>
        <div className="flex space-x-4">
          {links.map((link) => (
            <Link
              key={link.path}
              to={link.path}
              className={`px-3 py-2 rounded hover:bg-blue-500 ${
                location.pathname === link.path ? "bg-blue-800" : ""
              }`}
            >
              {link.label}
            </Link>
          ))}
        </div>
      </div>
    </nav>
  );
}
