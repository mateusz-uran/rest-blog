import React, { useState } from "react";
import { EditorState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import '../App.css';
import axios from 'axios';
import Moment from 'moment';

const WYSIWYG = ({ postId }) => {
  const [editorState, setEditorState] = React.useState(
    () => EditorState.createEmpty(),
  );

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
    <div className="editor">
      <div className="wrapper">
        <Editor
          editorState={editorState}
          toolbarClassName="toolbarClassName"
          wrapperClassName="wrapperClassName"
          editorClassName="editorClassName"
          onEditorStateChange={setEditorState}
          defaultValue={content || ''}
          onChange={(e) => onInputChange(e)}
        />
      </div>
    </div>
  );

}

export default WYSIWYG