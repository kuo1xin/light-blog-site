<template>
  <section class="section">
    <h2 class="section-title">评论区</h2>
    <form v-if="store.isLoggedIn && commentEnable === 1" class="mb-3" @submit.prevent="submit">
      <textarea v-model="content" class="form-control mb-2" rows="3" maxlength="500" placeholder="写下评论"></textarea>
      <button class="btn btn-primary btn-sm" type="submit" :disabled="submitting">发表评论</button>
    </form>
    <p v-else-if="commentEnable === 0" class="muted">作者已关闭评论。</p>
    <p v-else class="muted">登录后可以评论。</p>

    <div v-if="comments.length" class="vstack gap-3">
      <div v-for="item in comments" :key="item.id" class="border-top pt-3">
        <div class="d-flex justify-content-between gap-2">
          <strong>{{ item.nickname }}</strong>
          <span class="muted small">{{ formatDate(item.createTime) }}</span>
        </div>
        <p class="mb-2">{{ item.content }}</p>
        <button v-if="canDelete(item)" class="btn btn-sm btn-outline-danger" type="button" @click="remove(item.id)">删除</button>
      </div>
    </div>
    <p v-else class="muted mb-0">暂无评论。</p>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { addComment, deleteComment, getComments } from '@/api/comment'
import { useUserStore } from '@/stores/user'

const props = defineProps({
  articleId: { type: Number, required: true },
  articleUserId: { type: Number, required: true },
  commentEnable: { type: Number, required: true }
})

const store = useUserStore()
const comments = ref([])
const content = ref('')
const submitting = ref(false)

const load = async () => {
  const res = await getComments(props.articleId)
  comments.value = res.data || []
}

const submit = async () => {
  if (!content.value.trim()) return
  submitting.value = true
  try {
    await addComment({ articleId: props.articleId, content: content.value.trim() })
    content.value = ''
    await load()
  } finally {
    submitting.value = false
  }
}

const canDelete = (item) => {
  const userId = store.user?.id
  return store.isAdmin || userId === item.userId || userId === props.articleUserId
}

const remove = async (id) => {
  await deleteComment(id)
  await load()
}

const formatDate = (value) => value ? String(value).replace('T', ' ').slice(0, 16) : ''

onMounted(load)
</script>
