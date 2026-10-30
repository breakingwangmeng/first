import http from './http'

export interface LoginResponse {
  tokenName: string
  tokenValue: string
  nickname: string | null
}

export interface UserItem {
  id: number
  username: string
  nickname: string | null
  status: number
  createdAt: string
}

export interface PageData<T> {
  list: T[]
  total: number
  page: number
  size: number
}

export async function login(username: string, password: string): Promise<LoginResponse> {
  const response = await http.post('/admin/auth/login', { username, password })
  return response.data.data
}

export async function fetchUsers(page = 1, size = 10): Promise<PageData<UserItem>> {
  const response = await http.get('/admin/users', { params: { page, size } })
  return response.data.data
}
