import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/Layout/Layout";
import Main from "@/Layout/Main";

const routes = [
  {
    path: '/admin',
    name: 'Layout',
    component: Layout,
    redirect:"/user",
    children:[
      {
        path:'/user',
        name:'User',
        component:()=>import("@/views/User"),
      },
      {
        path:'/news',
        name:'News',
        component:()=>import("@/views/News"),
      },
    ]
  },
  {
    path: '/',
    name: 'Main',
    component: Main,
    redirect: "/goods",
    children:[
      {
        path:'/goods',
        name:'Goods',
        component:()=>import("@/views/Goods")
      },
      {
        path:'/details',
        name:'Detail',
        component:()=>import("@/views/Detail")
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=>import("@/views/Register")
  },
  {
    path: '/backstage',
    name: 'Backstage',
    component: ()=>import("@/views/Backstage")
  },
  {
    path:'/person',
    name:'Person',
    component:()=>import("@/views/Person")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
