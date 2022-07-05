import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

const EditPost = () => {
  let navigate = useNavigate();

  const { id } = useParams()

  const [post, setPost] = useState({
    header: "",
    content: ""
  });

  const { header, content } = post;

  const onInputChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadPost()
  }, [])

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/api/v1/post/${id}`, post);
    navigate("/");
  };


  const loadPost = async () => {
    const result = await axios.get(`http://localhost:8080/api/v1/post/${id}`)
    setPost(result.data)
  }

  return (
    <div className="container">
      <div className="row">
        <div
          className={
            "col-md-6 offset-md-3 border rounded p-4 mt-2 shadow"
          }
        >
          <h2 className={"text-center m-4"}>Edit User</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                Name
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

            <div className={"mb-3"}>
              <label htmlFor={"Name"} className={"form-label"}>
                UserName
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

            <button type={"submit"} className={"btn btn-success"}>
              Submit
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditPost;