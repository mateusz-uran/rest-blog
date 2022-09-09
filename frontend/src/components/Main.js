import React, { useState, useEffect } from 'react';
import { FaRegHandPeace } from "react-icons/fa";
import image_main from '../images/4002785.jpg';
import '../App.css';
import { isMobile } from 'react-device-detect';


const Main = () => {
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
  }, [])

  return (
    <div className='wrapper'>
      <div id='home' className='main'>
        <div className='leftSide'>
          <p>Hello there <FaRegHandPeace fill='#007FFF' />, my name is</p>
          <h1>Mateusz Uranowski</h1>
          <p>and I'm a web developer.
            <span id="show" style={{ transform: `scale(${scroll}, 1)`, opacity: `${scroll * (isMobile ? 12 : 8)}` }} >I guess</span>
            <span id="show" style={{ transform: `scale(${scroll}, 1)`, opacity: `${scroll * (isMobile ? 8 : 4)}` }} >Some day</span>
            <span id="show" style={{ transform: `scale(${scroll}, 1)`, opacity: `${scroll * (isMobile ? 4 : 2)}` }} >Finally</span>
          </p>
        </div>
        <div className='rightSide'>
          <img src={image_main} alt=''></img>
          <a className='credits' href="http://www.freepik.com" target='_blank' rel='noopener noreferrer'>Designed by stories / Freepik</a>
        </div>
      </div>
    </div>
  )
}

export default Main