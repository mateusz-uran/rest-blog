import React, { useState } from 'react';
import axios from 'axios';
import Moment from 'moment';
import '../App.css';

export default function AddComment({ postId }) {

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
    e.preventDefault();
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A')
    const defaultUser = "user";
    comment.date = formatDate;
    comment.author = defaultUser;
    await axios.post(`http://localhost:8080/api/v1/post/${postId}/add-comment`, comment);
    setComment('');
    e.target.reset();
  };

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
            />
          </div>
          <button type={"submit"}>
            Dodaj komentarz
          </button>
        </form>
      </div>
    </>
  );
}