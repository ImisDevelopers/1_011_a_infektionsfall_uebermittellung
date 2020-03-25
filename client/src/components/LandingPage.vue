<template>
    <div class="main">
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
    </div>
</template>

<script>
    export default {
        components: {
        },
        data() {
            return {
                paragraph1: "Das Sammeln und Verwalten der aktuellen Infektionszahlen erfordert extrem viel Zeit und Ressourcen. IMIS ist unsere Lösung für eine einfache Infektionsfall-Erfassung für Ärzte, Gesundheitsämter und Labore. Damit ermöglichen wir, dass wichtige Ressourcen an anderer Stelle besser eingesetzt werden können.",
                paragraph2: "Die COVID-19 Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Fax, E-Mails und Excel Tabellen um Fälle zusammenzutragen. Verspätet Meldungen an das RKI und überlastete Gesundheitsämter sind die Folge. So wurden laut RKI von einigen Gesundheitsämtern sogar keine Zahlen für dieses Wochenende übermittelt. Diese ungenaue Datenlage sorgt für eine große Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren. ",
            };
        },
        name: "LandingPage"
    }
</script>

<style>
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
