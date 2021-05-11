import { login, automobileLogin, getRole, getUserInfoAll, getUser } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter, constantRouterMap, asyncRouterMap } from '@/router'

/**
 * 获取默认状态信息
 */
const getDefaultState = () => {
  return {
    // 获取令牌
    token: getToken(),
    // userId
    name: '',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    role: [],
    user: '',
    addRouters: '',
    allRouters: constantRouterMap
  }
}

const state = getDefaultState()

// 通过commit调用方法法
const mutations = {
  // 重设状态属性
  RESET_STATE: (state) => {
    // 如果目标对象中的属性具有相同的键，则属性将被源对象中的属性覆盖 替换token
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    // 替换token
    state.token = token
  },
  SET_NAME: (state, name) => {
    // 设置name
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    // 设置头像
    state.avatar = avatar
  },
  SET_USER: (state, user) => {
    state.user = user
  },
  SET_ROLE: (state, role) => {
    state.role = role
  },
  SET_ROUTERS: (state, route) => {
    // console.log('store')
    // console.log(route)
    state.addRouters = route
    state.allRouters = constantRouterMap.concat(route)
  }
}

const actions = {
  // user login 用户登录
  // commit：用户调用mutations中的方法
  // userInfo: 登录form表单信息
  login({ commit }, userInfo) {
    // 解构对象
    const { username, password } = userInfo
    // 异步请求
    return new Promise((resolve, reject) => {
      // username.trim() 删除用户输入的多余空格
      login({ username: username.trim(), password: password }).then(response => {
        // 获取响应的数据
        // const { data } = response
        // 替换全局变量中的token
        console.log(response)
        commit('SET_TOKEN', response.token)
        commit('SET_NAME', response.userName)
        commit('SET_USER', response.userId)
        // 设置cookie
        setToken(response.token)
        // 回调函数
        resolve()
      }).catch(() => {
        reject('服务器拥挤，请稍后再试····')
      })
    })
  },

  automobileLogin({ commit }, userInfo) {
    console.log('开始执行登录')
    // 解构对象
    const { username, password } = userInfo

    return new Promise((resolve, reject) => {
      automobileLogin({ username: username.trim(), password: password }).then(response => {
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  getUserInfoAll() {
    return new Promise((resolve, reject) => {
      getUserInfoAll().then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  /**
   * 创建路由
   * @param {*} param0
   * @param {角色对象} data
   */
  createRouters({ commit }, data) {
    return new Promise((resolve) => {
      const roles = data.map(vlaue => {
        if (vlaue.roleName) {
          return vlaue.roleName
        }
      })

      // 设置角色信息
      commit('SET_ROLE', roles)

      var constRouters = []

      // 将异步符合条件对象筛选出来
      const addRouters = asyncRouterMap.filter(item => {
        // 设置条件
        let roleFlag = false

        // 循环迭代路由 并获取符号条件的子路由对象
        const constRouter = item.children.filter(ites => {
          // 计算路由长度
          if (ites.meta.role === undefined) {
            return false
          }
          const roleLength = ites.meta.role.length

          // 判断当前子路由中角色长度
          if (roleLength === 0) {
            return false
          }
          let count = 0

          // 根据获取mate中role长度循环判断是否在查询出的角色数组中存在
          for (let index = 0; index < roleLength; index++) {
            if (roles.includes(ites.meta.role[index])) {
              // 如果存在count加一
              count++
            }
          }

          // 根据count是否大于0而进行判断是否需要返回当前对象
          if (count > 0) {
            roleFlag = true
            return true
          } else {
            return false
          }
        })

        constRouters.push(constRouter)

        return roleFlag
      })

      for (let index = 0; index < constRouters.length; index++) {
        addRouters[index].children = constRouters[index]
      }

      // 更新路由
      commit('SET_ROUTERS', addRouters)

      resolve(addRouters)
    })
  },
  /**
   * 获取用户角色信息
   * @param {} param0
   */
  getRoleName({ commit }, userId) {
    return new Promise((resolve, reject) => {
      console.log(userId)
      getRole(userId).then(response => {
        console.log(response)
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout 登出
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      console.log('token => ' + state.token)
      removeToken() // must remove  token  first
      resetRouter() // 重新设置路由

      commit('RESET_STATE')
      resolve()
    })
  },

  // remove token 删除token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  // 获取所有用户信息
  getUId() {
    return new Promise((resolve, reject) => {
      getUser().then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

