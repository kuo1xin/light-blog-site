import request from './request'

export const getArticleList = (params) => request.get('/article/list', { params })
export const getArticleDetail = (id) => request.get(`/article/${id}`)
export const getMyArticles = () => request.get('/article/mine')
export const createArticle = (data) => request.post('/article', data)
export const updateArticle = (id, data) => request.put(`/article/${id}`, data)
export const deleteArticle = (id) => request.delete(`/article/${id}`)
