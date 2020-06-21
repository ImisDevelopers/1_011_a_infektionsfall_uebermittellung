module.exports = {
  devServer: {
    proxy: 'http://localhost:80',
    // Alternative config to run the app locally without root:
    // proxy: {
    //   '^/api': {
    //     target: 'http://localhost:8642',
    //     changeOrigin: true,
    //     pathRewrite: {
    //       '^/api': '/',
    //     },
    //     logLevel: 'debug',
    //   },
    // },
  },
}
