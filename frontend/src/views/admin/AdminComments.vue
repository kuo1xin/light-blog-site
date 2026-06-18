<template>
  <div class="table-responsive">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>文章</th>
          <th>评论人</th>
          <th>内容</th>
          <th>时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in comments" :key="item.id">
          <td><router-link :to="`/article/${item.articleId}`">{{ item.articleTitle }}</router-link></td>
          <td>{{ item.nickname }}</td>
          <td>{{ item.content }}</td>
          <td>{{ formatDate(item.createTime) }}</td>
          <td><button class="btn btn-sm btn-outline-danger" type="button" @click="remove(item.id)">删除</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { deleteAdminComment, getAdminComments } from '@/api/admin'

const comments = ref([])

const load = async () => {
  const res = await getAdminComments()
  comments.value = res.data || []
}

const remove = async (id) => {
  await deleteAdminComment(id)
  comments.value = comments.value.filter((item) => item.id !== id)
}

const formatDate = (value) => value ? String(value).replace('T', ' ').slice(0, 16) : ''

onMounted(load)
</script>
