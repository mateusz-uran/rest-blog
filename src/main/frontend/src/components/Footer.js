import React from 'react';
import { BsTelephoneFill } from 'react-icons/bs';
import { FaMapMarkerAlt } from 'react-icons/fa';
import { IoMdMail } from 'react-icons/io'
import { AiFillGithub, AiFillLinkedin, AiFillFacebook } from 'react-icons/ai';
import '../App.css';

const Footer = () => {

  return (
    <>
    <div id='footer' className='footer'>
      <div className='wrapper'>
        <div className='leftSide'>
          <h4>Contact</h4>
          <div className='contact'>
            <span>
              <i><FaMapMarkerAlt /></i>
              <p>Lublin, Polska</p>
            </span>
          </div>
          <div className='contact'>
            <span>
              <i><IoMdMail /></i>
              <a href='mailto:mateusz.uranowski@onet.pl'>mateusz.uranowski@onet.pl</a>
            </span>
          </div>
        </div>
        <div className='rightSide'>
          <h4>Links</h4>
          <div className='social'>
            <span>
              <i><AiFillGithub /></i>
              <a href={'https://github.com/mateusz-uran'} target='_blank' rel='noopener noreferrer'>GitHub</a>
            </span>
          </div>
          <div className='social'>
            <span>
              <i><AiFillLinkedin /></i>
              <a href={'https://pl.linkedin.com/'} target='_blank' rel='noopener noreferrer'>Linkedin</a>
            </span>
          </div>
        </div>
      </div>
    </div>
    <div className='copyrights'>
      <p>Copyright © 2022 mateusz-uran. All Rights Reserved</p>
    </div>
    </>
  )
}

export default Footer