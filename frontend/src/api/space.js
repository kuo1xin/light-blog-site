import request from './request'

export const getSpaceInfo = (userId) => request.get(`/space/${userId}`)
export const saveSpaceInfo = (data) => request.put('/space', data)
export const getSpaceArticles = (userId) => request.get(`/space/${userId}/articles`)
