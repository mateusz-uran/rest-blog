import React, {useState, useEffect, useCallback} from 'react';
import './App.css';
import axios from 'axios'
import {useDropzone} from 'react-dropzone'

const Post = () => {

  const [posts, setPosts] = useState([]);

  const fetchPosts = () => {
    axios.get("http://localhost:8080/api/v1/post").then(res => {
      console.log(res);
      setPosts(res.data);
    });
  }

  useEffect(() => {
    fetchPosts();
  }, []);

  return posts.map((post, index) => {

    return (
      <div key={index}>
        {post.id ? <img src={`http://localhost:8080/api/v1/post/${post.id}/download`} alt="" /> : null}
        {/*todo: post image*/}
        <br/>
        <br/>
        <h1>{post.header}</h1>
        <p>{post.content}</p>
        {/* <MyDropzone {...post}/> */}
        <MyDropzone postId={post.id}/>
        <br/>
      </div>
    )
  })
};

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

  }, []);
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

function App() {
  return (
    <div className="App">
      <Post />
    </div>
  );
}

export default App;
