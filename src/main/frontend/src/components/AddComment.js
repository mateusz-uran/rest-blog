import React, { useState } from 'react';
import Moment from 'moment';
import '../App.css';
import CommentService from '../services/comment.service';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';
import AuthService from '../services/auth.service';

export default function AddComment({ id }) {

  const [comment, setComment] = useState({
    content: "",
    date: "",
    author: ""
  });

  const { content } = comment;

  const onInputChange = (e) => {
    setComment({ ...comment, [e.target.name]: e.target.value });
  };
  
  const onSubmit = (e) => {
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A')
    const defaultUser = "user";
    comment.date = formatDate;
    // comment.author = defaultUser;
    const user = AuthService.getCurrentUser();
    if (user != null) {
      comment.author = user.username;
    }
    console.log(AuthService.getCurrentUser())
    e.preventDefault();
    CommentService.addComment(id, comment).then(
      () => {
        e.target.reset();
        setComment('');
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        console.log(resMessage);
        e.target.reset();
        setComment('');
        toast.error("Login to comment");
      }
    );
  };

  const commentLength = content?.length || 0;

  return (
    <>
      <div className='form-wrapper'>
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
            <span className='character-count'>{commentLength}/{555}</span>
          </div>
          <button type={"submit"}>
            Add comment
          </button>
        </form>
      </div>
    </>
  );
}