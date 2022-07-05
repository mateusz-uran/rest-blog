import React from 'react';
import './App.css';
import Home from './components/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddPostTest from './components/AddPostTest';
import EditPost from './components/EditPost';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/addpost"} element={<AddPostTest />} />
          <Route exact path={"/editpost/:id"} element={<EditPost />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
