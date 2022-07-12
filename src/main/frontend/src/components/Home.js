import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from 'axios'
import '../App.css';
import empty_image_post from '../images/Basic_Element_15-30_(18).jpg'
import user_basic from '../images/Basic_Ui_(186).jpg'
import { MdDeleteForever } from 'react-icons/md';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import EditCommentModal from './EditCommentModal';
import EditPostModal from './EditPostModal';
import AddComment from './AddComment';

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
    <div className='wrapper'>
      <div id='projects' className='projects'>
        <div className='post-modal'>
          <AddPostModal />
        </div>
        <h2>My projects</h2>
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
                  <i><EditPostModal postId={post.id} /></i>
                  <i><MdDeleteForever onClick={() => deletePost(post.id)} /></i>
                </div>
              </div>
              <div className='comment-button'>
                <AddComment postId={post.id} />
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

  const postHeaderLength = header?.length || 0;
  const postContentLength = content?.length || 0;
  return (
    <>
      <Button onClick={handleShow}>Add new post</Button>
      <Modal show={show} onHide={handleClose} className='add-post' centered>
        <Modal.Header closeButton></Modal.Header>
        <Modal.Body className='add-post-body'>
          <form className='form' onSubmit={(e) => onSubmit(e)}>
            <div className='form-row'>
              <label htmlFor='name' className='form-label'>
                Header
              </label>
              <input
                type={"text"}
                className={"form-control"}
                name={"header"}
                defaultValue={header || ''}
                onChange={(e) => onInputChange(e)}
                required
                maxLength={255}
              />
              <span className='character-count'>{postHeaderLength}/{255}</span>
            </div>
            <div className='form-row'>
              <label htmlFor='email' className='form-label'>
                Content
              </label>
              <textarea
                type={"text"}
                className={"form-control"}
                name={"content"}
                defaultValue={content || ''}
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
              Add post
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