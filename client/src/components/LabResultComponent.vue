<template>
  <div>
    <a-tabs defaultActiveKey="1" v-model="activeKey" @change="callback">
      <a-tab-pane tab="Laboranmeldung" key="1">
        <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
          <a-form :form="form" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
            <p>Bitte melden Sie sich hier mit Laborkennung und Passwort an um einen Test als positiv / negativ zu kennzeichnen.</p>
            <!-- <a-divider orientation="left" style="margin-top: 2rem;">Laboranmeldung</a-divider> -->
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
            <a-divider />
            <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
              <a-button type="primary" html-type="submit" @click="handleLabSubmit">
                Abschicken
              </a-button>
            </a-form-item>
          </a-form>
        </a-card>
      </a-tab-pane>
      <a-tab-pane tab="Testresultat eingeben" :disabled="isLoggedIn === false ? 'disabled' : false" key="2" forceRender>
        <a-card style="width: 500px; margin: 2rem auto; min-height: 300px">
          <a-form :form="form" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" @submit="handleSubmit">
            <a-form-item label="Proben-ID">
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
          </a-form>
        </a-card>
      </a-tab-pane>
    </a-tabs>
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
      content: "",
      activeKey: "1",
      isLoggedIn: false
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
    },
    handleLabSubmit() {
      this.isLoggedIn = true
      this.activeKey = "2";
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
