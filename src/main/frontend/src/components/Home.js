import React from 'react';
import '../App.css';
import image from '../images/undraw_hello_re_3evm.svg'
import { FaRegHandPeace } from "react-icons/fa";
import { Link } from "react-router-dom";
import About from './About';
import Post from './Post';

const Home = () => {
  return (
    <div className='Home'>
      <Link className={"btn btn-outline-light"} to={"/addpost"}>Add Post</Link>
      <div className='wrapper'>
        <div className='leftSide'>
          <p>Hello there <FaRegHandPeace fill='#007FFF' />, I'm</p>
          <h1>Mateusz Uranowski</h1>
          <p>and I'm a web developer.</p>
        </div>
        <div className='rightSide'>
          <img src={image} alt=''></img>
        </div>
      </div>
      <div>
        <About />
        <Post />
      </div>
    </div>
  )
}

export default Home