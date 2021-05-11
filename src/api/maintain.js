import request from '@/utils/request'

/**
 * 获取工单表中所有信息
 * @param {*} query
 */
export function getMaintainAll(query) {
  return request({
    url: '/maintain/getMain',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 获取未指派员工工单
 */
export function getNotAccendant() {
  return request({
    url: '/maintain/getNoAcc',
    method: 'post'
  })
}

/**
 * 查询已完成的工单
 * @param {*} query
 */
export function getFinish(query) {
  return request({
    url: '/maintain/getFinish',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 派遣维修人员
 * @param {*} query
 */
export function upAccendant(query) {
  return request({
    url: '/maintain/getFinish',
    method: 'post',
    params: {
      accendantId: query.accendantId,
      maintainNo: query.maintainNo
    }
  })
}

/**
 * 新增工单
 */
export function addMaintain(query) {
  return request({
    url: '/maintain/addMain',
    method: 'post',
    params: {
      carId: query.carId,
      accendantId: query.accendantId,
      maintainInfo: query.maintainInfo
    }
  })
}

/**
 * 获取工单表中需处理信息
 * @param {*} query
 */
export function getMainAll(query) {
  return request({
    url: '/maintain/getMainAll',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 更新工单信息
 * @param {*} query
 */
export function upMaintain(query) {
  return request({
    url: '/maintain/upMainData',
    method: 'post',
    params: {
      finishDate: query.finishDate,
      accendantId: query.accendantId,
      usePartsId: query.usePartsId,
      maintainInfo: query.maintainInfo,
      maintainCost: query.maintainCost,
      state: query.state,
      Id: query.id
    }
  })
}
