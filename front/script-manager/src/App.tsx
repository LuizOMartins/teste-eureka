import './App.css';
import SearchScripts from './pages/SearchScripts';
import CreateScript from './pages/CreateScript';
import FilteredScripts from './pages/FilteredScripts';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function App() {
  return (
    <Router>
      <div>
        {/* NavBar */}
        <nav style={{ background: "#282c34", padding: "1rem" }}>
          <ul style={{ display: "flex", listStyle: "none", margin: 0, padding: 0 }}>
            <li style={{ marginRight: "1rem" }}>
              <Link to="/search" style={{ color: "#61dafb", textDecoration: "none" }}>Search Scripts</Link>
            </li>
            <li style={{ marginRight: "1rem" }}>
              <Link to="/create" style={{ color: "#61dafb", textDecoration: "none" }}>Create Script</Link>
            </li>
            <li>
              <Link to="/filter" style={{ color: "#61dafb", textDecoration: "none" }}>Filter Scripts</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/search" element={<SearchScripts />} />
          <Route path="/create" element={<CreateScript />} />
          <Route path="/filter" element={<FilteredScripts />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
