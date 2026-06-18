<template>
  <div>
    <form class="row g-2 mb-3" @submit.prevent="load">
      <div class="col-md-5">
        <input v-model.trim="query.title" class="form-control" placeholder="标题" />
      </div>
      <div class="col-md-5">
        <input v-model.trim="query.author" class="form-control" placeholder="作者" />
      </div>
      <div class="col-md-2 d-grid">
        <button class="btn btn-primary" type="submit">筛选</button>
      </div>
    </form>
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th>标题</th>
            <th>作者</th>
            <th>权限</th>
            <th>评论</th>
            <th>发布时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in articles" :key="item.id">
            <td><router-link :to="`/article/${item.id}`">{{ item.title }}</router-link></td>
            <td>{{ item.authorName }}</td>
            <td>{{ item.publicFlag === 1 ? '公开' : '不公开' }}</td>
            <td>{{ item.commentEnable === 1 ? '允许' : '禁止' }}</td>
            <td>{{ formatDate(item.createTime) }}</td>
            <td><button class="btn btn-sm btn-outline-danger" type="button" @click="remove(item.id)">删除</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { deleteAdminArticle, getAdminArticles } from '@/api/admin'

const query = reactive({ title: '', author: '' })
const articles = ref([])

const load = async () => {
  const res = await getAdminArticles(query)
  articles.value = res.data || []
}

const remove = async (id) => {
  await deleteAdminArticle(id)
  articles.value = articles.value.filter((item) => item.id !== id)
}

const formatDate = (value) => value ? String(value).replace('T', ' ').slice(0, 16) : ''

onMounted(load)
</script>
