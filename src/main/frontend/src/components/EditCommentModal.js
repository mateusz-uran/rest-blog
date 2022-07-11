import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import Moment from 'moment';
import { MdOutlineEdit } from 'react-icons/md'

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

  const commentContentLength = content.length;
  return (
    <>
      <MdOutlineEdit onClick={handleShow}/>

      <Modal show={show} onHide={handleClose} className='edit-comment' centered>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Content
              </label>
              <textarea
                type={"text"}
                className={"form-control"}
                placeholder={"Enter your name"}
                name={"content"}
                value={content}
                onChange={(e) => onInputChange(e)}
                maxLength={555}
              />
              <span className='character-count'>{commentContentLength}/{555}</span>
            </div>

            <Button onClick={handleClose} className='close' >
              Close
            </Button>
            <Button type={"submit"} onClick={handleClose} className='submit' >
              Save changes
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}