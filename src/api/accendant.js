import request from '@/utils/request'

/**
 * 分页获取维修人员表中所有信息
 * @param {分页信息} query
 */
export function initAccendantData(query) {
  if (query === undefined) {
    return request({
      url: '/accendant/getAccAll',
      method: 'post',
      params: {
        page: null,
        limit: null
      }
    })
  } else {
    return request({
      url: '/accendant/getAccAll',
      method: 'post',
      params: {
        page: query.page,
        limit: query.limit
      }
    })
  }
}

/**
 * 添加维修人信息
 * @param {维修人员信息} acc
 */
export function addAccendant(acc) {
  return request({
    url: '/accendant/addAcc',
    method: 'post',
    params: {
      accendantName: acc.accendantName,
      phone: acc.phone
    }
  })
}

/**
 * 删除维修人员信息
 * @param {维修人id'} id
 */
export function deAccendant(id) {
  return request({
    url: '/accendant/deAcc/' + id,
    method: 'post'
  })
}

/**
 * 更新维修人员信息
 */
export function upAccendant(acc) {
  console.log('==========upAccendant===========')
  console.log(acc)
  return request({
    url: '/accendant/upAcc',
    method: 'post',
    params: {
      accendantId: acc.accendantId,
      accendantName: acc.accendantName,
      phone: acc.phone
    }
  })
}

