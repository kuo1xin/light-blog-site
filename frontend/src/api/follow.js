import request from './request'

export const toggleFollow = (data) => request.post('/follow', data)
export const getFollowList = () => request.get('/follow/list')
