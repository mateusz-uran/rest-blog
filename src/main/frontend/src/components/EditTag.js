import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import { MdOutlineEdit} from 'react-icons/md';

export default function EditTag({ id, tagId }) {

  const [tag, setTag] = useState({
    content: ""
  });

  const { content } = tag;

  const onInputChange = (e) => {
    setTag({ ...tag, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadTag()
  }, [])


  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/post/${id}/edit-tag/${tagId}`, tag);
  };

  const loadTag = async () => {
    try {
      const result = await axios.get(`http://localhost:8080/api/v1/post/${id}/tag/${tagId}`);
      setTag(result.data);
    } catch (err) {
      console.log(err);
    }
  }

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  return (
    <>
      <MdOutlineEdit onClick={handleShow} />

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
              />
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