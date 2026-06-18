<template>
  <section class="section auth-panel">
    <h1 class="section-title">登录</h1>
    <form class="vstack gap-3" @submit.prevent="submit">
      <div>
        <label class="form-label">用户名</label>
        <input v-model.trim="form.username" class="form-control" required autocomplete="username" />
      </div>
      <div>
        <label class="form-label">密码</label>
        <input v-model="form.password" class="form-control" required type="password" autocomplete="current-password" />
      </div>
      <p v-if="error" class="text-danger mb-0">{{ error }}</p>
      <button class="btn btn-primary" type="submit" :disabled="loading">登录</button>
      <router-link class="small" to="/register">注册账号</router-link>
    </form>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const store = useUserStore()
const form = reactive({ username: '', password: '' })
const error = ref('')
const loading = ref(false)

const submit = async () => {
  error.value = ''
  loading.value = true
  try {
    await store.login(form)
    router.push('/')
  } catch (err) {
    error.value = err.response?.data?.msg || '登录失败'
  } finally {
    loading.value = false
  }
}
</script>
