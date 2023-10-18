import defaultSettings from '@/settings'

const title = defaultSettings.title || '应急物资管理'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
