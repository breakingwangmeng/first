<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchUsers, type UserItem } from '../api/admin'

const users = ref<UserItem[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const loading = ref(false)

async function load() {
  loading.value = true
  try {
    const data = await fetchUsers(page.value, size.value)
    users.value = data.list
    total.value = data.total
  } catch {
    // 错误提示已由 http 响应拦截器统一弹出，这里吞掉已处理的拒绝，避免冒泡到 Vue 告警
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <el-card>
    <template #header>用户列表（示例页：演示带 token 调用后端分页接口）</template>
    <el-table v-loading="loading" :data="users">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" />
    </el-table>
    <el-pagination
      v-model:current-page="page"
      class="pagination"
      :page-size="size"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="load"
    />
  </el-card>
</template>

<style scoped>
.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
