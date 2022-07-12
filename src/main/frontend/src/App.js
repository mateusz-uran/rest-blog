import React from 'react';
import './App.css';
import Home from './components/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Footer from './components/Footer';
import Main from './components/Main';
import About from './components/About';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Main/>
        <About />
        <Home/>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
