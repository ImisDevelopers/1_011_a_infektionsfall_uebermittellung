<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style scoped lang="scss">
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;

    a {
      font-weight: bold;
      color: #2c3e50;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }

  // CSS classes used by multiple components
  .imis-radio-group {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    padding-left: 15px;

    > label {
      padding: 10px 0 10px 15px;
      display: flex;
      align-items: center;
    }

    > label:hover {
      background: rgba(0, 0, 0, 0.1);
    }

    .ant-radio {
      margin-right: 10px;
    }

    i {
      margin-right: 10px;
    }
  }

</style>
