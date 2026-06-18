<template>
  <div class="workspace-grid">
    <section>
      <div class="section mb-3">
        <h1 class="mb-2">{{ space.name }}</h1>
        <p class="muted mb-0">{{ space.intro || '这个空间还没有简介。' }}</p>
      </div>
      <div v-if="articles.length" class="vstack gap-3">
        <ArticleCard v-for="item in articles" :key="item.id" :data="item" />
      </div>
      <div v-else class="section muted">暂无文章。</div>
    </section>
    <aside class="section">
      <h2 class="section-title">空间信息</h2>
      <p class="muted">共 {{ articles.length }} 篇文章</p>
      <router-link v-if="isOwner" class="btn btn-primary" to="/profile">编辑空间</router-link>
    </aside>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { getSpaceArticles, getSpaceInfo } from '@/api/space'
import ArticleCard from '@/components/ArticleCard.vue'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const store = useUserStore()
const space = reactive({ name: '', intro: '' })
const articles = ref([])
const isOwner = computed(() => Number(route.params.userId) === store.user?.id)

const load = async () => {
  const [spaceRes, articlesRes] = await Promise.all([
    getSpaceInfo(route.params.userId),
    getSpaceArticles(route.params.userId)
  ])
  Object.assign(space, spaceRes.data || {})
  articles.value = articlesRes.data || []
}

watch(() => route.params.userId, load)
onMounted(load)
</script>
