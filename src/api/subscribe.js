import request from '@/utils/request'

/**
 * 获取所有预约记录
 */
export function getSubscribeAll(query) {
  return request({
    url: '/Subscribe/getAll',
    method: 'get',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 更新预约记录
 * @param {Subscribe} data
 */
export function upSubscribe(data) {
  console.log(data)
  console.log('-------upSubscribe-----------')
  return request({
    url: '/Subscribe/upSub',
    method: 'post',
    params: {
      Id: data.id,
      cilentId: data.cilentId,
      subscribeInfo: data.subscribeInfo
    }
  })
}

/**
 * 向预约表中插入一条信息
 * @param {Subscribe} data
 */
export function InSubscribe(data) {
  console.log(data)
  console.log('-------InSubscribe-----------')
  return request({
    url: '/Subscribe/inSub',
    method: 'post',
    params: {
      carId: data.carId,
      subscribeInfo: data.subscribeInfo
    }
  })
}

/**
 * 删除一条预约记录信息
 * @param {subscribeId} data
 */
export function DelSubscribe(data) {
  console.log(data)
  console.log('-------DelSubscribe-----------')
  return request({
    url: '/Subscribe/deSub/' + data,
    method: 'post'
  })
}

