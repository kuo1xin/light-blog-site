<template>
  <div class="workspace-grid">
    <section>
      <div class="section mb-3">
        <div class="d-flex flex-wrap justify-content-between gap-3 align-items-end">
          <div>
            <h1 class="section-title mb-1">公开文章</h1>
            <p class="muted mb-0">浏览会员发布的公开内容。</p>
          </div>
          <form class="d-flex gap-2" @submit.prevent="search">
            <input v-model="keyword" class="form-control" type="search" placeholder="标题、作者、标签" />
            <button class="btn btn-primary" type="submit">搜索</button>
          </form>
        </div>
      </div>

      <div v-if="loading" class="section">加载中...</div>
      <div v-else-if="articles.length" class="vstack gap-3">
        <ArticleCard v-for="item in articles" :key="item.id" :data="item" />
        <Pagination :page="page" :size="size" :total="total" @change="changePage" />
      </div>
      <div v-else class="section muted">暂无公开文章。</div>
    </section>

    <aside class="section">
      <h2 class="section-title">小站动态</h2>
      <div class="vstack gap-3">
        <div>
          <div class="h3 mb-0">{{ total }}</div>
          <div class="muted">公开文章</div>
        </div>
        <router-link v-if="store.isLoggedIn" class="btn btn-primary" to="/write">发布新文章</router-link>
        <router-link v-else class="btn btn-outline-primary" to="/register">注册成为会员</router-link>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getArticleList } from '@/api/article'
import ArticleCard from '@/components/ArticleCard.vue'
import Pagination from '@/components/Pagination.vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const articles = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const keyword = ref('')
const loading = ref(false)

const load = async () => {
  loading.value = true
  try {
    const res = await getArticleList({ page: page.value, size: size.value, keyword: keyword.value })
    articles.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const search = () => {
  page.value = 1
  load()
}

const changePage = (nextPage) => {
  page.value = nextPage
  load()
}

onMounted(load)
</script>
