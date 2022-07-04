import React from 'react';
import '../App.css';
import image from '../images/V1228_generated.jpg'

const About = () => {
  return (
    <div className='About'>
      <div className='wrapper'>
        <div className='leftSide'><img src={image} alt=''></img></div>
        <div className='rightSide'>
          <div className='content'>
            <h2>About Me</h2>
            <p>My name is Mateusz and I'm 25.
            </p>
            <p>

            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default About