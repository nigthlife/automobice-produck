import request from '@/utils/request'

/**
 * 获取所有客户信息
 * @param {页码和页大小} query
 */
export function getClientInfo(query) {
  return request({
    url: '/client/getCliAll',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 新增客户信息
 * @param {ClientInfo} obj
 */
export function addClientData(obj) {
  console.log(obj)
  console.log('--------addClientData----------')
  return request({
    url: '/client/addClient',
    method: 'get',
    params: {
      clientName: obj.clientName,
      sex: obj.sex,
      phone: obj.phone
    }
  })
}

/**
 * 更新客户信息
 * @param {ClientInfo} obj
 */
export function upClient(obj) {
  console.log(obj)
  console.log('--------upClient----------')
  return request({
    url: '/client/upClient',
    method: 'post',
    params: {
      clientId: obj.clientId,
      clientName: obj.clientName,
      sex: obj.sex,
      phone: obj.phone
    }
  })
}

/**
 * 删除客户
 * @param {clientId} id
 */
export function deClient(id) {
  console.log(id)
  console.log('--------deClient----------')
  return request({
    url: '/client/deClient/' + id,
    method: 'post'
  })
}

/**
 * 获取下拉列表客户信息
 */
export function getSelectInfo() {
  return request({
    url: '/client/getSelectInfo',
    method: 'post'
  })
}

