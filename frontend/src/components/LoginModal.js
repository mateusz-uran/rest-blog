import React, { useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AuthService from "../services/auth.service";
import RegisterModal from './RegisterModal';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';

function LoginModal() {

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };
  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    AuthService.login(username, password).then(
      () => {
        handleClose();
        window.location.reload();
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
          if(error.response.status === 401) {
            toast.error("Wrong login or password");
            console.log(error.response.status);
          }
          toast.error("Something went wrong");
        console.log(resMessage);
      }
    );
  };

  return (
    <>
      <Button onClick={handleShow}>Login</Button>
      <Modal show={show} onHide={handleClose} id='login' centered>
        <Modal.Header closeButton className='head'><h4>Sing in</h4></Modal.Header>
        <Modal.Body className='login-body'>
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
                type={"password"}
                className={"form-intro"}
                placeholder={"Password"}
                defaultValue={password}
                onChange={(e) => onChangePassword(e)}
              />
            </div>
            <Button type={"submit"} className='submit login-btn' >
              Login
            </Button>
          </form>
          <div className='login-footer'>
            <p>Not a member yet? <RegisterModal onClick={handleClose}/></p>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default LoginModal