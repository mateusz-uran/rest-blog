import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from 'axios'
import '../App.css';
import image_main from '../images/undraw_hello_re_3evm.svg'
import image_about from '../images/V1228_generated.jpg'
import empty_image_post from '../images/Basic_Element_15-30_(18).jpg'
import user_basic from '../images/Basic_Ui_(186).jpg'
import { FaRegHandPeace } from "react-icons/fa";
import { Link } from "react-router-dom";
import Moment from 'moment';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import AddCommentModal from './AddCommentModal';
import EditCommentModal from './EditCommentModal';
import EditPOstModal from './EditPostModal';

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
      <div className='manage'>
        <Link className='default-link' to={"/addpost"}>Add Post</Link>
      </div>
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
          <InfoModal />
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
                  <div className="delete-btn" onClick={() => deletePost(post.id)}>Delete</div>
                  <EditPOstModal postId={post.id}/>
                </div>
                <AddCommentModal postId={post.id} />
                {
                  post.comments.map((comment, index) => (
                    <div className='comments' key={index}>
                      <div className='commentContainer'>
                        <div className='leftSide'>
                          <div className='row'>
                            <img src={user_basic} alt=''></img>
                          </div>
                        </div>
                        <div className='rightSide'>
                          <div className='row'>
                            <p>{comment.author}</p>
                            <p>{comment.date}</p>
                          </div>
                          <div className='row'><p>{comment.content}</p></div>
                        </div>
                        <EditCommentModal postId={post.id} commentId={comment.id}/>
                        <div className="delete-btn" onClick={() => deleteComment(post.id, comment.id)}>Delete</div>
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

function InfoModal() {

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
          <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Title
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
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Content
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

function AddComment({ postId }) {
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
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A')
    const defaultUser = "user";
    comment.date = formatDate;
    comment.author = defaultUser;
    await axios.post(`http://localhost:8080/api/v1/post/${postId}/add-comment`, comment);
  };

  return (
    <form onSubmit={(e) => onSubmit(e)}>
      <input
        type={"text"}
        className={"form-control"}
        placeholder={"Enter your comment"}
        name={"content"}
        value={content}
        onChange={(e) => onInputChange(e)}
      />
      <button type={"submit"} className={"btn btn-outline-primary"}>
        Submit
      </button>
    </form>
  )
}

function EditComment({ postId, commentId }) {
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
    await axios.put(`http://localhost:8080/api/v1/post/${postId}/edit-comment/${commentId}`, comment);
  };

  const loadComment = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/post/${postId}/comment/${commentId}`)
    setComment(result.data)
  }

  return (
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

      <button type={"submit"} className={"btn btn-success"}>
        Submit
      </button>
    </form>
  )
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
      window.location.reload()
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