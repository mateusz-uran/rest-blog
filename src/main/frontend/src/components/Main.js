import React, { useState, useEffect } from 'react';
import { FaRegHandPeace } from "react-icons/fa";
import image_main from '../images/4002785.jpg';
import '../App.css';

const Main = () => {
  const [isVisible, setIsVisible] = useState(false);

  const listenToScroll = () => {
    let heightToShowFrom = 200;
    const winScroll = document.body.scrollTop ||
      document.documentElement.scrollTop;

    if (winScroll > heightToShowFrom) {
      !isVisible &&
        setIsVisible(true);
    } else {
      setIsVisible(false);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", listenToScroll);
    return () =>
      window.removeEventListener("scroll", listenToScroll);
  }, [])

  return (
    <div className='wrapper'>
      <div id='home' className='main'>
        <div className='leftSide'>
          <p>Hello there <FaRegHandPeace fill='#007FFF' />, my name is</p>
          <h1>Mateusz Uranowski</h1>
          <p>and I'm a web developer.
            {
              isVisible
              &&
              <span id="show">I guess</span>
            }
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