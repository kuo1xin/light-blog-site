import { defineStore } from 'pinia'
import { getUserInfoApi, loginApi, logoutApi } from '@/api/user'

const savedUser = localStorage.getItem('user')

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: savedUser ? JSON.parse(savedUser) : null
  }),
  getters: {
    isLoggedIn: (state) => Boolean(state.token),
    isAdmin: (state) => state.user?.role === 'admin'
  },
  actions: {
    async login(form) {
      this.clearSession()
      const res = await loginApi(form)
      this.token = res.data.token
      this.user = res.data.user
      localStorage.setItem('token', this.token)
      localStorage.setItem('user', JSON.stringify(this.user))
    },
    async fetchUserInfo() {
      if (!this.token) return
      try {
        const res = await getUserInfoApi()
        this.user = res.data
        localStorage.setItem('user', JSON.stringify(this.user))
      } catch (error) {
        this.clearSession()
        throw error
      }
    },
    async logout() {
      try {
        await logoutApi()
      } finally {
        this.clearSession()
      }
    },
    clearSession() {
      this.token = ''
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }
})
