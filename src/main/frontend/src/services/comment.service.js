import api from './api'

const addComment = (id, comment) => {
  return api.post("/post/add-comment", comment, { params: { id: id } });
}

const addCommentByUser = (id, userId, comment) => {
  return api.post("/post/add-comment-by-user", comment, { params: { id: id, userId: userId } });
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

const deleteCommentByUser = (id, commentId, userId) => {
  return api.delete("/post/delete-comment-by-user", { params: { id: id, commentId: commentId, userId: userId }})
}

const CommentService = {
  addComment,
  addCommentByUser,
  getComment,
  editComment,
  deleteComment,
  deleteCommentByUser
};
export default CommentService;