import React, { useState, useEffect, useCallback } from 'react';
import '../App.css';
import axios from 'axios'
import {  useDropzone} from 'react-dropzone'
import image from '../images/Basic_Element_15-30_(18).jpg'

const client = axios.create({
  baseURL: "http://localhost:8080/api/v1/post" 
});

const Post = () => {

  const [posts, setPosts] = useState([]);

  const fetchPosts = async () => {
    let response = await client.get();
    setPosts(response.data)
  }

  const deletePost = async (id) => {
    await client.delete(`${id}`);
    setPosts(
       posts.filter((post) => {
          return post.id !== id;
       })
    );
 };

  useEffect(() => {
    fetchPosts();
  }, []);

  return posts.map((post, index) => {

    return (
      <div key={index}>
        {post.id && post.imageName != null ? <img src={`http://localhost:8080/api/v1/post/${post.id}/download`} alt="" /> : <img src={image} alt=''></img>}
        <br/>
        <br/>
        <h2>{post.header}</h2>
        <p>{post.content}</p>
        <MyDropzone postId={post.id}/>
        <br/>
        <div className="button" >
          <div className="delete-btn" onClick={() => deletePost(post.id)}>Delete</div>
        </div>
        <br/>
      </div>
    )
  })
}

function MyDropzone({postId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];

    console.log(file);

    const formData = new FormData();
    formData.append('file', file);

    axios.post(
      `http://localhost:8080/api/v1/post/${postId}/upload`,
    formData, 
    {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }
    ).then(() => {
      console.log("File uploaded successfully");
      window.location.reload()
    }).catch(err => {
      console.log(err)
    });

  });
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the files here ...</p> :
          <p>Drag 'n' drop some files here, or click to select files</p>
      }
    </div>
  )
}

export default Post