import React, { useState, useEffect } from 'react';
import { MdOutlineEdit, MdCheck } from 'react-icons/md';
import TagsService from '../services/tags.service';

export default function EditTag({ id, tagId, setFetchedPosts }) {

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

  const onSubmit = (e) => {
    e.preventDefault();
    TagsService.editTag(id, tagId, tag).then(
      (response) => {
        e.target.reset();
        setTag(response.data)
        setFetchedPosts(true);
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

  const loadTag = () => {
    TagsService.getTag(id, tagId).then(
      (response) => {
        setTag(response.data);
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