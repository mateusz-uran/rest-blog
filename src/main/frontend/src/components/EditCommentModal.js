import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import Moment from 'moment';
import { MdOutlineEdit } from 'react-icons/md'
import CommentService from '../services/comment.service';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';

export default function EditCommentModal({ id, commentId, userId }) {

  const [comment, setComment] = useState({
    content: ""
  });

  const { content } = comment;

  const onInputChange = (e) => {
    setComment({ ...comment, [e.target.name]: e.target.value });
  };

  const onSubmit = (e) => {
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A');
    comment.date = formatDate;
    e.preventDefault();
    CommentService.editCommentByUser(id, commentId, userId, comment).then(
      (response) => {
        e.target.reset();
        setComment(response.data)
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

  const loadComment = () => {
    CommentService.getCommentByUser(id, commentId, userId).then(
      (response) => {
        setComment(response.data);
        setShow(true)
      },
      (error) => {
        setShow(false)
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        console.log(resMessage);
        if (error.response.status === 500) {
          toast.error("You can't edit someone else comment!");
        }
      }
    );
  };

  useEffect(() => {
  }, [])

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const commentContentLength = content?.length || 0;

  return (
    <>
      <MdOutlineEdit onClick={() => loadComment()} />

      <Modal show={show} onHide={handleClose} className='edit-comment' centered>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className='comment-text'>
              <textarea
                type={"text"}
                name={"content"}
                defaultValue={content || ''}
                onChange={(e) => onInputChange(e)}
                required
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