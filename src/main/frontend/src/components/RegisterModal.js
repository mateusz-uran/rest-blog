import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AuthService from "../services/auth.service";

function RegisterModal() {

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

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
    AuthService.register(username, email, password).then(
      () => {
        window.location.reload();
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        console.log(resMessage);
      }
    );
  };

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
                defaultValue={username}
                onChange={(e) => onChangeUsername(e)}
                required
              />
            </div>
            <div className='form-row'>
              <input
                type={"text"}
                className={"form-header"}
                placeholder={"Email"}
                defaultValue={email}
                onChange={(e) => onChangeEmail(e)}
                required
              />
            </div>
            <div className='form-row'>
              <input
                type={"text"}
                className={"form-intro"}
                placeholder={"Password"}
                defaultValue={password}
                onChange={(e) => onChangePassword(e)}
              />
            </div>
            <Button type={"submit"} onClick={handleClose} className='submit register-btn' >
              Sign in
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default RegisterModal