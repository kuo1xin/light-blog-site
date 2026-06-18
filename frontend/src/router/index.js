import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import Home from '@/views/Home.vue'
import Login from '@/views/auth/Login.vue'
import Register from '@/views/auth/Register.vue'
import ArticleDetail from '@/views/article/ArticleDetail.vue'
import ArticleEditor from '@/views/article/ArticleEditor.vue'
import Profile from '@/views/profile/Profile.vue'
import Space from '@/views/space/Space.vue'
import AdminLayout from '@/views/admin/AdminLayout.vue'
import AdminUsers from '@/views/admin/AdminUsers.vue'
import AdminArticles from '@/views/admin/AdminArticles.vue'
import AdminComments from '@/views/admin/AdminComments.vue'
import AdminOnline from '@/views/admin/AdminOnline.vue'

const routes = [
  { path: '/', name: 'home', component: Home },
  { path: '/login', name: 'login', component: Login, meta: { guestOnly: true } },
  { path: '/register', name: 'register', component: Register, meta: { guestOnly: true } },
  { path: '/article/:id', name: 'article-detail', component: ArticleDetail },
  { path: '/write', name: 'article-new', component: ArticleEditor, meta: { requiresAuth: true } },
  { path: '/article/:id/edit', name: 'article-edit', component: ArticleEditor, meta: { requiresAuth: true } },
  { path: '/profile', name: 'profile', component: Profile, meta: { requiresAuth: true } },
  { path: '/space/:userId', name: 'space', component: Space },
  {
    path: '/admin',
    component: AdminLayout,
    meta: { requiresAuth: true, role: 'admin' },
    redirect: '/admin/users',
    children: [
      { path: 'users', component: AdminUsers },
      { path: 'articles', component: AdminArticles },
      { path: 'comments', component: AdminComments },
      { path: 'online', component: AdminOnline }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to) => {
  const store = useUserStore()
  if (store.token && !store.user) {
    await store.fetchUserInfo()
  }
  if (to.meta.requiresAuth && !store.isLoggedIn) {
    return '/login'
  }
  if (to.meta.guestOnly && store.isLoggedIn) {
    return '/'
  }
  if (to.meta.role === 'admin' && !store.isAdmin) {
    return '/'
  }
  return true
})

export default router
