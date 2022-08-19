import React, { useState, useEffect } from 'react';
import '../App.css';
import { Link } from "react-router-dom";
import { FaBars, FaRandom } from "react-icons/fa";
import LoginModal from './LoginModal';
import RegisterModal from './RegisterModal';
import AuthService from '../services/auth.service';
import { AiOutlinePoweroff } from 'react-icons/ai';

function Navbar() {

  const [showLinks, setShowLinks] = useState(false);

  const logOut = () => {
    AuthService.logout();
    window.location.reload();
  };

  useEffect(() => {
    let url = window.location.href.split("/");
    let target = url[url.length - 1].toLowerCase();
    let element = document.getElementById(target);
    element && element.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  return (
    <div className='Navbar'>
      <div className='wrapper-nav'>
        <div className='leftSide'>
          <div className='links' id={showLinks ? 'hidden' : ''}>
            <Link
              to="/"
              onClick={() => {
                setShowLinks(false)
                let home = document.getElementById("home");
                home && home.scrollIntoView({ behavior: "smooth", block: "start" });
              }}
            >
              Home
            </Link>
            <Link
              to="/about"
              onClick={() => {
                setShowLinks(false)
                let about = document.getElementById("about");
                about && about.scrollIntoView({ behavior: "smooth", block: "start" });
              }}
            >
              About
            </Link>
            <Link
              to="/projects"
              onClick={() => {
                setShowLinks(false)
                let projects = document.getElementById("projects");
                projects && projects.scrollIntoView({ behavior: "smooth", block: "start" });
              }}
            >
              Projects
            </Link>
            <Link
              to="/contact"
              onClick={() => {
                setShowLinks(false)
                let footer = document.getElementById("footer");
                footer && footer.scrollIntoView({ behavior: "smooth", block: "start" });
              }}
            >
              Contact
            </Link>
          </div>
          <button onClick={() => setShowLinks(!showLinks)}><FaBars size='1.5em' fill='#007FFF' /></button>
        </div>
        <div className='rightSide'>
          <div className='nav-buttons'>
            <LoginModal />
          </div>
          <div className='nav-buttons'>
            <RegisterModal />
          </div>
          <div className='nav-buttons'>
            <i onClick={logOut}><AiOutlinePoweroff /></i>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Navbar