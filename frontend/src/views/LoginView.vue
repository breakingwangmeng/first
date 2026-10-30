<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/admin'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: 'admin', password: '123456' })

async function handleLogin() {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }
  loading.value = true
  try {
    const data = await login(form.username, form.password)
    localStorage.setItem(data.tokenName, data.tokenValue)
    ElMessage.success('登录成功')
    await router.push({ name: 'users' })
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2 class="title">Basic Framework</h2>
      <el-form @submit.prevent="handleLogin">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button class="submit" type="primary" :loading="loading" native-type="submit">
          登录
        </el-button>
      </el-form>
      <p class="hint">种子账号：admin / 123456（管理员），demo / 123456（普通用户）</p>
    </el-card>
  </div>
</template>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f2f5;
}

.login-card {
  width: 360px;
}

.title {
  text-align: center;
  margin: 8px 0 24px;
}

.submit {
  width: 100%;
}

.hint {
  color: #909399;
  font-size: 12px;
  margin-top: 12px;
}
</style>
