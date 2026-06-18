<template>
  <article class="article-card">
    <div class="d-flex flex-wrap align-items-center gap-2 mb-2">
      <span v-if="data.publicFlag === 0" class="status-pill off">不公开</span>
      <span v-if="data.commentEnable === 0" class="status-pill off">评论关闭</span>
      <span v-if="data.categoryName" class="tag">{{ data.categoryName }}</span>
      <span class="muted small">{{ formatDate(data.createTime) }}</span>
    </div>
    <h2 class="h5 mb-2">
      <router-link :to="`/article/${data.id}`">{{ data.title }}</router-link>
    </h2>
    <p class="muted mb-3">{{ excerpt }}</p>
    <div class="d-flex flex-wrap justify-content-between gap-2 align-items-center">
      <router-link class="small" :to="`/space/${data.userId}`">{{ data.authorName || '匿名作者' }}</router-link>
      <div class="d-flex flex-wrap gap-1">
        <span v-for="tag in tags" :key="tag" class="tag">{{ tag }}</span>
      </div>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  data: { type: Object, required: true }
})

const excerpt = computed(() => {
  const text = props.data.content || ''
  return text.length > 110 ? `${text.slice(0, 110)}...` : text
})

const tags = computed(() => (props.data.tags || '').split(',').map((item) => item.trim()).filter(Boolean))

const formatDate = (value) => {
  if (!value) return ''
  return String(value).replace('T', ' ').slice(0, 16)
}
</script>
