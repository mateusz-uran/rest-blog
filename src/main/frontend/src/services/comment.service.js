import api from './api'

const addCommentByUser = (id, userId, comment) => {
  return api.post("/post/add-comment-by-user", comment, { params: { id: id, userId: userId } });
}

const getCommentByUser = (id, commentId, userId) => {
  return api.get("/post/comment-by-user", { params: { id: id, commentId: commentId, userId: userId }})
}

const getCommentsPaginationMap = (params) => {
  return api.get("/post/comments-page-map", { params });
}

const editCommentByUser = (id, commentId, userId, comment) => {
  return api.put("/post/edit-comment-by-user", comment, { params: { id: id, commentId: commentId, userId: userId }})
}

const deleteCommentByUser = (id, commentId, userId) => {
  return api.delete("/post/delete-comment-by-user", { params: { id: id, commentId: commentId, userId: userId }})
}

const CommentService = {
  addCommentByUser,
  getCommentByUser,
  getCommentsPaginationMap,
  editCommentByUser,
  deleteCommentByUser
};
export default CommentService;