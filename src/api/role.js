import request from '@/utils/request'

/**
 * 获取所有角色
 * @param {*} query
 */
export function getClientInfo(query) {
  if (query === undefined) {
    return request({
      url: '/role/getRoleAll',
      method: 'post'
    })
  } else {
    return request({
      url: '/role/getRoleAll',
      method: 'post',
      params: {
        page: query.page,
        limit: query.limit
      }
    })
  }
}
