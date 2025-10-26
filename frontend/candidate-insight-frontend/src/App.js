import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import TierPage from "./pages/TierPage";
import LocationPage from "./pages/LocationPage";
import DepartmentPage from "./pages/DepartmentPage";
import EmployeePage from "./pages/EmployeePage";
import Navbar from "./components/Navbar";

function App() {
  return (
    <BrowserRouter>
     <Navbar />
      <div className="container mx-auto mt-6">
      <Routes>
        <Route path="/tiers" element={<TierPage />} />
        <Route path="/" element={<Navigate to="/tiers" />} />
        <Route path="/locations" element={<LocationPage />} />
         <Route path="/departments" element={<DepartmentPage />} />
         <Route path="/employees" element={<EmployeePage />} />
      </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
