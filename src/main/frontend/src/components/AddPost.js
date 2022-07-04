import { useState } from 'react';
import '../App.css';
import axios from'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Modal, Button} from 'react-bootstrap';
const url = 'http://localhost:8080/api/v1/post';

const AddPost = () => {

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [header, setHeader] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async (e) => {
    try {
      const resp = await axios.post(url, {header, content});
      console.log(resp.data);
    } catch (error) {
      console.log(error.response);
    }
  }

  return (
    <section className='AddPost'>
      <div className='wrapper'>
        <Button className="postModal" onClick={handleShow}>
          Dodaj post
        </Button>
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>post request</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <form className='form' onSubmit={handleSubmit}>
              <div className='form-row'>
                <label htmlFor='name' className='form-label'>
                  Title
                </label>
                <input
                  type='text'
                  className='form-input'
                  id='header'
                  value={header}
                  onChange={(e) => setHeader(e.target.value)}
                />
              </div>
              <div className='form-row'>
                <label htmlFor='email' className='form-label'>
                  Content
                </label>
                <input
                  type='text'
                  className='form-input'
                  id='content'
                  value={content}
                  onChange={(e) => setContent(e.target.value)}
                />
              </div>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
              <Button variant="primary" type='submit' onClick={handleClose}>
                Save Changes
              </Button>
            </form>
          </Modal.Body>
        </Modal>
      </div>
    </section>
  );
};

export default AddPost;