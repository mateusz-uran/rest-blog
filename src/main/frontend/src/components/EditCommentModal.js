import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import Moment from 'moment';

export default function EditCommentModal({ postId, commentId }) {

  const [comment, setComment] = useState({
    content: ""
  });

  const { content } = comment;

  const onInputChange = (e) => {
    setComment({ ...comment, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadComment()
  }, [])

  const onSubmit = async (e) => {
    e.preventDefault();
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A');
    comment.date = formatDate;
    await axios.put(`http://localhost:8080/api/v1/post/${postId}/edit-comment/${commentId}`, comment);
  };

  const loadComment = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/post/${postId}/comment/${commentId}`)
    setComment(result.data)
  }

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  return (
    <>
      <Button className="nextButton" onClick={handleShow}>
        Edytuj komentarz
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Treść
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
              Zamknij
            </Button>
            <Button type={"submit"} variant="primary" onClick={handleClose}>
              Zapisz zmiany
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}