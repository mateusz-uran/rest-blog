import React from 'react';
import { FaRegHandPeace } from "react-icons/fa";
import image_main from '../images/undraw_hello_re_3evm.svg';
import '../App.css';

const Main = () => {
  return (
    <div className='wrapper'>
      <div id='home' className='main'>
        <div className='leftSide'>
          <p>Hello there <FaRegHandPeace fill='#007FFF' />, my name is</p>
          <h1>Mateusz Uranowski</h1>
          <p>and I'm a web developer. I guess</p>
        </div>
        <div className='rightSide'>
          <img src={image_main} alt=''></img>
        </div>
      </div>
    </div>
  )
}

export default Main