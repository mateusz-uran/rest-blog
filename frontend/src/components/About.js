import React from 'react';
import image_about from '../images/V1228_generated.jpg';
import '../App.css';
import PDF from '../documents/CV-template.pdf';
import { AiOutlineFilePdf } from 'react-icons/ai';


const About = () => {

  return (
    <div className='wrapper'>
      <div id='about' className='about'>
        <div className='leftSide'>
          <div data-aos='fade-up' className='img-bng'></div>
          <img src={image_about} alt=''></img>
        </div>
        <div className='rightSide'>
          <div className='content'>
            <h2>About Me</h2>
            <p>My name is Johny and I'm 25. Something about you.
            </p>
            <h4>Technologies and Languages I'm using</h4>
            <div className='list'>
              <ul className='tech-list'>
                <li>Java 11</li>
                <li>Spring Boot</li>
                <li>AWS</li>
                <li>JavaScript</li>
                <li>React</li>
                <li>HTML</li>
                <li>CSS</li>
              </ul>
              <ul className='bar-list'>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='java-11-bar-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='spring-boot-bar-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='aws-bar-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='js-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='react-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='html-progress'>&nbsp;</div>
                  </div>
                </li>
                <li>
                  <div className='bng-bar'>
                    <div data-aos='fade-right' className='css-progress'>&nbsp;</div>
                  </div>
                </li>
              </ul>
            </div>
            <div className='pdf-link'>
              <div className='pdf-border'>
                <p>CV</p>
                <a href={PDF} target="_blank" download={"Your Fullname - CV"} rel='noopener noreferrer'><i><AiOutlineFilePdf className='pdf-icon' /></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default About