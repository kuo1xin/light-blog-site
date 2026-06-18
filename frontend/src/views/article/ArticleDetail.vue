<template>
  <div v-if="article" class="workspace-grid">
    <section class="vstack gap-3">
      <article class="section">
        <div class="d-flex flex-wrap align-items-center gap-2 mb-2">
          <span v-if="article.publicFlag === 0" class="status-pill off">不公开</span>
          <span v-if="article.commentEnable === 0" class="status-pill off">评论关闭</span>
          <span v-if="article.categoryName" class="tag">{{ article.categoryName }}</span>
          <span class="muted small">{{ formatDate(article.createTime) }}</span>
        </div>
        <h1 class="mb-3">{{ article.title }}</h1>
        <div class="content-body">{{ article.content }}</div>
      </article>
      <CommentList
        :article-id="Number(article.id)"
        :article-user-id="Number(article.userId)"
        :comment-enable="Number(article.commentEnable)"
      />
    </section>

    <aside class="section">
      <h2 class="section-title">作者</h2>
      <p class="mb-1">
        <router-link :to="`/space/${article.userId}`">{{ article.authorName }}</router-link>
      </p>
      <p class="muted small">更新于 {{ formatDate(article.updateTime) }}</p>
      <div class="vstack gap-2">
        <button v-if="store.isLoggedIn" class="btn btn-outline-primary" type="button" @click="followArticle">关注文章</button>
        <button v-if="store.isLoggedIn && store.user?.id !== article.userId" class="btn btn-outline-primary" type="button" @click="followAuthor">关注作者</button>
        <router-link v-if="canEdit" class="btn btn-primary" :to="`/article/${article.id}/edit`">编辑文章</router-link>
      </div>
    </aside>
  </div>
  <section v-else class="section">加载中...</section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleDetail } from '@/api/article'
import { toggleFollow } from '@/api/follow'
import CommentList from '@/components/CommentList.vue'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const store = useUserStore()
const article = ref(null)

const canEdit = computed(() => store.isAdmin || store.user?.id === article.value?.userId)

const load = async () => {
  const res = await getArticleDetail(route.params.id)
  article.value = res.data
}

const followArticle = async () => {
  await toggleFollow({ type: 'article', targetId: article.value.id })
}

const followAuthor = async () => {
  await toggleFollow({ type: 'user', targetId: article.value.userId })
}

const formatDate = (value) => value ? String(value).replace('T', ' ').slice(0, 16) : ''

onMounted(load)
</script>
