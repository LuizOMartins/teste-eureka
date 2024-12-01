import './App.css';
import SearchScripts from './pages/SearchScripts';
import CreateScript from './pages/CreateScript';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/search" element={<SearchScripts />} />
        <Route path="/create" element={<CreateScript />} />
      </Routes>
    </Router>
  );
}
export default App;
