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
        path: '/zlAdmin',
        name: 'ZlAdmin',
        component: () => import('../views/ZlAdmin.vue')
      },
      {
        path: '/cmntAdmin',
        name: 'CmntAdmin',
        component: () => import('../views/CmntAdmin.vue')
      },
      {
        path: '/orderAdmin',
        name: 'OrderAdmin',
        component: () => import('../views/OrderAdmin.vue')
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
// 检查是否登录
router.beforeEach((to,from,next) =>{
  // 获取缓存
  let userJson = sessionStorage.getItem("user")
  if(to.path === '/login' || to.path === '/register') {
    next()
  }else {
    if(!userJson){
      router.push('/login')
    }else {
      next()
    }
  }
})


export default router
