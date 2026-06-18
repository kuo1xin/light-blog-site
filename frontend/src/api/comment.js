import request from './request'

export const getComments = (articleId) => request.get(`/comment/list/${articleId}`)
export const addComment = (data) => request.post('/comment', data)
export const deleteComment = (id) => request.delete(`/comment/${id}`)
