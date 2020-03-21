<template>
  <div>
    <h3>Example Entities</h3>
    <div v-if="entities.length === 0">
      No Example Entities created yet...
    </div>
    <div v-else v-for="(entity, idx) in entities" :key="idx">
      <span>{{entity.id}}</span>
      <span>......</span>
      <span>{{entity.content}}</span>
    </div>
    <h3>Create an Example Entity</h3>
    <form @submit="create" autocomplete="off">
      <span>Content:</span>
      <input v-model="content"/>
      <button>Create</button>
    </form>
  </div>
</template>

// Labs erstellen Berichte zu Tests/ProbeIDs

<script>
export default {
  name: 'LabResultComponent',
  props: {
    msg: String
  },
  data() {
    return {
      entities: [],
      content: ""
    }
  },
  methods: {
    create(e) {
      e.preventDefault();
      if (this.content.length > 0) {
        fetch('/exampleEntities',
                {
                  method: 'POST',
                  body: JSON.stringify({ content: this.content }),
                  headers: {
                    'Content-Type': 'application/json'
                  }
                })
        .then((response) => {
          return response.json();
        })
        .then((entity) => {
          this.entities.push(entity);
        });
        this.content = "";
      }
    }
  },
  created() {
    fetch('/exampleEntities')
    .then((response) => {
      return response.json();
    })
    .then((entities) => {
      this.entities.push(...entities);
    });
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 20px 10px;
}
span {
  margin: 10px;
}
</style>
