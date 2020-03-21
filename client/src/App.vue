<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <ExampleComponent/>
  </div>
</template>

<script>
import ExampleComponent from './components/ExampleComponent.vue'

export default {
  name: 'App',
  components: {
    ExampleComponent
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
