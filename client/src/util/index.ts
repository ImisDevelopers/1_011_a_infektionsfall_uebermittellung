export function parseJwt(token: string): any {
  const base64Url = token.split('.')[1]
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
  const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
  }).join(''))
  return JSON.parse(jsonPayload)
}

export function anonymizeProperties(keys: any[], obj: any) {
  keys.forEach(key => {
    if (typeof key === 'string' && obj[key]) {
      obj[key] = obj[key].substr(0, 1) + '**********'
    } else if (typeof key === 'object' && key.type === 'number' && obj[key]) {
      obj[key.key] = 11111
    }
  })
}
