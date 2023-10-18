import request from '@/utils/request'

export function getList() {
  return request({
    url: '/chart/piechart',
    method: 'get',
  })
}