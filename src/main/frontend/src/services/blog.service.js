import api from './api'

const getPublicContent = () => {
  return api.get("/test/all");
};

const getSecuredContent = () => {
  return api.get("/test");
};

const BlogService = {
  getPublicContent,
  getSecuredContent
};
export default BlogService;