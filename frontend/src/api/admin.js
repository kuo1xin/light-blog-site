import request from './request'

export const getUsers = () => request.get('/admin/users')
export const updateUser = (id, data) => request.put(`/admin/user/${id}`, data)
export const deleteUser = (id) => request.delete(`/admin/user/${id}`)
export const getAdminArticles = (params) => request.get('/admin/articles', { params })
export const deleteAdminArticle = (id) => request.delete(`/admin/article/${id}`)
export const getAdminComments = () => request.get('/admin/comments')
export const deleteAdminComment = (id) => request.delete(`/admin/comment/${id}`)
export const getOnlineUsers = () => request.get('/admin/online')
