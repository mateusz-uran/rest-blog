import api from './api'

const addPost = (post) => {
  return api.post("/post", post);
};

const getPost = (id) => {
  return api.get("/post/single", { params: { id: id } })
}

const editPost = (id, post) => {
  return api.put("/post/update", post, { params: { id: id } })
}

const deletePost = (id) => {
  return api.delete("/post/delete-post", { params: { id: id } })
}

const uploadImage = (postId, file) => {
  return api.post("/post/upload", postId, file)
}

const PostService = {
  addPost,
  getPost,
  editPost,
  deletePost,
  uploadImage
};
export default PostService;