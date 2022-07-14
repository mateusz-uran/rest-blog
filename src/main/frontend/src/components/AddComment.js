import React, { useState } from 'react';
import axios from 'axios';
import Moment from 'moment';
import '../App.css';

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

  const onSubmit = async (e) => {
    e.preventDefault();
    const formatDate = Moment().format('DD-MM-YYYY, h:mm A')
    const defaultUser = "user";
    comment.date = formatDate;
    comment.author = defaultUser;
    await axios.post(`http://localhost:8080/api/v1/post/${id}/add-comment`, comment);
    setComment('');
    e.target.reset();
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