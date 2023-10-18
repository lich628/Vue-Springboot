import request from '@/utils/request'

export function getList() {
  return request({
    url: '/record/list',
    method: 'get',
  })
}

export function insert(data) {
  return request({
    url: '/record/insert',
    method: 'post',
    data
  })
}

export function deleteTuple(id) {
  return request({
    url: '/record/delete',
    method: 'delete',
    params: { id }
  })
}