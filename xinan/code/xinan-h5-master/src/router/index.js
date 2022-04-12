import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/index.vue'
import notice from '../views/notice/notice.vue'
import login from '../views/login/login.vue'
import user from '../views/user/user.vue'
import password from '../views/user/password.vue'
import collection from '../views/user/collection.vue'
import search from '../views/search/search.vue'
import feedback from '../views/user/feedback.vue'
import noticeDetail from '../views/notice/noticeDetail.vue'
import book from '../views/detail/book.vue'
import other from '../views/detail/other.vue'
import feedbacks from '../views/detail/feedbacks.vue'
import index from '../views/public/index.vue'
import record from '../views/record/record.vue'
import catalist from '../views/detail/catalist.vue'
import scientific from '../views/scientific/scientific.vue'
import diseaseSearch from '../views/scientific/diseaseSearch.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: {
      title: '首页',
      login: true,
      type: ''
    }
  },
  {
    path: '/notice',
    name: 'notice',
    component: notice,
    meta: {
      title: '培训通知',
      login: true,
      type: ''
    }
  },
  {
    path: '/login',
    name: 'login',
    component: login,
    meta: {
      title: '登录',
      login: false,
      type: ''
    }
  },
  {
    path: '/user',
    name: 'user',
    component: user,
    meta: {
      title: '个人中心',
      login: true,
      type: ''
    }
  },
  {
    path: '/password',
    name: 'password',
    component: password,
    meta: {
      title: '修改密码',
      login: true,
      type: ''
    }
  },
  {
    path: '/collection',
    name: 'collection',
    component: collection,
    meta: {
      title: '我的收藏',
      login: true,
      type: ''
    }
  },
  {
    path: '/search',
    name: 'search',
    component: search,
    meta: {
      title: '搜索',
      login: true,
      type: ''
    }
  },
  {
    path: '/feedback',
    name: 'feedback',
    component: feedback,
    meta: {
      title: '我的反馈',
      login: true,
      type: ''
    }
  },
  {
    path: '/noticeDetail',
    name: 'noticeDetail',
    component: noticeDetail,
    meta: {
      title: '培训详情',
      login: true,
      type: ''
    }
  },
  {
    path: '/other',
    name: 'other',
    component: other,
    meta: {
      title: '',
      login: true,
      type: ''
    }
  },
  {
    path: '/book',
    name: 'book',
    component: book,
    meta: {
      title: '',
      login: true,
      type: ''
    }
  },
  {
    path: '/feedbacks',
    name: 'feedbacks',
    component: feedbacks,
    meta: {
      title: '反馈意见',
      login: true,
      type: ''
    }
  },
  {
    path: '/index',
    name: 'index',
    component: index,
    meta: {
      title: '',
      login: false,
      type: ''
    }
  },
  {
    path: '/record',
    name: 'record',
    component: record,
    meta: {
      title: '',
      login: false,
      type: ''
    }
  },
  {
    path: '/catalist',
    name: 'catalist',
    component: catalist,
    meta: {
      title: '栏目列表',
      login: false,
      type: ''
    }
  },
  {
    path: '/scientific',
    name: 'scientific',
    component: scientific,
    meta: {
      title: '消渴病肾病',
      login: true,
      type: ''
    }
  },
  {
    path: '/diseaseSearch',
    name: 'diseaseSearch',
    component: diseaseSearch,
    meta: {
      title: '消渴肾病列表',
      login: true,
      type: ''
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: '/xaphone/',
  routes
})

export default router
