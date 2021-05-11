import request from '@/utils/request'

/**
 * 分页查询所有零件
 * @param {temp} query
 */
export function getPartsInfo(query) {
  return request({
    url: '/partsInfo/getParts',
    method: 'post',
    params: {
      page: query.page,
      limit: query.limit
    }
  })
}

/**
 * 新增采购零件
 * @param {PartsInfo} query
 */
export function addParts(query) {
  return request({
    url: '/partsInfo/addParts',
    method: 'post',
    params: {
      partsName: query.partsName,
      price: query.price,
      count: query.count
    }
  })
}

/**
 * 删除采购零件
 * @param {id} id
 */
export function dePartsId(id) {
  return request({
    url: '/partsInfo/deClient/' + id,
    method: 'post'
  })
}
/**
 * 更新采购零件信息
 * @param {PartsInfo} query
 */
export function upParts(query) {
  return request({
    url: '/partsInfo/upParts',
    method: 'post',
    params: {
      partsId: query.partsId,
      partsName: query.partsName,
      price: query.price,
      count: query.count
    }
  })
}
