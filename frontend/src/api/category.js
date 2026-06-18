import request from './request'

export const getCategories = () => request.get('/category')
export const createCategory = (data) => request.post('/category', data)
export const updateCategory = (id, data) => request.put(`/category/${id}`, data)
export const deleteCategory = (id) => request.delete(`/category/${id}`)
