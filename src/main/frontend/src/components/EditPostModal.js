import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';

export default function EditPOstModal({ postId }) {

  const [post, setPost] = useState({
    header: "",
    content: ""
  });

  const { header, content } = post;

  const onInputChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadPost()
  }, [])

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/post/${postId}`, post);
  };


  const loadPost = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/post/${postId}`)
    setPost(result.data)
  }

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
                name={"header"}
                value={header}
                onChange={(e) => onInputChange(e)}
              />
            </div>

            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                UserName
              </label>
              <input
                type={"text"}
                className={"form-control"}
                placeholder={"Enter your username"}
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