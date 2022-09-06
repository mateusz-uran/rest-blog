import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AuthService from "../services/auth.service";
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';
import { getRandomOptions } from '../services/getRandomOptions';

function RegisterModal() {

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [hidden, setHidden] = useState(true);

  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const genders = [
    { value: '', text: '--Choose an gender--' },
    { value: 'male', text: 'Male' },
    { value: 'female', text: 'Female' },
    { value: 'other', text: 'Other' },
  ];
  const [gender, setGender] = useState(genders[0].value);
  const options = getRandomOptions(gender);


  const handleChange = event => {
    if (event.target.value === "other") {
      setHidden(false);
      setGender("");
    } else {
      setHidden(true)
      setGender(event.target.value);
    }
  };

  const onChangeGender = event => {
    setGender(event.target.value);
  }

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };
  const onChangeEmail = (e) => {
    const email = e.target.value;
    setEmail(email);
  };
  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    AuthService.register(username, email, password, gender, JSON.stringify(options)).then(
      () => {
        handleClose();
        toast.success("Registered successfully, please login.");
        setUsername('');
        setEmail('');
        setPassword('');
        setGender('');
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        console.log(resMessage);
        toast.error(resMessage);
      }
    );
  };

  useEffect(() => {
  })

  return (
    <>
      <Button onClick={handleShow}>Register</Button>
      <Modal show={show} onHide={handleClose} id='register' centered>
        <Modal.Header closeButton className='head'><h4>Sing up</h4></Modal.Header>
        <Modal.Body className='register-body'>
          <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <input
                type={"text"}
                className={"form-header"}
                placeholder={"Username"}
                name={"username"}
                defaultValue={username}
                onChange={(e) => onChangeUsername(e)}
                required
              />
            </div>
            <div className='form-row'>
              <input
                type={"email"}
                className={"form-header"}
                placeholder={"Email"}
                name={"email"}
                defaultValue={email}
                onChange={(e) => onChangeEmail(e)}
                required
              />
            </div>
            <div className='form-row'>
              <input
                type={"password"}
                className={"form-intro"}
                placeholder={"Password"}
                name={"password"}
                defaultValue={password}
                onChange={(e) => onChangePassword(e)}
              />
            </div>
            <div className='form-row'>
              <select value={gender} onChange={handleChange}>
                {genders.map(option => (
                  <option key={option.value} value={option.value}>
                    {option.text}
                  </option>
                ))}
              </select>
              {!hidden ? <input
                className='otherGender'
                name={"gender"}
                defaultValue={gender}
                onChange={(e) => onChangeGender(e)} /> : null}
            </div>
            <Button type={"submit"} className='submit register-btn' >
              Sign in
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default RegisterModal