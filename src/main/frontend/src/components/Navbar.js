import React, {useState} from 'react';
import '../App.css';
import { FaBars } from "react-icons/fa";

function Navbar() {

  const [showLinks, setShowLinks] = useState(false);

  return (
    <div className='Navbar'>
      <div className='wrapper'>
        <div className='leftSide'>
          <div className='links' id={showLinks ? 'hidden' : ''}>
            <a href='/home'>Home</a>
            <a href='/about'>About</a>
            <a href='/projects'>Projects</a>
            <a href='/contact '>Contact</a>
          </div>
          <button onClick={() => setShowLinks(!showLinks)}><FaBars size='1.5em' fill='#007FFF'/></button>
        </div>
        <div className='rightSide'>
            <a href='/login'><button>Login</button></a>
            <a href='/register'><button>Register</button></a>
        </div>
      </div>
    </div>
  )
}

export default Navbar