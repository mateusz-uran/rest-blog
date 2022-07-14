import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { MdOutlineEdit, MdCheck } from 'react-icons/md'

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
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/post/${id}/edit-tag/${tagId}`, tag);
  };

  const loadTag = async () => {
    try {
      const result = await axios.get(`http://localhost:8080/api/v1/post/${id}/tag/${tagId}`);
      setTag(result.data);
    } catch (err) {
      console.log(err);
    }
  }

  const [showEditForm, setShowEditForm] = useState(true);
  const handleShow = () => setShowEditForm(!showEditForm);

  return (
    <>
      <MdOutlineEdit onClick={handleShow} className='edit-tag-icon' />

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
            <MdCheck type={"submit"} onClick={() => setShowEditForm(!showEditForm)} className='save-tag-edit' />
          </form>
        </div>
      </div>
    </>
  );
}