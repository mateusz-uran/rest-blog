import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import { MdOutlineEdit } from 'react-icons/md';
import PostService from '../services/post.service';


export default function EditPostModal({ id, setFetchedPosts }) {

  const [post, setPost] = useState({
    header: "",
    intro: "",
    content: "",
    projectCodeLink: "",
    projectDemoLink: ""
  });

  const { header, intro, content, projectCodeLink, projectDemoLink } = post;

  const onInputChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const onSubmit = (e) => {
    e.preventDefault();
    PostService.editPost(id, post).then(
      (response) => {
        e.target.reset();
        setPost(response.data)
        handleClose();
        setFetchedPosts(true);
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

  const loadPost = () => {
    PostService.getPost(id).then(
      (response) => {
        setPost(response.data);
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

  useEffect(() => {
    loadPost()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const postHeaderLength = header?.length || 0;
  const postIntroLength = intro?.length || 0;
  const postContentLength = content?.length || 0;
  return (
    <>
      <MdOutlineEdit onClick={handleShow}/>

      <Modal show={show} onHide={handleClose} className='edit-post' centered>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
        <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Header
              </label>
              <textarea
                type={"text"}
                className={"form-header"}
                name={"header"}
                defaultValue={header || ''}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={255}
              />
              <span className='character-count'>{postHeaderLength}/{255}</span>
            </div>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Intro
              </label>
              <textarea
                type={"text"}
                className={"form-intro"}
                name={"intro"}
                defaultValue={intro || ''}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={255}
              />
              <span className='character-count'>{postIntroLength}/{255}</span>
            </div>
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Content
              </label>
              <textarea
                type={"text"}
                className={"form-content"}
                name={"content"}
                defaultValue={content || ''}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={555}
              />
              <span className='character-count'>{postContentLength}/{555}</span>
            </div>
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Project source code link
              </label>
              <input
                type={"url"}
                className={"form-content-input"}
                name={"projectCodeLink"}
                defaultValue={projectCodeLink || ''}
                onChange={(e) => onInputChange(e)}
              />
              <label htmlFor='email' className='form-label'>
                Project demo link
              </label>
              <input
                type={"url"}
                className={"form-content-input"}
                name={"projectDemoLink"}
                defaultValue={projectDemoLink || ''}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <Button onClick={handleClose} className='close' >
              Close
            </Button>
            <Button type={"submit"} className='submit' >
              Save changes
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}