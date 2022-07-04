import React from 'react';
import './App.css';
import About from './components/About';
import Home from './components/Home';
import Navbar from './components/Navbar';
import Post from './components/Post';

function App() {
  return (
    <div className="App">
      <Navbar />
      <br/>
      <Home />
      <br/>
      <About />
      <br/>
      <Post />
    </div>
  );
}

export default App;
