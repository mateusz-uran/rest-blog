import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import Moment from 'moment';

export default function AddCommentModal({ postId }) {

  const [comment, setComment] = useState({
    content: "",
    date: "",
    author: ""
  });

  const { content } = comment;

  const onInputChange = (e) => {
    setComment({ ...comment, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A')
    const defaultUser = "user";
    comment.date = formatDate;
    comment.author = defaultUser;
    await axios.post(`http://localhost:8080/api/v1/post/${postId}/add-comment`, comment);
  };

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  return (
    <>
      <Button className="nextButton" onClick={handleShow}>
        Dodaj post
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Modal heading</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Name
              </label>
              <input
                type={"text"}
                className={"form-control"}
                placeholder={"Enter your name"}
                name={"content"}
                value={content}
                onChange={(e) => onInputChange(e)}
              />
            </div>

            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
            <Button type={"submit"} variant="primary" onClick={handleClose}>
              Save Changes
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}