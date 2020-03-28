<template>
    <a-card style="width: 500px; margin: 4rem auto; min-height: 300px;">
        <a-form :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" :form="form">
            <div class="login-header">
                <img
                        src="../assets/logo.png"
                        height="100"
                />
                <p>Login</p>
            </div>
            <a-form-item label="Kennung">
                <a-input
                        v-decorator="['id', { rules: [{
                            required: true, message: 'Bitte geben Sie Ihre Kennung ein.'
                        }]}]"
                        placeholder="z.B 1337-4237-9438"
                >
                    <a-icon slot="prefix" type="user" />
                </a-input>
            </a-form-item>
            <a-form-item label="Passwort">
                <a-input
                        v-decorator="['password', { rules: [{
                            required: true, message: 'Bitte geben Sie Ihr Passwort ein.'
                        }]}]"
                        type="password"
                        placeholder="**********"
                >
                    <a-icon slot="prefix" type="lock" />
                </a-input>
            </a-form-item>
            <a-divider/>
            <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
                <a-button type="primary" html-type="submit" @click="handleLogin">
                    Einloggen
                </a-button>
            </a-form-item>
        </a-form>
        <p class="test-access">
            Testzugang <i><b>1234</b></i> mit Password <i><b>asdf</b></i>
        </p>
    </a-card>
</template>

<script>

import { authenticationStore } from "../util/auth";
import Api from "../api/Api";

export default {
    name: "Login",
    created() {
        authenticationStore.clearAuthentication();
    },
    props: {},
    data() {
        return {
            form: this.$form.createForm(this),
            authenticationStore: authenticationStore
        };
    },
    methods: {
        handleLogin(e) {
            e.preventDefault();

            this.form.validateFields((err, values) => {
                if (err) {
                    return;
                }

                // Query param
                let forwardToPath = this.$route.query.forwardTo;
                if (!forwardToPath) {
                    // TODO: Where to forward to when user directly accessed the login page?
                    forwardToPath = "/prototype/register-patient";
                }
                if (values.id === "1234" && values.password === "asdf") {
                    // TODO: Entfernen, wenn auth läuft
                    authenticationStore.user = {
                        token: "test",
                    };
                    localStorage.setItem("user", "test");
                    this.$router.push({path: forwardToPath});
                } else {
                    Api.postAuthentication({
                        userName: values.id,
                        password: values.password
                    }).then(tokenObject => {
                        // TODO: Decode JWT to get user object
                        const token = tokenObject.token;
                        // TODO: Parse token
                        authenticationStore.user = {
                            token: token,
                        };
                        localStorage.setItem("user", token);
                        this.$router.push({path: forwardToPath});
                    }, error => {
                        console.error("Error at login: ");
                        console.error(error);
                        this.$notification["error"]({
                            message: "Login Fehler",
                            description: "Kennung und / oder Password nicht korrekt."
                        });
                    });
                }

            });
        }
    }
};
</script>

<style scoped>
.test-access {
    color: red;
}

.login-header {
    display: flex;
    align-items: center;
    justify-content: center;
    padding-bottom: 2rem;
}

.login-header > img {
    vertical-align: middle;
    margin-right: 25px;
}

.login-header > p {
    color: rgba(0, 0, 0, 0.87);
    margin: 0;
    font-size: 32px;
    font-weight: 300;
}
</style>
