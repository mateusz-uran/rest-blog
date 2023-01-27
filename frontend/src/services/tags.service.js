import api from './api'

const addTag = (id, tags) => {
  return api.post("/add-tag", tags, { params: { id: id } });
};

const getTag = (id, tagId) => {
  return api.get("/tag", { params: { id: id, tagId: tagId } });
};

const editTag = (id, tagId, tags) => {
  return api.put("/edit-tag", tags, { params: { id: id, tagId: tagId } });
};

const deleteTag = (id, tagId) => {
  return api.delete("/delete-tag", { params: { id: id, tagId: tagId } });
};

const TagsService = {
  addTag,
  getTag,
  editTag,
  deleteTag
};
export default TagsService;