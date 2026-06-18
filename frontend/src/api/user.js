import request from './request'

export const loginApi = (data) => request.post('/user/login', data)
export const registerApi = (data) => request.post('/user/register', data)
export const logoutApi = () => request.post('/user/logout')
export const getUserInfoApi = () => request.get('/user/info')
export const updateProfileApi = (data) => request.put('/user/profile', data)
export const changePasswordApi = (data) => request.put('/user/password', data)
export const getStatsApi = () => request.get('/user/stats')
