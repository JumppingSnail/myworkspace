import Vue from 'vue'
import App from './App.vue'
import Vant from 'vant'
import router from './router'
import 'vant/lib/index.css'
import cookie from 'vue-cookie'
import { post } from './axios/api.js'

Vue.use(Vant)

Vue.prototype.$post = post
Vue.prototype.token = ''
Vue.prototype.$cookie = cookie
// Vue.prototype.url = 'https://demo.metatcm.com/xinan/'
Vue.prototype.url = process.env.NODE_ENV === 'development' ? 'http://localhost:9900/xinan' : 'https://demo.metatcm.com/xinan'
Vue.config.productionTip = false
Vue.prototype.is_weixin = () => /micromessenger/i.test(navigator.userAgent)


router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  const login = to.meta.login
  // 判断该路由是否需要登录权限
  if (login) {
    let token = cookie.get('token')
    if (!token) {
      // router.push({path:'/login'});
      next('/login')
    } else {
      Vue.prototype.token = token
      next()
    }
  } else {
    next()
  }
})
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
