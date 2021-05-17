import request from '@/utils/request'
/**
 * 用户登录
 * @param {账号密码} data
 */
export function login(data) {
  return request({
    url: 'loginCheck',
    method: 'post',
    params: {
      loginName: data.username,
      userPwd: data.password
    }
  })
}
/**
 *
 * @param {*} data
 */
export function getRole(data) {
  return request({
    url: '/user/getRole/' + data,
    method: 'post'
  })
}
/**
 * 获取用户信息
 * @param {令牌} token
 */
export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}
/**
 * 获取所有用户信息
 */
export function getUserInfoAll() {
  return request({
    url: '/user/getInfoAll',
    method: 'get'
  })
}

/**
 * 登出
 */
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post',
    withCredentials: true
  })
}

/**
 * 登录验证
 * @param {userInfo} data
 */
export function automobileLogin(data) {
  return request({
    url: '/loginCheck',
    method: 'post',
    params: {
      loginName: data.username,
      userPwd: data.password
    }
  })
}

export function getRouter(data) {
  return request({
    url: '/user/getRouter/' + data,
    method: 'post'
  })
}

/**
 * 获取所有用户id
 */
export function getUser(query) {
  return request({
    url: '/user/getInfoAll',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 更新用户信息
 * @param {userInfo} data
 */
export function updateUserInfo(data) {
  return request({
    url: '/user/upUserInfo',
    method: 'post',
    params: {
      userId: data.userId,
      userName: data.userName,
      loginName: data.loginName,
      userPwd: data.userPwd
    }
  })
}

/**
 * 添加一条用户信息
 * @param {UserInfo} data
 */
export function addUserInfo(data) {
  // delete data.importance
  // delete data.loginState
  return request({
    url: '/user/inUser',
    method: 'post',
    params: {
      userId: data.userId,
      userName: data.userName,
      loginName: data.loginName,
      userPwd: data.userPwd
    }
  })
}

/**
 * 删除用户表的数据
 * @param {UserID} id
 */
export function deUserInfo(id) {
  return request({
    url: '/user/deUser/' + id,
    method: 'post'
  })
}

