import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css';
import { AiOutlinePlus } from 'react-icons/ai';

export default function AddTags({ postId, tagId }) {

  const [tags, setTags] = useState({
    content: ""
  });

  const { content } = tags;

  const onInputChange = (e) => {
    setTags({ ...tags, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post(`http://localhost:8080/api/v1/post/${postId}/add-tag`, tags);
    console.log(tags)
    setTags('');
    e.target.reset();
  };

  return (
    <>
      <div className='form-wrapper'>
        <form onSubmit={(e) => onSubmit(e)} className='form-tag'>
          <div className='tag-text'>
            <input 
            type={"text"}
            name={"content"}
            defaultValue={content || ''}
            onChange={(e) => onInputChange(e)}
            />
          </div>
          <i><button type={"submit"} className='tag-button'><AiOutlinePlus/></button></i>
        </form>
      </div>
    </>
  );
}