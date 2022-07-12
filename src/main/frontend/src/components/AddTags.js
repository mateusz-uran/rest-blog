import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css';

export default function AddTags({ postId, tagId }) {

  const [tags, setTags] = useState({
    tag: ""
  });

  const { tag } = tags;

  const onInputChange = (e) => {
    setTags({ ...tags, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`http://localhost:8080/api/v1/post/${postId}/add-tag`, tags);
    setTags('');
    e.target.reset();
  };

  return (
    <>
      <div className='form-wrapper'>
        <form onSubmit={(e) => onSubmit(e)}>
          <div className='comment-text'>
            <input 
            type={"text"}
            name={"tag"}
            defaultValue={tag || ''}
            onChange={(e) => onInputChange(e)}
            />
          </div>
          <button type={"submit"}>
            Add tag
          </button>
        </form>
      </div>
    </>
  );
}