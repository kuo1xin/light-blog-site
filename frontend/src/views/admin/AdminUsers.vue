<template>
  <div class="table-responsive">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>用户名</th>
          <th>昵称</th>
          <th>角色</th>
          <th>状态</th>
          <th>邮箱</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in users" :key="item.id">
          <td>{{ item.username }}</td>
          <td><input v-model="item.nickname" class="form-control form-control-sm" /></td>
          <td>
            <select v-model="item.role" class="form-select form-select-sm">
              <option value="user">会员</option>
              <option value="admin">管理员</option>
            </select>
          </td>
          <td>
            <select v-model.number="item.status" class="form-select form-select-sm">
              <option :value="1">正常</option>
              <option :value="0">禁用</option>
            </select>
          </td>
          <td><input v-model="item.email" class="form-control form-control-sm" /></td>
          <td>
            <div class="btn-group btn-group-sm">
              <button class="btn btn-outline-primary" type="button" @click="save(item)">保存</button>
              <button class="btn btn-outline-danger" type="button" @click="remove(item.id)">删除</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { deleteUser, getUsers, updateUser } from '@/api/admin'

const users = ref([])

const load = async () => {
  const res = await getUsers()
  users.value = res.data || []
}

const save = async (item) => {
  await updateUser(item.id, item)
}

const remove = async (id) => {
  await deleteUser(id)
  users.value = users.value.filter((item) => item.id !== id)
}

onMounted(load)
</script>
