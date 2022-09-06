import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from 'axios'
import EditPostModal from './EditPostModal';
import AddComment from './AddComment';
import AddTags from './AddTags';
import EditTag from './EditTag';
import Comments from './Comments';
import AuthService from '../services/auth.service';
import PostService from '../services/post.service';
import TagsService from '../services/tags.service';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../App.css';
import empty_image_post from '../images/Basic_Element_15-30_(18).jpg'
import { MdDeleteForever, MdClear, MdOutlineOpenInNew } from 'react-icons/md';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import { BsCodeSlash } from 'react-icons/bs'

import AOS from 'aos';
import 'aos/dist/aos.css';

const client = axios.create({
  baseURL: "http://localhost:8080/api/v1/post"
});

const Home = () => {

  const [posts, setPosts] = useState([]);
  const [tags, setTags] = useState([]);
  const [user, setUser] = useState();

  const [hidden, setHidden] = useState(true);

  const deletePostByParam = async (id) => {
    PostService.deletePost(id).then(
      () => {
        setPosts(
          posts.filter((post) => {
            return post.id !== id;
          })
        );
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
  }

  const deleteTagByParam = async (id, tagId) => {
    TagsService.deleteTag(id, tagId).then(
      () => {
        setTags(
          tags.filter((tag) => {
            return tag.id !== id;
          })
        );
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
  }

  useEffect(() => {
    const fetchPosts = async () => {
      let response = await client.get("/all");
      setPosts(response.data);
    }
    setUser(AuthService.getCurrentUser());
    if (user != null && user.roles.includes("ROLE_ADMIN")) {
      setHidden(false);
    }
    fetchPosts();
  }, [posts]);

  return (
    <div className='wrapper'>
      <ToastContainer />
      <div className='header'>
        <h2 data-aos='fade-right'>My projects</h2>
        <div className='post-modal'>
          {!hidden ? <AddPostModal /> : null}
        </div>
      </div>
      <div id='projects' className='projects'>
        {
          posts.map((post, index) => (
            <div className='post-wrapper' key={index}>
              <div data-aos='fade-left' className='postContainer' >
                <div className='text'>
                  <h3>{post.header}</h3>
                  <p className='intro'>{post.intro}</p>
                  <p className='content'>{post.content}</p>
                  <div className='tags-wrapper'>
                    {
                      post.tags.map((tag, index) => (
                        <div className='tags' key={index}>
                          <span className='tag-content'>{tag.content}</span>
                          {!hidden ?
                            <i
                              onClick={() => deleteTagByParam(post.id, tag.id)}
                            >
                              <MdClear className='delete-tag-icon' />
                            </i> : null}
                          {!hidden ?
                            <i className='edit-tag'>
                              <EditTag id={post.id} tagId={tag.id} />
                            </i> : null}
                        </div>
                      ))
                    }
                  </div>
                </div>
                <div className='image' >
                  {post.id && post.imageName != null ?
                    <img src={`http://localhost:8080/api/v1/post/${post.id}/download`} alt="" />
                    : <img src={empty_image_post} alt=''></img>}
                  {!hidden ? <MyDropzone postId={post.id} className='form-image-wrapper' /> : null}
                  {!hidden ?
                    <div className='post-icons'>
                      <i><EditPostModal id={post.id} /></i>
                      <i><MdDeleteForever onClick={() => deletePostByParam(post.id)} /></i>
                    </div> : null}
                  <div className='project-links'>
                    <a href={post.projectCodeLink} target='_blank' rel='noopener noreferrer' className={post.projectCodeLink === null ? 'inactive' : ''}>
                      <i><BsCodeSlash className='project-icon' /></i>
                    </a>
                    <a href={post.projectDemoLink} target='_blank' rel='noopener noreferrer' className={post.projectCodeLink === null ? 'inactive' : ''}>
                      <i><MdOutlineOpenInNew className='project-icon' /></i>
                    </a>
                  </div>
                </div>
                <div className='comment-button'>
                  {!hidden ? <AddTags id={post.id} className='add-tags' /> : null}
                  <AddComment id={post.id} />
                </div>
                <div data-aos='fade-right' className='comments-wrapper'>
                  <Comments postId={post.id} />
                </div>
              </div>
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
    PostService.addPost(post).then(
      () => {
        e.preventDefault();
        setPost(post);
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

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const postHeaderLength = header?.length || 0;
  const postIntroLength = intro?.length || 0;
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
    formData.append('postId', postId);

    PostService.uploadImage(formData).then(
      () => {
        console.log("uploaded");
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