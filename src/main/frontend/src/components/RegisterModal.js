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
      <Modal show={show} onHide={handleClose} className='login' centered>
        <Modal.Header closeButton></Modal.Header>
        <Modal.Body>
          <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <label htmlFor='username' className='form-label'>
                Username
              </label>
              <input
                type={"text"}
                className={"form-header"}
                name={"username"}
                defaultValue={username}
                onChange={(e) => onChangeUsername(e)}
                required
              />
            </div>
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Email
              </label>
              <input
                type={"text"}
                className={"form-header"}
                name={"email"}
                defaultValue={email}
                onChange={(e) => onChangeEmail(e)}
                required
              />
            </div>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Password
              </label>
              <input
                type={"text"}
                className={"form-intro"}
                name={"intro"}
                defaultValue={password}
                onChange={(e) => onChangePassword(e)}
              />
            </div>
            <Button onClick={handleClose} className='close' >
              Close
            </Button>
            <Button type={"submit"} onClick={handleClose} className='submit' >
              Register
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default RegisterModal