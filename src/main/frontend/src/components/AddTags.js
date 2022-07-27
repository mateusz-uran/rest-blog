import React, { useState } from 'react';
import '../App.css';
import { AiOutlinePlus } from 'react-icons/ai';
import TagsService from '../services/tags.service';

export default function AddTags({ id, tagId }) {

  const [tags, setTags] = useState({
    content: ""
  });

  const { content } = tags;

  const onInputChange = (e) => {
    setTags({ ...tags, [e.target.name]: e.target.value });
  };

  const onSubmit = (e) => {
    e.preventDefault();
    TagsService.addTag(id, tags).then(
      (response) => {
        e.target.reset();
      setTags('');
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