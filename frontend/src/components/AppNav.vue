<template>
  <nav class="navbar navbar-expand-lg topbar">
    <div class="container-fluid app-shell py-0">
      <router-link class="navbar-brand d-flex align-items-center gap-2" to="/">
        <span class="brand-mark">轻</span>
        <span>轻博客小站</span>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div id="mainNav" class="collapse navbar-collapse">
        <div class="navbar-nav ms-auto align-items-lg-center gap-lg-2">
          <router-link class="nav-link" to="/">首页</router-link>
          <router-link v-if="store.isLoggedIn" class="nav-link" to="/write">写文章</router-link>
          <router-link v-if="store.isLoggedIn" class="nav-link" to="/profile">个人中心</router-link>
          <router-link v-if="store.isAdmin" class="nav-link" to="/admin">后台管理</router-link>
          <router-link v-if="!store.isLoggedIn" class="btn btn-sm btn-outline-primary ms-lg-2" to="/login">登录</router-link>
          <router-link v-if="!store.isLoggedIn" class="btn btn-sm btn-primary" to="/register">注册</router-link>
          <button v-else class="btn btn-sm btn-outline-secondary" type="button" @click="logout">
            退出 {{ store.user?.nickname }}
          </button>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const store = useUserStore()

const logout = async () => {
  await store.logout()
  router.push('/')
}
</script>
