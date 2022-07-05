import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const AddPostTest = () => {
  let navigate = useNavigate();

  const [post, setPost] = useState({
    header: "",
    content: ""
  });

  const { header, content } = post;

  const onInputChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    await axios.post("http://localhost:8080/api/v1/post", post);
    navigate(-2)
  };

  return (
    <div className="container">
      <div className="row">
        <form className='form' onSubmit={(e) => onSubmit(e)}>
          <div className='form-row'>
            <label htmlFor='name' className='form-label'>
              Title
            </label>
            <input
              type={"text"}
              className={"form-control"}
              placeholder={"Enter your name"}
              name={"header"}
              value={header}
              onChange={(e) => onInputChange(e)}
            />
          </div>
          <div className='form-row'>
            <label htmlFor='email' className='form-label'>
              Content
            </label>
            <input
              type={"text"}
              className={"form-control"}
              placeholder={"Enter your username"}
              name={"content"}
              value={content}
              onChange={(e) => onInputChange(e)}
            />
          </div>
          <button type={"submit"} className={"btn btn-outline-primary"}>
            Submit
          </button>
          <Link className={"btn btn-outline-danger mx-2"} to={"/"}>Cancel</Link>
        </form>
      </div>
    </div>
  );
};

export default AddPostTest;