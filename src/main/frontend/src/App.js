import React from 'react';
import './App.css';
import Navbar from './components/Navbar';
import Post from './components/Post';

function App() {
  return (
    <div className="App">
      <Navbar />
      <br/>
      <Post />
    </div>
  );
}

export default App;
