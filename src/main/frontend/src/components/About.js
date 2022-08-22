import React from 'react';
import image_about from '../images/V1228_generated.jpg';
import '../App.css';

const About = () => {
  return (
    <div className='wrapper'>
        <div id='about' className='about'>
          <div className='leftSide'><img src={image_about} alt=''></img></div>
          <div className='rightSide'>
            <div className='line'>&nbsp;</div>
            <div className='content'>
              <h2>About Me</h2>
              <p>My name is Mateusz and I'm 25. I've graduated at the beggining of 2022 IT studies
                with specialization for web development.
              </p>
              <div>
                <h4>Technologies and Languages I'm using</h4>
                <ul>
                  <li>Java 11</li>
                  <li>Spring Boot</li>
                  <li>AWS
                    <ul>
                      <li>S3 Bucket</li>
                      <li>Elastik Beanstalk</li>
                      <li>Amplify</li>
                    </ul>
                  </li>
                  <li>JavaScript - basics</li>
                  <li>React</li>
                  <li>HTML</li>
                  <li>CSS</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
  )
}

export default About