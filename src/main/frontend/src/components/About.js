import React from 'react';
import '../App.css';
import image from '../images/V1228_generated.jpg'

const About = () => {
  return (
    <div className='About'>
      <div className='wrapper'>
        <div className='leftSide'><img src={image} alt=''></img></div>
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
                <li>HTML</li>
                <li>CSS</li>
                <li>JavaScript - basics</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default About