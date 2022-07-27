import api from './api'

const getPublicContent = () => {
  return api.get("/test/all");
};

const getSecuredContent = () => {
  return api.get("/test");
};

const addTag = (id, tags) => {
  return api.post("/add-tag", tags, {params: {id: id}} );
};

const editTag = (id, tagId, tags) => {
  return api.put("/edit-tag", tags, {params: {id: id, tagId: tagId}});
}

const getTag = (id, tagId) => {
  return api.get("/tag", {params: {id: id, tagId: tagId}})
}

const deleteTag = (id, tagId) => {
  return api.delete("/delete-tag", {
    params: {
      id: id,
      tagId: tagId
    }
  })
};

const addPost = (header, intro, content, projectCodeLink, projectDemoLink) => {
  return api.post("/post", {
    header,
    intro,
    content,
    projectCodeLink,
    projectDemoLink
  });
};

const deletePost = (id) => {
  return api.delete("/post/delete-post", {
    params: {
      id: id
    }
  })
}

const BlogService = {
  getPublicContent,
  getSecuredContent,
  deleteTag,
  addTag,
  editTag,
  getTag,
  addPost,
  deletePost,
};
export default BlogService;