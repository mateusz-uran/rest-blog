import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { MdOutlineEdit, MdCheck } from 'react-icons/md';
import authHeader from '../services/auth-header';

export default function EditTag({ id, tagId }) {

  const [tag, setTag] = useState({
    content: ""
  });

  const { content } = tag;

  const onInputChange = (e) => {
    setTag({ ...tag, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadTag()
  }, [])


  const onSubmit = async (e) => {
    try {
      e.preventDefault();
      axios({
        method: 'put',
        url: "http://localhost:8080/api/v1/post/edit-tag",
        data: tag,
        headers: authHeader(),
        params: { id, tagId }
      });
    } catch (error) {
      console.log(error)
    }
  };

  const loadTag = async () => {
    try {
      const result = await axios({
        method: 'get',
        url: "http://localhost:8080/api/v1/post/tag",
        headers: authHeader(),
        params: { id, tagId }
      });
      setTag(result.data);
    } catch (error) {
      console.log(error)
    }
  }

  const [showEditForm, setShowEditForm] = useState(true);
  const handleShow = () => setShowEditForm(!showEditForm);

  return (
    <>
      <MdOutlineEdit onClick={handleShow} className='edit-tag-icon'/>

      <div id={showEditForm ? 'hidden' : ''} className='tag-form-wrapper'>
        <div>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className='comment-text'>
              <input
                type={"text"}
                name={"content"}
                defaultValue={content || ''}
                onChange={(e) => onInputChange(e)}
                className='edit-tag-input'
                required
              />
            </div>
            <button type={"submit"} onClick={() => setShowEditForm(!showEditForm)}><MdCheck  /></button>
          </form>
        </div>
      </div>
    </>
  );
}