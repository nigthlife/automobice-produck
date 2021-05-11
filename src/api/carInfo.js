import request from '@/utils/request'

/**
 * 分页获取汽车表中所有信息
 * @param {页码} query
 */
export function getCarInfoAll(query) {
  if (query === undefined) {
    return request({
      url: '/carInfo/getAll',
      method: 'post',
      params: {
        page: null,
        limit: null
      }
    })
  } else {
    return request({
      url: '/carInfo/getAll',
      method: 'post',
      params: {
        page: query.page,
        limit: query.limit
      }
    })
  }
}

/**
 * 新增一条汽车
 * @param {*} obj
 */
export function setCarInfo(obj) {
  return request({
    url: '/carInfo/setData',
    method: 'post',
    params: {
      carId: obj.carId,
      clientId: obj.clientId,
      carBrand: obj.carBrand,
      carNumber: obj.carNumber
    }
  })
}

/**
 * 删除一条汽车信息
 * @param {carId} id
 */
export function deleteCar(id) {
  return request({
    url: '/carInfo/delCar/' + id,
    method: 'post'
  })
}

/**
 * 更新汽车信息
 * @param {*} obj
 */
export function updateCar(obj) {
  console.log('-------------------')
  console.log(obj)
  return request({
    url: '/carInfo/upCar',
    method: 'post',
    params: {
      carId: obj.carId,
      clientId: obj.clientId,
      carBrand: obj.carBrand,
      carNumber: obj.carNumber
    }
  })
}

/**
 * 获取当前用户下的所有汽车
 * @param {clientId} query
 */
export function getClientCar(query) {
  return request({
    url: '/carInfo/getClientCar/' + query,
    method: 'post'
  })
}
