import React, { useState, useEffect } from 'react';
import './App.css';
import Home from './components/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router } from "react-router-dom";
import Footer from './components/Footer';
import Main from './components/Main';
import About from './components/About';

function App() {
  const [scroll, setScroll] = useState(0);

  useEffect(() => {

    let progressBarHandler = () => {

      const totalScroll = document.documentElement.scrollTop;
      const windowHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
      const scroll = `${totalScroll / windowHeight}`;

      setScroll(scroll);
    }

    window.addEventListener("scroll", progressBarHandler);

    return () => window.removeEventListener("scroll", progressBarHandler);
  }, []);

  return (
    <div className="App">
      <div className='hero-effect'>
        <div className='overlay'>
          <div id="progressBarContainer">
            <div id="progressBar" style={{ transform: `scale(${scroll}, 1)`, opacity: `${scroll * 1.5}` }} />
          </div>
          <Router>
            <Navbar />
            <Main />
            <About />
            <Home />
            <Footer />
          </Router>
        </div>
      </div>
    </div>
  );
}

export default App;
