import React, { useEffect, useState } from 'react';
import '../App.css';
import CommentService from '../services/comment.service';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from '../services/auth.service';
import EditCommentModal from './EditCommentModal';
import { MdDeleteForever } from 'react-icons/md';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';
import user_basic from '../images/Basic_Ui_(186).jpg'
import { Pagination } from '@mui/material';

export default function Comments({ postId }) {

  const [comments, setComments] = useState([]);
  const [hiddenComment, setHiddenComment] = useState(true);
  const [page, setPage] = useState(1);
  const [count, setCount] = useState(0);
  const [user, setUser] = useState();
  const pageSize = 5;

  let userId = 0;
  if (user != null) {
    userId = user.id;
  }

  const getRequestParam = (postId, page, pageSize) => {
    let params = {};
    if(postId) {
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
  useEffect(() => {
    setUser(AuthService.getCurrentUser());
    const retrieveComments = () => {
      const params = getRequestParam(postId, page, pageSize);
      CommentService.getCommentsPaginationMap(params)
      .then((response) => {
        const { comments, totalPages } = response.data;
        setComments(comments);
        setCount(totalPages);
      })
    }

    if (user != null) {
      setHiddenComment(false);
    }

    retrieveComments();
  }, [comments, postId, page])

  return (
    <>
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
          <div className='comments' key={index}>
            <div className='leftSide'>
              <img src={user_basic} alt=''></img>
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
                <EditCommentModal id={postId} commentId={comment.id} userId={userId} />
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