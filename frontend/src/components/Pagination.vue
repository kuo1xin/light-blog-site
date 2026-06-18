<template>
  <div class="d-flex justify-content-between align-items-center mt-3">
    <span class="muted small">第 {{ page }} 页，共 {{ totalPages }} 页</span>
    <div class="btn-group">
      <button class="btn btn-outline-secondary btn-sm" :disabled="page <= 1" @click="$emit('change', page - 1)">上一页</button>
      <button class="btn btn-outline-secondary btn-sm" :disabled="page >= totalPages" @click="$emit('change', page + 1)">下一页</button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  page: { type: Number, required: true },
  size: { type: Number, required: true },
  total: { type: Number, required: true }
})

defineEmits(['change'])

const totalPages = computed(() => Math.max(1, Math.ceil(props.total / props.size)))
</script>
