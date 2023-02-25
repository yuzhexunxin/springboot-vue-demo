import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout.vue";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/exhAdmin',
    children: [
      {
        path: '/userAdmin',
        name: 'UserAdmin',
        component: () => import("@/views/UserAdmin.vue")
      },
      {
        path: '/bscInfoAdmin',
        name: 'BscInfoAdmin',
        component: () => import('../views/BscInfoAdmin.vue')
      },
      {
        path: '/exhAdmin',
        name: 'ExhAdmin',
        component: () => import('../views/ExhAdmin.vue')
      },
      {
        path: '/cmntAdmin',
        name: 'CmntAdmin',
        component: () => import('../views/CmntAdmin.vue')
      },
      {
        path: '/self',
        name: 'Self',
        component: () => import('../views/Self.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/test',
    name: 'Test',
    component: () => import('../views/Test.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
