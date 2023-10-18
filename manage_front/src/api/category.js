import request from '@/utils/request'

export function getList() {
  return request({
    url: '/category/list',
    method: 'get',
  })
}

export function insert(data) {
  return request({
    url: '/category/insert',
    method: 'post',
    headers: { "Content-Type": "multipart/form-data" },
    data
  })
}

export function deleteTuple(id) {
  return request({
    url: '/category/delete',
    method: 'delete',
    params: { id }
  })
}