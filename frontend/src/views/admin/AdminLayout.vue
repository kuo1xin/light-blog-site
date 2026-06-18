<template>
  <section class="section">
    <h1 class="section-title">后台管理</h1>
    <nav class="admin-tabs">
      <router-link to="/admin/users">用户</router-link>
      <router-link to="/admin/articles">文章</router-link>
      <router-link to="/admin/comments">评论</router-link>
      <router-link to="/admin/online">在线用户</router-link>
    </nav>
    <router-view />
  </section>
</template>
