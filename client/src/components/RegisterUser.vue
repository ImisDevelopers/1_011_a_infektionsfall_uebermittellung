<template>
    <a-card style="width: 500px; margin: 4rem auto; min-height: 300px;">
        <a-form :label-col="{ span: 10 }" :wrapper-col="{ span: 14 }" :form="form">
            <p class="register-header">Registrierung</p>
            <a-form-item label="Kennung">
                <a-input
                        v-decorator="[
            'id',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte Kennung eingeben.'
                }
              ]
            }
          ]"
                        placeholder="z.B 1337-4237-9438"
                />
            </a-form-item>
            <a-form-item label="Passwort">
                <a-input
                        v-decorator="[
            'password',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte Passwort eingeben.'
                }
              ]
            }
          ]"
                        type="password"
                        placeholder="**********"
                />
            </a-form-item>
            <a-form-item label="Passwort wiederholen">
                <a-input
                        v-decorator="[
            'password-repeat',
            {
              rules: [
                {
                  required: true,
                  message: 'Bitte Passwort wiederholen.'
                }
              ]
            }
          ]"
                        type="password"
                        placeholder="**********"
                />
            </a-form-item>
            <a-form-item label="Rolle">
                <a-select
                        v-decorator="[
                    'institutionType',
                    {
                      rules: [
                        {
                          required: true,
                          message: 'Bitte Rolle auswählen!'
                        }
                      ]
                    }
                  ]"
                        placeholder="Bitte wählen..."
                >
                    <a-select-option value="LABORATORY">Labor</a-select-option>
                    <a-select-option value="DOCTORS_OFFICE">Arztpraxis</a-select-option>
                    <a-select-option value="CLINIC">Klinik</a-select-option>
                    <a-select-option value="TEST_SITE">Teststelle</a-select-option>
                </a-select>
            </a-form-item>
            <a-divider/>
            <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
                <a-button type="primary" html-type="submit" @click="handleRegister">
                    Registrieren
                </a-button>
            </a-form-item>
        </a-form>
        <p class="test-access">
            Eine Selbstregistrierung ist im Prototypen nur zu Testzwecken möglich.
        </p>
    </a-card>
</template>

<script>
import Api from "../api/Api";

export default {
    name: "RegisterUser",
    props: {},
    data() {
        return {
            form: this.$form.createForm(this),
        };
    },
    methods: {
        handleRegister(e) {
            e.preventDefault();

            this.form.validateFields((err, values) => {
                if (err) {
                    console.error(err);
                    return;
                }

                // Query param
                let forwardToPath = this.$route.query.forwardTo;
                if (!forwardToPath) {
                    // TODO: Where to forward to when user directly accessed the login page?
                    forwardToPath = "/prototype/register-patient";
                }
                Api.postRegister({
                    userName: values.id,
                    password: values.password
                }).then((result, error) => {
                    if (error) {
                        console.error("Error at login: ");
                        console.error(error);
                        this.$notification["error"]({
                            message: "Fehler bei Registrierung",
                            description: error.message
                        });
                    } else {
                        this.$notification["info"]({
                            message: "Registrierung erfolgreich",
                        });
                    }
                });
                this.$router.push({path: forwardToPath});

            });
        }
    }
}
</script>

<style scoped>
.register-header {
    font-size: 28px;
    font-weight: 300;
}

.test-access {
    color: red;
    font-weight: bold;
}
</style>
