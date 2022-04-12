/* eslint-disable handle-callback-err */
import axios from 'axios'
import cookie from 'vue-cookie'
// axios 配置
axios.defaults.timeout = 50000
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// if (getUrlKey('wxToken', window.location.href)) {
//   cookie.set('token', getUrlKey('wxToken', window.location.href))
// }
axios.defaults.headers.post['access-token'] = cookie.get('token')
axios.defaults.withCredentials = true
// Axios实现请求重试
// axios.defaults.retry = 5 // 重试次数
// axios.defaults.retryDelay = 10000 // 重试延时
// axios.defaults.shouldRetry = (error) => true // 重试条件，默认只要是错误都需要重试


axios.host = '/api/'
let instance = axios.create({
  headers: {
    'content-type': 'application/json;charset=UTF-8'
  }
})
instance.interceptors.request.use(config => {
  config.headers['access-token'] = cookie.get('token')
  return config
});
/* 封装get方法 */
export function get (url, params = {}) {
  return new Promise((resolve, reject) => {
    instance.get(axios.host + url, {
      params: params
    }).then(response => {
      resolve(response.data)

    }).catch(err => {
      reject(err)
    })
  })
}
/* 封装post方法 */
export function post (url, data = {}) {
  return new Promise((resolve, reject) => {
    instance.post(axios.host + url, data).then(response => {
      resolve(response.data)
    }).catch(err => {
      reject(err)
    })
  })
}
export function getUrlKey (name, url) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(url) || [',', ""])[1].replace(/\+/g, '%20')) || null
}

export default instance
