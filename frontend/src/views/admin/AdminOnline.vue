<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-3">
      <strong>当前在线人数：{{ online.length }}</strong>
      <button class="btn btn-sm btn-outline-primary" type="button" @click="load">刷新</button>
    </div>
    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th>用户名</th>
            <th>登录时间</th>
            <th>最后活动</th>
            <th>状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in online" :key="item.id">
            <td>{{ item.username }}</td>
            <td>{{ formatDate(item.loginTime) }}</td>
            <td>{{ formatDate(item.lastActive) }}</td>
            <td>{{ item.status === 1 ? '在线' : '离线' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue'
import { getOnlineUsers } from '@/api/admin'

const online = ref([])
let timer = null

const load = async () => {
  const res = await getOnlineUsers()
  online.value = res.data || []
}

const formatDate = (value) => value ? String(value).replace('T', ' ').slice(0, 16) : ''

onMounted(() => {
  load()
  timer = window.setInterval(load, 5000)
})

onUnmounted(() => {
  if (timer) window.clearInterval(timer)
})
</script>
