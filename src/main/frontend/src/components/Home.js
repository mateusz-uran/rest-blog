import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from 'axios'
import '../App.css';
import empty_image_post from '../images/Basic_Element_15-30_(18).jpg'
import user_basic from '../images/Basic_Ui_(186).jpg'
import { MdDeleteForever, MdClear, MdOutlineOpenInNew, MdOutlineEdit, MdCheck } from 'react-icons/md';
import { BsCodeSlash } from 'react-icons/bs'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button } from 'react-bootstrap';
import EditCommentModal from './EditCommentModal';
import EditPostModal from './EditPostModal';
import AddComment from './AddComment';
import AddTags from './AddTags';
import EditTag from './EditTag';

const client = axios.create({
  baseURL: "http://localhost:8080/api/v1/post/"
});

const Home = () => {

  const [posts, setPosts] = useState([]);
  const [comments, setComments] = useState([]);
  const [tags, setTags] = useState([]);

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

  const deleteTag = async (postId, id) => {
    await axios.delete(`http://localhost:8080/api/v1/post/${postId}/delete-tag/${id}`);
    setTags(
      tags.filter((tag) => {
        return tag.id !== id;
      })
    );
  };

  const [hidden, setHidden] = useState(false);

  useEffect(() => {
    fetchPosts();
  }, [posts]);

  return (
    <div className='wrapper'>
      <div className='header'>
        <h2>My projects</h2>
        <div className='post-modal'>
          {!hidden ? <AddPostModal /> : null}
        </div>
      </div>
      <div id='projects' className='projects'>
        {
          posts.map((post, index) => (
            <div className='post-wrapper' key={index}>
              <div className='postContainer' >
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
                              onClick={() => deleteTag(post.id, tag.id)}
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
                <div className='image'>
                  {post.id && post.imageName != null ? <img src={`http://localhost:8080/api/v1/post/${post.id}/download`} alt="" /> : <img src={empty_image_post} alt=''></img>}
                  {!hidden ? <MyDropzone postId={post.id} className='form-image-wrapper' /> : null}
                  {!hidden ?
                    <div className='post-icons'>
                      <i><EditPostModal id={post.id} /></i>
                      <i><MdDeleteForever onClick={() => deletePost(post.id)} /></i>
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
                          <EditCommentModal id={post.id} commentId={comment.id} />
                        </div>
                        <div className='icon'>
                          <MdDeleteForever onClick={() => deleteComment(post.id, comment.id)} />
                        </div>
                      </div>
                    </div>
                  ))
                }
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

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/api/v1/post", post);
    setPost('');
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