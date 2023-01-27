import React, { useEffect, useState } from 'react';
import CommentService from '../services/comment.service';
import AuthService from '../services/auth.service';
import EditCommentModal from './EditCommentModal';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';
import { Pagination } from '@mui/material';
import { BigHead } from '@bigheads/core'
import { MdDeleteForever } from 'react-icons/md';
import '../App.css';
import AddComment from './AddComment';

export default function Comments({ postId }) {

  const [comments, setComments] = useState([]);
  const [hiddenComment, setHiddenComment] = useState(false);
  const [page, setPage] = useState(1);
  const [count, setCount] = useState(0);
  const [user, setUser] = useState('');
  const pageSize = 5;

  let userId = 0;
  if (user != null) {
    userId = user.id;
  }

  const getRequestParam = (postId, page, pageSize) => {
    let params = {};
    if (postId) {
      params["id"] = postId;
    }
    if (page) {
      params["page"] = page - 1;
    }
    if (pageSize) {
      params["size"] = pageSize;
    }
    return params;
  }

  const deleteCommentByUser = async (postId, commentId, userId) => {
    CommentService.deleteCommentByUser(postId, commentId, userId).then(
      () => {
        setComments(
          comments.filter((comment) => {
            return comment.id !== commentId;
          })
        );
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        console.log(resMessage);
        if (error.response.status === 500) {
          toast.error("You can't delete someone else comment!");
        }
      }
    );
  }

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const [fetchedComments, setFetchedComments] = useState(true)

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    const retrieveComments = () => {
      const params = getRequestParam(postId, page, pageSize);
      CommentService.getCommentsPaginationMap(params)
        .then((response) => {
          const { totalPages, comments } = response.data;
          setComments(comments);
          setCount(totalPages);
        })
    }

    if (user === null) {
      setHiddenComment(true);
    }
    retrieveComments();
    setFetchedComments(false);
    setUser(user);
  }, [fetchedComments, postId, page])

  return (
    <>
      <div className='add-comment'>
        <AddComment id={postId} setFetchedComments={setFetchedComments} />
      </div>
      <div className="comment-pagination">
        <Pagination
          className="my-3"
          count={count}
          page={page}
          siblingCount={1}
          boundaryCount={1}
          variant="outlined"
          shape="rounded"
          onChange={handlePageChange}
        />
      </div>
      {
        comments.map((comment, index) => (
          <div id='comments' key={index}>
            <div className='leftSide'>
              <BigHead {...JSON.parse(comment.authorAvatar)} className='image' />
            </div>
            <div className='rightSide'>
              <div className='row'>
                <p>{comment.author}&nbsp;&nbsp;{comment.date}</p>
              </div>
              <div className='row'>
                <p>{comment.content}</p>
              </div>
            </div>
            {!hiddenComment ? <div className='side'>
              <div className='icon'>
                <EditCommentModal id={postId} commentId={comment.id} userId={userId} setFetchedComments={setFetchedComments} />
              </div>
              <div className='icon'>
                <MdDeleteForever onClick={() => deleteCommentByUser(postId, comment.id, userId)} />
              </div>
            </div> : null}
          </div>
        ))
      }
    </>
  );
}