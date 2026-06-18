<template>
  <section class="section auth-panel">
    <h1 class="section-title">注册</h1>
    <form class="vstack gap-3" @submit.prevent="submit">
      <div>
        <label class="form-label">用户名</label>
        <input v-model.trim="form.username" class="form-control" required minlength="3" maxlength="24" />
      </div>
      <div>
        <label class="form-label">昵称</label>
        <input v-model.trim="form.nickname" class="form-control" required />
      </div>
      <div>
        <label class="form-label">邮箱</label>
        <input v-model.trim="form.email" class="form-control" type="email" />
      </div>
      <div>
        <label class="form-label">密码</label>
        <input v-model="form.password" class="form-control" required type="password" minlength="6" />
      </div>
      <p v-if="error" class="text-danger mb-0">{{ error }}</p>
      <button class="btn btn-primary" type="submit" :disabled="loading">注册</button>
      <router-link class="small" to="/login">已有账号，去登录</router-link>
    </form>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerApi } from '@/api/user'

const router = useRouter()
const form = reactive({ username: '', nickname: '', email: '', password: '' })
const error = ref('')
const loading = ref(false)

const submit = async () => {
  error.value = ''
  loading.value = true
  try {
    await registerApi(form)
    router.push('/login')
  } catch (err) {
    error.value = err.response?.data?.msg || '注册失败'
  } finally {
    loading.value = false
  }
}
</script>
