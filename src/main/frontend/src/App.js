import React, { useState, useEffect } from 'react';
import './App.css';
import Home from './components/Home';
import Navbar from './components/Navbar';
import { BrowserRouter as Router } from "react-router-dom";
import Footer from './components/Footer';
import Main from './components/Main';
import About from './components/About';
import { AiOutlineArrowUp } from 'react-icons/ai'

import AOS from 'aos';
import 'aos/dist/aos.css';

function App() {
  const [scroll, setScroll] = useState(0);
  const [scrollArrow, setScrollArrow] = useState(0);

  useEffect(() => {
    window.scrollTo({top: 0, left: 0, behavior: 'smooth'});
    AOS.init({ duration: 1000 });

    let progressBarHandler = () => {

      const totalScroll = document.documentElement.scrollTop;
      const windowHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
      const scroll = `${totalScroll / windowHeight}`;

      setScroll(scroll);
      if(totalScroll >= 700) {
        setScrollArrow(1)
      } else {
        setScrollArrow(0)
      }
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
        <button className='arrow-to-top'
          data-aos='fade'
          onClick={() => {
            window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
          }}
          style={{ opacity: `${scrollArrow}` }}
          >
          <i><AiOutlineArrowUp/></i>
        </button>
      </div>
    </div>
  );
}

export default App;
