<template>
  <div class="workspace-grid">
    <section class="vstack gap-3">
      <div class="section">
        <h1 class="section-title">个人资料</h1>
        <form class="row g-3" @submit.prevent="saveProfile">
          <div class="col-md-6">
            <label class="form-label">昵称</label>
            <input v-model.trim="profile.nickname" class="form-control" />
          </div>
          <div class="col-md-6">
            <label class="form-label">邮箱</label>
            <input v-model.trim="profile.email" class="form-control" type="email" />
          </div>
          <div class="col-12">
            <label class="form-label">头像地址</label>
            <input v-model.trim="profile.avatar" class="form-control" />
          </div>
          <div class="col-12">
            <label class="form-label">简介</label>
            <textarea v-model.trim="profile.intro" class="form-control" rows="3"></textarea>
          </div>
          <div class="col-12">
            <button class="btn btn-primary" type="submit">保存资料</button>
          </div>
        </form>
      </div>

      <div class="section">
        <div class="d-flex justify-content-between gap-2 align-items-center mb-3">
          <h2 class="section-title mb-0">我的文章</h2>
          <router-link class="btn btn-primary btn-sm" to="/write">写文章</router-link>
        </div>
        <div v-if="articles.length" class="vstack gap-3">
          <div v-for="item in articles" :key="item.id" class="border-top pt-3">
            <div class="d-flex justify-content-between gap-2">
              <router-link :to="`/article/${item.id}`">{{ item.title }}</router-link>
              <div class="btn-group btn-group-sm">
                <router-link class="btn btn-outline-secondary" :to="`/article/${item.id}/edit`">编辑</router-link>
                <button class="btn btn-outline-danger" type="button" @click="removeArticle(item.id)">删除</button>
              </div>
            </div>
            <div class="d-flex gap-2 mt-2">
              <span :class="['status-pill', item.publicFlag === 1 ? '' : 'off']">{{ item.publicFlag === 1 ? '公开' : '不公开' }}</span>
              <span :class="['status-pill', item.commentEnable === 1 ? '' : 'off']">{{ item.commentEnable === 1 ? '可评论' : '禁评' }}</span>
            </div>
          </div>
        </div>
        <p v-else class="muted mb-0">还没有文章。</p>
      </div>
    </section>

    <aside class="vstack gap-3">
      <div class="section">
        <h2 class="section-title">统计</h2>
        <div class="row g-2 text-center">
          <div class="col-4">
            <div class="h4 mb-0">{{ stats.articleCount || 0 }}</div>
            <div class="muted small">文章</div>
          </div>
          <div class="col-4">
            <div class="h4 mb-0">{{ stats.commentCount || 0 }}</div>
            <div class="muted small">评论</div>
          </div>
          <div class="col-4">
            <div class="h4 mb-0">{{ stats.followCount || 0 }}</div>
            <div class="muted small">关注</div>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">博客空间</h2>
        <form class="vstack gap-2" @submit.prevent="saveSpace">
          <input v-model.trim="space.name" class="form-control" placeholder="空间名称" />
          <textarea v-model.trim="space.intro" class="form-control" rows="3" placeholder="空间简介"></textarea>
          <button class="btn btn-outline-primary" type="submit">保存空间</button>
          <router-link class="btn btn-primary" :to="`/space/${store.user?.id}`">进入空间</router-link>
        </form>
      </div>

      <div class="section">
        <h2 class="section-title">分类</h2>
        <form class="d-flex gap-2 mb-3" @submit.prevent="addCategory">
          <input v-model.trim="categoryName" class="form-control" placeholder="分类名称" />
          <button class="btn btn-primary" type="submit">新增</button>
        </form>
        <div class="vstack gap-2">
          <div v-for="item in categories" :key="item.id" class="d-flex justify-content-between align-items-center">
            <span>{{ item.name }}</span>
            <button class="btn btn-sm btn-outline-danger" type="button" @click="removeCategory(item.id)">删除</button>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">我的关注</h2>
        <div v-if="follows.length" class="vstack gap-2">
          <span v-for="item in follows" :key="item.id" class="tag">{{ item.type === 'user' ? '作者' : '文章' }}：{{ item.targetName }}</span>
        </div>
        <p v-else class="muted mb-0">暂无关注。</p>
      </div>

      <div class="section">
        <h2 class="section-title">修改密码</h2>
        <form class="vstack gap-2" @submit.prevent="savePassword">
          <input v-model="password.oldPassword" class="form-control" type="password" placeholder="原密码" />
          <input v-model="password.newPassword" class="form-control" type="password" placeholder="新密码" />
          <button class="btn btn-outline-primary" type="submit">更新密码</button>
        </form>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { deleteArticle, getMyArticles } from '@/api/article'
import { createCategory, deleteCategory, getCategories } from '@/api/category'
import { getFollowList } from '@/api/follow'
import { getSpaceInfo, saveSpaceInfo } from '@/api/space'
import { changePasswordApi, getStatsApi, updateProfileApi } from '@/api/user'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const profile = reactive({ nickname: '', email: '', avatar: '', intro: '' })
const password = reactive({ oldPassword: '', newPassword: '' })
const space = reactive({ name: '', intro: '' })
const stats = reactive({})
const articles = ref([])
const categories = ref([])
const follows = ref([])
const categoryName = ref('')

const load = async () => {
  await store.fetchUserInfo()
  Object.assign(profile, store.user || {})
  const [statsRes, articlesRes, categoriesRes, followsRes, spaceRes] = await Promise.all([
    getStatsApi(),
    getMyArticles(),
    getCategories(),
    getFollowList(),
    getSpaceInfo(store.user.id)
  ])
  Object.assign(stats, statsRes.data || {})
  articles.value = articlesRes.data || []
  categories.value = categoriesRes.data || []
  follows.value = followsRes.data || []
  Object.assign(space, spaceRes.data || {})
}

const saveProfile = async () => {
  const res = await updateProfileApi(profile)
  store.user = res.data
  localStorage.setItem('user', JSON.stringify(store.user))
}

const savePassword = async () => {
  await changePasswordApi(password)
  password.oldPassword = ''
  password.newPassword = ''
}

const saveSpace = async () => {
  const res = await saveSpaceInfo(space)
  Object.assign(space, res.data)
}

const addCategory = async () => {
  if (!categoryName.value) return
  await createCategory({ name: categoryName.value })
  categoryName.value = ''
  const res = await getCategories()
  categories.value = res.data || []
}

const removeCategory = async (id) => {
  await deleteCategory(id)
  categories.value = categories.value.filter((item) => item.id !== id)
}

const removeArticle = async (id) => {
  await deleteArticle(id)
  articles.value = articles.value.filter((item) => item.id !== id)
}

onMounted(load)
</script>
