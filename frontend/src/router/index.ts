import { createRouter, createWebHistory } from 'vue-router'

const TOKEN_KEY = 'satoken'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/',
      component: () => import('../layout/BasicLayout.vue'),
      redirect: '/users',
      children: [
        {
          path: 'users',
          name: 'users',
          component: () => import('../views/UsersView.vue'),
        },
      ],
    },
  ],
})

// 路由守卫：无 token 一律回登录页；已登录访问登录页则跳首页
router.beforeEach((to) => {
  const token = localStorage.getItem(TOKEN_KEY)
  if (!token && to.name !== 'login') {
    return { name: 'login' }
  }
  if (token && to.name === 'login') {
    return { name: 'users' }
  }
})

export default router
