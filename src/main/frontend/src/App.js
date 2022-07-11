import React from 'react';
import './App.css';
import Home from './components/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Footer from './components/Footer';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route exact path={"/"} element={<Home />} />
        </Routes>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
