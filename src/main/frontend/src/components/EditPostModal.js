import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import { MdOutlineEdit } from 'react-icons/md'

export default function EditPostModal({ postId }) {

  const [post, setPost] = useState({
    header: "",
    content: ""
  });

  const { header, content } = post;

  const onInputChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/post/${postId}`, post);
  };


  const loadPost = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/post/${postId}`)
    setPost(result.data)
  }

  useEffect(() => {
    loadPost()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const postHeaderLength = header?.length || 0;
  const postContentLength = content?.length || 0;
  return (
    <>
      <MdOutlineEdit onClick={handleShow}/>

      <Modal show={show} onHide={handleClose} className='edit-post' centered>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Header
              </label>
              <input
                type={"text"}
                className={"form-control"}
                name={"header"}
                value={header}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={255}
              />
              <span className='character-count'>{postHeaderLength}/{255}</span>
            </div>

            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Content
              </label>
              <textarea
                type={"text"}
                className={"form-control"}
                name={"content"}
                value={content}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={555}
              />
              <span className='character-count'>{postContentLength}/{555}</span>
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