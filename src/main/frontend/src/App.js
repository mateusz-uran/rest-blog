import React, {useState, useEffect} from 'react';
import './App.css';
import axios from 'axios'

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
        <h1>{post.header}</h1>
        <p>{post.content}</p>
      </div>
    )
  })
};

function App() {
  return (
    <div className="App">
      <Post />
    </div>
  );
}

export default App;
