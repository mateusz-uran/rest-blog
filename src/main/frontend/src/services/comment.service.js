import api from './api'

const addComment = (id, comment) => {
  return api.post("/post/add-comment", comment, { params: { id: id } })
}

const getComment = (id, commentId) => {
  return api.get("/post/comment", { params: { id: id, commentId: commentId } })
}

const editComment = (id, commentId, comment) => {
  return api.put("/post/edit-comment", comment, { params: { id: id, commentId: commentId } });
}

const deleteComment = (id, commentId) => {
  return api.delete("/post/delete-comment", { params: { id: id, commentId: commentId } })
};

const CommentService = {
  addComment,
  getComment,
  editComment,
  deleteComment
};
export default CommentService;