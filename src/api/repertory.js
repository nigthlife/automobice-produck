import request from '@/utils/request'

/**
 * 分页获取仓库中所有信息
 * @param {query} query
 */
export function getRepertoryAll(query) {
  if (query === undefined) {
    return request({
      url: '/repertory/obtainAll',
      method: 'post',
      params: {
        page: null,
        limit: null
      }
    })
  } else {
    return request({
      url: '/repertory/obtainAll',
      method: 'post',
      params: {
        page: query.page,
        limit: query.limit
      }
    })
  }
}

/**
 * 向仓库表中更新零件信息
 * @param {partsRepertory} query
 */
export function purchaseParts(query) {
  console.log('========purchaseParts======')
  console.log(query)
  return request({
    url: '/repertory/purchase',
    method: 'post',
    params: {
      partsId: query.partsId,
      count: query.purchaseNum
    }
  })
}

/**
 * 更新销售价格
 * @param {*} query
 */
export function upPartsPrice(query) {
  return request({
    url: '/repertory/upPrice/' + query.repertoryId + '/' + query.partsPrice,
    method: 'post'
  })
}

/**
 * 获取仓库中已有零件信息
 */
export function getPartsAll() {
  return request({
    url: '/repertory/obtainPartsAll/',
    method: 'post'
  })
}
