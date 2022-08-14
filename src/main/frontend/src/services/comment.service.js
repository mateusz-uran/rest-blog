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

const getCommentByUser = (id, commentId, userId) => {
  return api.get("/post/comment-by-user", { params: { id: id, commentId: commentId, userId: userId }})
}

const getComments = (id) => {
  return api.get("/post/comments", {params: { id: id }});
}

const getCommentsPagination = (params) => {
  return api.get("/post/comments-page", { params });
}

const getCommentsPaginationMap = (params) => {
  return api.get("/post/comments-page-map", { params });
}

const editComment = (id, commentId, comment) => {
  return api.put("/post/edit-comment", comment, { params: { id: id, commentId: commentId } });
}

const editCommentByUser = (id, commentId, userId, comment) => {
  return api.put("/post/edit-comment-by-user", comment, { params: { id: id, commentId: commentId, userId: userId }})
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
  getComments,
  getCommentByUser,
  getCommentsPagination,
  getCommentsPaginationMap,
  editComment,
  editCommentByUser,
  deleteComment,
  deleteCommentByUser
};
export default CommentService;