import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from 'axios'
import '../App.css';
import image_main from '../images/undraw_hello_re_3evm.svg'
import image_about from '../images/V1228_generated.jpg'
import empty_image_post from '../images/Basic_Element_15-30_(18).jpg'
import user_basic from '../images/Basic_Ui_(186).jpg'
import { FaRegHandPeace } from "react-icons/fa";
import { MdDeleteForever } from 'react-icons/md';
import { AiOutlinePlusCircle } from 'react-icons/ai';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AddCommentModal from './AddCommentModal';
import EditCommentModal from './EditCommentModal';
import EditPostModal from './EditPostModal';

const client = axios.create({
  baseURL: "http://localhost:8080/api/v1/post/"
});

const Home = () => {

  const [posts, setPosts] = useState([]);
  const [comments, setComments] = useState([]);

  const fetchPosts = async () => {
    let response = await client.get();
    setPosts(response.data);
  }

  const deletePost = async (id) => {
    await client.delete(`${id}`);
    setPosts(
      posts.filter((post) => {
        return post.id !== id;
      })
    );
  };

  const deleteComment = async (postId, id) => {
    await axios.delete(`http://localhost:8080/api/v1/post/${postId}/delete-comment/${id}`);
    setComments(
      comments.filter((comment) => {
        return comment.id !== id;
      })
    );
  };

  useEffect(() => {
    fetchPosts();
  }, [posts]);

  return (
    <div className='Home'>
      <div className='wrapper'>
        <div className='main'>
          <div className='leftSide'>
            <p>Hello there <FaRegHandPeace fill='#007FFF' />, I'm</p>
            <h1>Mateusz Uranowski</h1>
            <p>and I'm a web developer.</p>
          </div>
          <div className='rightSide'>
            <img src={image_main} alt=''></img>
          </div>
        </div>
      </div>
      <div className='wrapper'>
        <div className='about'>
          <div className='leftSide'><img src={image_about} alt=''></img></div>
          <div className='rightSide'>
            <div className='line'>&nbsp;</div>
            <div className='content'>
              <h2>About Me</h2>
              <p>My name is Mateusz and I'm 25. I've graduated at the beggining of 2022 IT studies
                with specialization for web development.
              </p>
              <div>
                <h4>Technologies and Languages I'm using</h4>
                <ul>
                  <li>Java 11</li>
                  <li>Spring Boot</li>
                  <li>AWS
                    <ul>
                      <li>S3 Bucket</li>
                      <li>Elastik Beanstalk</li>
                      <li>Amplify</li>
                    </ul>
                  </li>
                  <li>HTML</li>
                  <li>CSS</li>
                  <li>JavaScript - basics</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className='wrapper'>
        <div className='projects'>
          <div className='post-modal'>
            <AddPostModal />
          </div>
          {
            posts.map((post, index) => (
              <div className='postContainer' key={index}>
                <div className='text'>
                  <h3>{post.header}</h3>
                  <p>{post.content}</p>
                </div>
                <div className='image'>
                  {post.id && post.imageName != null ? <img src={`http://localhost:8080/api/v1/post/${post.id}/download`} alt="" /> : <img src={empty_image_post} alt=''></img>}
                  <MyDropzone postId={post.id} />
                  <div className='post-icons'>
                    <EditPostModal postId={post.id} />
                    <MdDeleteForever onClick={() => deletePost(post.id)}/>
                  </div>
                </div>
                <div className='comment-button'>
                  <AddCommentModal postId={post.id} />
                </div>
                {
                  post.comments.map((comment, index) => (
                    <div className='comments' key={index}>
                      <div className='leftSide'>
                        <img src={user_basic} alt=''></img>
                      </div>
                      <div className='rightSide'>
                        <div className='row'>
                          <p>{comment.author}&nbsp;&nbsp;{comment.date}</p>
                        </div>
                        <div className='row'>
                          <p>{comment.content}</p>
                        </div>
                      </div>
                      <div className='side'>
                        <div className='icon'>
                          <EditCommentModal postId={post.id} commentId={comment.id} />
                        </div>
                        <div className='icon'>
                          <MdDeleteForever onClick={() => deleteComment(post.id, comment.id)} />
                        </div>
                      </div>
                    </div>
                  ))
                }
              </div>
            ))
          }
        </div>
      </div>
    </div>
  )
}

function AddPostModal() {

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
    await axios.post("http://localhost:8080/api/v1/post", post);
    setPost('');
  };

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  return (
    <>
      <AiOutlinePlusCircle onClick={handleShow} />
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Nagłówek
              </label>
              <input
                type={"text"}
                className={"form-control"}
                name={"header"}
                value={header}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Treść posta
              </label>
              <input
                type={"text"}
                className={"form-control"}
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

function MyDropzone({ postId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];

    console.log(file);

    const formData = new FormData();
    formData.append('file', file);

    axios.post(
      `http://localhost:8080/api/v1/post/${postId}/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("File uploaded successfully");
    }).catch(err => {
      console.log(err)
    });

  });
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop })

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the files here ...</p> :
          <p>Drag 'n' drop some files here, or click to select files</p>
      }
    </div>
  )
}

export default Home