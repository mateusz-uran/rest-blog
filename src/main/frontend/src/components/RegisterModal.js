import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AuthService from "../services/auth.service";

function RegisterModal() {

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [hidden, setHidden] = useState(true);

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [gender, setGender] = useState("");
  const genders = [
    { value: '', text: '--Choose an gender--' },
    { value: 'male', text: 'Male' },
    { value: 'female', text: 'Female' },
    { value: 'other', text: 'Other' },
  ];
  const [selected, setSelected] = useState(genders[0].value);

  const handleChange = event => {
    if (event.target.value === "other") {
      setHidden(false);
    } else {
      setHidden(true)
    }
    setSelected(event.target.value);
    setGender(selected);
  };

  const onChangeGender = (e) => {
    const gender = e.target.value;
    setGender(gender);
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
    AuthService.register(username, email, password, gender).then(
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
            <div className='form-row'>
              <select value={selected} onChange={handleChange}>
                {genders.map(gender => (
                  <option key={gender.value} value={gender.value}>
                    {gender.text}
                  </option>
                ))}
              </select>
              {!hidden ? <input
                defaultValue={gender}
                onChange={(e) => onChangeGender(e)} /> : null}
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