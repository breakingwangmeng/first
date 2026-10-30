import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

export interface Result<T> {
  code: number
  message: string
  data: T
}

const TOKEN_KEY = 'satoken'

const http = axios.create({ timeout: 10000 })

http.interceptors.request.use((config) => {
  const token = localStorage.getItem(TOKEN_KEY)
  if (token) {
    config.headers.satoken = token
  }
  return config
})

// 统一处理业务码：登录过期回登录页，其余错误弹提示
http.interceptors.response.use(
  (response) => {
    const result = response.data as Result<unknown>
    if (result.code !== 0) {
      ElMessage.error(result.message)
      return Promise.reject(new Error(result.message))
    }
    return response
  },
  (error) => {
    const result = error.response?.data as Result<unknown> | undefined
    if (result?.code === 40100) {
      localStorage.removeItem(TOKEN_KEY)
      void router.push({ name: 'login' })
    }
    ElMessage.error(result?.message ?? '网络异常，请稍后重试')
    return Promise.reject(error)
  },
)

export default http
