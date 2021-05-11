import { getSubscribeAll, upSubscribe, InSubscribe, DelSubscribe } from '@/api/subscribe'

const actions = {
  getSubAll() {
    return new Promise((resolve, reject) => {
      getSubscribeAll().then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  upSubscribeData() {
    return new Promise((resolve, reject) => {
      upSubscribe().then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  inSubscribeData() {
    return new Promise((resolve, reject) => {
      InSubscribe().then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  },
  deSubscribeData() {
    return new Promise((resolve, reject) => {
      DelSubscribe().then(res => {
        resolve(res)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  actions
}
