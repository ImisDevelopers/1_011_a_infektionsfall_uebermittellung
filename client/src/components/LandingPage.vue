<template>
    <div class="inner-body">
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
                    <a-button type="default" @click="showGithub" class="cta">GitHub</a-button>
                    <a-modal title="IMIS ist Open Source" class="video-wrapper" v-model="githubVisible">
                        IMIS ist ein Open Source Projekt. <a href="https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung" target="_blank">Fork me</a> auf Github und wirke an IMIS mit.
                    </a-modal>
                    <a-button type="default" @click="showVideo" class="cta">Video ansehen</a-button>
                    <a-modal title="Wie IMIS funktioniert" class="video-wrapper" v-model="videoVisible">
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
                <a-row :gutter="32" type="flex">
                    <a-col :span="12">
                        <a-card title="Problem" :bordered="false">
                            <p>Die COVID-19 Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Fax, E-Mails und Excel Tabellen um Fälle zusammenzutragen. Verspätet Meldungen an das RKI und überlastete Gesundheitsämter sind die Folge. So wurden laut RKI von einigen Gesundheitsämtern sogar keine Zahlen für dieses Wochenende übermittelt. Diese ungenaue Datenlage sorgt für eine große Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren.</p>
                        </a-card>
                        <a-card title="Lösung" :bordered="false">
                            <p>IMIS beschleunigt den Meldeprozess und sorgt damit für eine Entlastung in Gesundheitsämtern, Krankenhäusern und Laboren. Die Meldungen werden zentral erfasst und können in Echtzeit statistisch ausgewertet werden. IMIS schafft eine Vereinheitlichung des Meldeprozesses in Deutschland. IMIS stellt darüber hinaus Echtzeitdaten für Entscheidungsträger auf Landes- und Bundeseben zur Verfügung.</p>
                        </a-card>
                    </a-col>
                    <a-col :span="12" style="height: 100%">
                        <a-card :bordered="false">
                            <a-carousel arrows dotsClass="slick-dots slick-thumb">
                                <a slot="customPaging" slot-scope="props">
                                    <img :src="getImgUrl(props.i)" />
                                </a>
                                <div v-for="(item, i) in 5" :key="i">
                                    <img :src="require(`@/assets/carousel/carousel-image-${i+1}.png`)" />
                                </div>
                            </a-carousel>
                        </a-card>
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
                videoVisible: false,
                githubVisible: false,
                videoHeight: "auto",
            };
        },
        name: "LandingPage",
        methods: {
            showVideo() {
                this.videoVisible = true;
            },
            showGithub() {
                this.githubVisible = true;
            },
            getImgUrl(i) {
                return require(`@/assets/carousel/carousel-image-${i+1}.png`);
            },
        }
    }
</script>

<style scoped>
    .inner-body {
        background-color: #f5f5f5;
        min-height: 100vh;
    }

    section {
        padding: 2rem 0;
    }

    p {
        text-align: left;
        line-height: 1.5rem;
    }

    header {
        box-sizing: border-box;
        padding: 1rem 4rem;
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

    .ant-card {
        margin-bottom: 2rem;
    }

    .ant-carousel >>> .slick-dots {
        height: auto;
    }

    .ant-carousel >>> .slick-slide img {
        display: block;
        margin: auto;
        max-width: 100%;
    }

    .ant-carousel >>> .slick-thumb li {
        width: 60px;
        height: 45px;
    }

    .ant-carousel >>> .slick-thumb li img {
        width: 100%;
        height: 100%;
        filter: grayscale(100%);
    }

    .ant-carousel >>> .slick-thumb li.slick-active img {
        filter: grayscale(0%);
    }
</style>
