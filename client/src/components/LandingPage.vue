<template>
    <div>
        <header>
            <a-row type="flex" justify="space-between" align="middle">
                <a-col>
                    <a-row type="flex" align="middle">
                        <a-col>
                            <router-link to="/">
                                <img src="@/assets/imis-logo.svg" class="logo" alt="IMIS - Infektionsmelde- und Informationssystem">
                            </router-link>
                        </a-col>
                        <a-col>
                            <h2>Infektionsmelde- &#38; Informationssystem</h2>
                        </a-col>
                    </a-row>
                </a-col>
                <a-col>
                    <a-button type="primary" @click="showVideo" class="cta">Video ansehen</a-button>
                    <a-modal title="Wie IMIS funktioniert" class="video-wrapper" v-model="visible">
                        <template slot="footer"></template>
                        <iframe allowfullscreen="true" class="video" id="youtube" :style="{width: '100%', height: '260px' }" src="https://www.youtube.com/embed/XIIlMh3Lbsc">Video vom Browser nicht unterstützt.</iframe>
                    </a-modal>
                    <router-link to="/prototype" class="cta">
                        <a-button type="primary">
                            Zum Prototypen
                        </a-button>
                    </router-link>
                </a-col>
            </a-row>
        </header>
        <main>
            <section>
                <a-row :gutter="32">
                    <a-col :span="12">
                        <a-divider class="headline-divider" orientation="left">Problem</a-divider>
                        <p>Die COVID-19 Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Fax, E-Mails und Excel Tabellen um Fälle zusammenzutragen. Verspätet Meldungen an das RKI und überlastete Gesundheitsämter sind die Folge. So wurden laut RKI von einigen Gesundheitsämtern sogar keine Zahlen für dieses Wochenende übermittelt. Diese ungenaue Datenlage sorgt für eine große Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren.</p>
                    </a-col>
                    <a-col :span="12">
                        <a-divider class="headline-divider" orientation="left">Lösung</a-divider>
                        <p>IMIS beschleunigt den Meldeprozess und sorgt damit für eine Entlastung in Gesundheitsämtern, Krankenhäusern und Laboren. Die Meldungen werden zentral erfasst und können in Echtzeit statistisch ausgewertet werden. IMIS schafft eine Vereinheitlichung des Meldeprozesses in Deutschland. IMIS stellt darüber hinaus Echtzeitdaten für Entscheidungsträger auf Landes- und Bundeseben zur Verfügung.</p>
                    </a-col>
                </a-row>
            </section>
        </main>
        <!-- <div class="main">
            <div class="left-part">
                <div>
                    <h1><img src="../assets/logo.png" aspect-ratio="1.7" style="vertical-align: top">Schnell. Einfach. Zuverlässig.</h1>
                    <br>
                    <iframe allowfullscreen="true" id="youtube" v-bind:style="{width: '100%', height: 60/16*9+ 'vw'}" src="https://www.youtube.com/embed/XIIlMh3Lbsc">Video vom Browser nicht unterstützt.</iframe>
                </div><br>
                <p style="display:table-cell; width: 50%; padding-right: 5%">{{paragraph1}}</p>
                <p style="display:table-cell; width: 50%; padding-right: 5%">{{paragraph2}}</p>
            </div>
            <div class="transition"></div>
            <div class="right-part">
                <h1>Hier geht's zur Live-Demo:</h1>
                <a-button type="primary" shape="round" icon="arrow-right" size="large" href="/prototype" block>Zum Prototypen</a-button>
            </div>
        </div> -->
    </div>
</template>

<script>
    export default {
        components: {
        },
        data() {
            return {
                visible: false,
                videoHeight: "auto"
            };
        },
        name: "LandingPage",
        methods: {
            showVideo() {
                this.visible = true;
            },
        }
    }
</script>

<style scoped>
    p {
        text-align: left;
        line-height: 1.5rem;
    }

    header {
        box-sizing: border-box;
        padding: 1rem 4rem;
        padding-bottom: 5rem;
    }

    header h2 {
        margin: 0;
        margin-left: 2rem;
    }

    .cta {
        margin-left: 1rem;
    }

    .logo {
        width: 80px;
    }

    .video {
        border: none;
    }

    main {
        box-sizing: border-box;
        padding: 1rem 4rem;
    }

    .headline-divider {
        font-size: 1.4rem;
    }

    .main {
        background-color: whitesmoke;
        margin: 0 auto;
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    .left-part {
        display:table-cell;
        width: 60vw;
        height: 100vh;
        vertical-align: center;
        text-align: left;
        padding: 1%;
    }

    .transition {
        display: table-cell;
        background-image: linear-gradient(80deg, rgba(0,0,0,0) 0%, rgba(0,0,0,0) 50%, lightgrey 50%, lightgrey 100%);
        width: 15vw;
        height: 100vh;
    }

    .right-part {
        display: table-cell;
        width: 25vw;
        height: 100vh;
        background-color: lightgrey;
        text-align: left;
        vertical-align: middle;
    }
</style>
