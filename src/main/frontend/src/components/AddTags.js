import React, { useState } from 'react';
import axios from 'axios';
import '../App.css';
import { AiOutlinePlus } from 'react-icons/ai';
import authHeader from '../services/auth-header';

export default function AddTags({ id, tagId }) {

  const [tags, setTags] = useState({
    content: ""
  });

  const { content } = tags;

  const onInputChange = (e) => {
    setTags({ ...tags, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    try {
      e.preventDefault();
      axios({
        method: 'post',
        url: "http://localhost:8080/api/v1/add-tag",
        data: tags,
        headers: authHeader(),
        params: { id }
      });
      e.target.reset();
      setTags('');
    } catch (error) {
      console.log(error)
    }
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
              required
            />
          </div>
          <i><button type={"submit"} className='tag-button'><AiOutlinePlus /></button></i>
        </form>
      </div>
    </>
  );
}