<template>
  <section class="section">
    <h1 class="section-title">{{ isEdit ? '编辑文章' : '发布文章' }}</h1>
    <form class="vstack gap-3" @submit.prevent="submit">
      <div>
        <label class="form-label">标题</label>
        <input v-model.trim="form.title" class="form-control" required maxlength="120" />
      </div>
      <div class="row g-3">
        <div class="col-md-4">
          <label class="form-label">分类</label>
          <select v-model="form.categoryId" class="form-select">
            <option :value="null">未分类</option>
            <option v-for="item in categories" :key="item.id" :value="item.id">{{ item.name }}</option>
          </select>
        </div>
        <div class="col-md-8">
          <label class="form-label">标签</label>
          <input v-model.trim="form.tags" class="form-control" placeholder="用英文逗号分隔" />
        </div>
      </div>
      <div>
        <label class="form-label">内容</label>
        <textarea v-model.trim="form.content" class="form-control" rows="14" required></textarea>
      </div>
      <div class="d-flex flex-wrap gap-4">
        <div class="form-check form-switch">
          <input id="publicFlag" v-model="publicChecked" class="form-check-input" type="checkbox" />
          <label class="form-check-label" for="publicFlag">公开</label>
        </div>
        <div class="form-check form-switch">
          <input id="commentEnable" v-model="commentChecked" class="form-check-input" type="checkbox" />
          <label class="form-check-label" for="commentEnable">允许评论</label>
        </div>
      </div>
      <p v-if="error" class="text-danger mb-0">{{ error }}</p>
      <div class="d-flex gap-2">
        <button class="btn btn-primary" type="submit" :disabled="saving">保存</button>
        <router-link class="btn btn-outline-secondary" to="/profile">返回个人中心</router-link>
      </div>
    </form>
  </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createArticle, getArticleDetail, updateArticle } from '@/api/article'
import { getCategories } from '@/api/category'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => Boolean(route.params.id))
const categories = ref([])
const saving = ref(false)
const error = ref('')
const publicChecked = ref(true)
const commentChecked = ref(true)
const form = reactive({
  title: '',
  content: '',
  categoryId: null,
  tags: ''
})

const load = async () => {
  const categoryRes = await getCategories()
  categories.value = categoryRes.data || []
  if (isEdit.value) {
    const res = await getArticleDetail(route.params.id)
    Object.assign(form, {
      title: res.data.title,
      content: res.data.content,
      categoryId: res.data.categoryId,
      tags: res.data.tags
    })
    publicChecked.value = res.data.publicFlag === 1
    commentChecked.value = res.data.commentEnable === 1
  }
}

const submit = async () => {
  saving.value = true
  error.value = ''
  const payload = {
    ...form,
    publicFlag: publicChecked.value ? 1 : 0,
    commentEnable: commentChecked.value ? 1 : 0
  }
  try {
    const res = isEdit.value
      ? await updateArticle(route.params.id, payload)
      : await createArticle(payload)
    router.push(`/article/${res.data.id}`)
  } catch (err) {
    error.value = err.response?.data?.msg || '保存失败'
  } finally {
    saving.value = false
  }
}

onMounted(load)
</script>
