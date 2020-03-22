<template>
  <div style="width: 80%">
    <a-form :form="form" :label-col="{ span: 7 }" :wrapper-col="{ span: 17 }" @submit="handleSubmit">
      <a-card style="width: 500px">
      <p style="text-align: left">Bitte melden Sie sich hier mit Laborkennung und Passwort an um einen Test als positiv / negativ zu kennzeichnen.</p>
      <a-divider orientation="left" style="margin-top: 2rem;">Laboranmeldung</a-divider>
        <a-form-item label="Laborkennung">
          <a-input
            v-decorator="['note', { rules: [{ required: true, message: 'Bitte geben Sie Ihre Laborkennung ein.' }] }]"
            placeholder="z.B 1337-4237-9438"
          />
        </a-form-item>
        <a-form-item label="Passwort">
          <a-input
            v-decorator="['note', { rules: [{ required: true, message: 'Bitte geben Sie Ihr Passwort ein.' }] }]"
            type="password"
          />
        </a-form-item>
        <a-divider orientation="left" style="margin-top: 2rem;">Testresultat eingeben</a-divider>
        <a-form-item label="Test-ID">
          <a-input
            v-decorator="['note', { rules: [{ required: true, message: 'Bitte geben Sie Ihre Test-ID ein.' }] }]"
            placeholder="z.B 1337-4237-9438"
          />
        </a-form-item>
        <a-form-item label="Testresultat">
          <a-radio-group v-decorator="['radio-group']">
            <a-radio value="true">
              Positiv
            </a-radio>
            <a-radio value="false">
              Negativ
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-divider />
        <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
          <a-button type="primary" html-type="submit">
            Abschicken
          </a-button>
        </a-form-item>
      </a-card>
    </a-form>
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
