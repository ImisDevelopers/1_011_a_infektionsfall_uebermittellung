// Redirect to unsupported.html if the browser does not meet requirements
// Actually only checking for IE (any version)
const isIE10OrLower = (window.navigator.userAgent.indexOf('MSIE ') > 0)
const isIE11 = (window.navigator.userAgent.indexOf('Trident/') > 0)
if (isIE10OrLower || isIE11) {
  window.location.href = 'unsupported.html'
}
