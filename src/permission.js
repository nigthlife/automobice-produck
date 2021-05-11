import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar 进度条（内置函数）
import 'nprogress/nprogress.css' // progress bar style 进度条样式
import { getToken } from '@/utils/auth' // get token from cookie 获取cookie中获取token
import getPageTitle from '@/utils/get-page-title' // 获取页面的标题

NProgress.configure({ showSpinner: false }) // NProgress Configuration 页面顶部加载进度条配置

const whiteList = ['/login'] // no redirect whitelist 没有重定向白名单

// 前置守卫
router.beforeEach(async(to, from, next) => {
  // 设置开始进度条
  NProgress.start()

  // 将即将要进入的目标的路由对象中的meat中的标题设置为当前网页标题
  document.title = getPageTitle(to.meta.title)

  const hasToken = getToken()

  // 确认用户是否登录
  if (hasToken) {
    // 判断当前url路径是否是登录页面
    if (to.path === '/login') {
      // 如果已登录，重定向到主页
      next({ path: '/' })
      // 完成进度条
      NProgress.done()
    } else {
      // 获取vuex中的角色状态信息
      const role = store.getters.role
      // 判断角色信息是否存在
      if (role.length !== 0) {
        // 当有用户权限的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
        next()
      } else {
        try {
          // 判断用户名是否为空
          var name = store.getters.name
          if (name === '' || name === undefined || name === null) {
            await store.dispatch('user/resetToken')
            next(`/login?redirect=${to.path}`)
            NProgress.done()
          }
          // 判断角色是否为空
          if (role) {
            // await store.dispatch('user/resetToken')
            // 根据用户Id获取该用户的角色信息
            store.dispatch('user/getRoleName', store.getters.user).then(resp => {
              // 1.获取结果集中的角色
              // 2.调用获取动态路由的action中
              console.log(resp)

              store.dispatch('user/createRouters', resp).then((res) => {
                // 更新路由
                router.options.routes = res

                // 添加
                router.addRoutes(res)
                next({ ...to, replace: true })
              })
            })
          } else {
            next()
          }
        } catch (error) {
          // 删除令牌，转到登录页面重新登录
          await store.dispatch('user/resetToken')
          Message.error('服务器拥挤，请稍后再试····')
          // Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          // 完成进度条
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      // 否则全部重定向到登录页
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

// 后置守卫（hook）
// 在离开当前路由前调用
router.afterEach(() => {
  // finish progress bar 完成进度条
  NProgress.done()
})
