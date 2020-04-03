<template>
  <div class="main">
    <div class="left-part">
      <div>
        <h1>
          <img
            src="../assets/logo.png"
            aspect-ratio="1.7"
            style="vertical-align: middle"
            height="140"
          />
          <span style="margin-left: 15px">Schnell. Einfach. Zuverlässig.</span>
        </h1>
        <br />
        <table>
          <tr style="vertical-align: middle;">
            <td width="60%">
              <iframe
                allowfullscreen="true"
                id="youtube"
                v-bind:style="{ width: '100%', height: (38 / 16) * 9 + 'vw' }"
                src="https://www.youtube.com/embed/XIIlMh3Lbsc"
                style="vertical-align: top"
                >Video vom Browser nicht unterstützt.
              </iframe>
            </td>
            <td width="40%">
              <div style="padding-left: 50px; text-align: justify">
                {{ paragraph1 }}
              </div>
              <br />
              <div style="padding-left: 50px; text-align: justify">
                {{ paragraph2 }}
              </div>
              <div style="padding-left: 50px; text-align: justify">
                <a href="https://drive.google.com/file/d/1sATb_ff2r79Ts9hdNiH9Slsexv47aR3x/view?usp=sharing" target="_blank">Hier geht's zu unserer Prozessübersicht.</a>
              </div>
            </td>
          </tr>
        </table>
      </div>
      <br>
      <div>
        <h2 style="text-align: center; width:80%">Unsere Vision</h2>
        <img src="../assets/vision.png" width="80%"/>
      </div>
      <br />
    </div>
    <div class="transition"></div>
    <div class="right-part">
      <table style="position: absolute; top: 1rem; right: 1rem;">
        <tr>
          <td><a-button type="primary" block="true"  href="mailto:imis-team@gmx.de" icon="mail" shape="round">Kontakt</a-button></td>
          <td><a-button type="primary" block="true" href="https://devpost.com/software/imis-infektions-melde-und-informations-system" icon="bulb" shape="round">DevPost</a-button></td>
          <td><a-button type="primary" block="true" href="https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung" icon="tool" shape="round">GitHub</a-button></td>
        </tr>
      </table>
      <h1>Hier geht's zur Live-Demo:</h1>
      <a-button
        type="primary"
        shape="round"
        icon="arrow-right"
        size="large"
        href="/prototype/register-patient"
        block
        >Zum Prototypen
      </a-button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      paragraph1:
        "Das Sammeln und Verwalten der aktuellen Infektionszahlen erfordert extrem viel Zeit und Ressourcen. IMIS ist unsere Lösung für eine schnelle, sichere und einfache Infektionsfall-Erfassung für Ärzte, Gesundheitsämter und Labore. Damit ermöglichen wir, dass wichtige Ressourcen frei werden, die dann an anderer Stelle gewinnbringend eingesetzt werden können.",
      paragraph2:
        "Die COVID-19-Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Telefon, Fax, E-Mails und Excel-Tabellen, um Fälle zusammenzutragen. Eine starke Auslastung der Gesundheitsämter und verspätete Meldungen sind die Folge. So erhalten Entscheider häufig nur lückenhafte Statistiken. Diese Lage sorgt für Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren. ",

    };
  },
  name: "LandingPage"
};
</script>

<style>
.main {
    position: relative;
    background-color: whitesmoke;
    margin: 0 auto;
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

.left-part {
    display: table-cell;
    width: 70vw;
    height: 100vh;
    text-align: left;
    padding: 1%;
}

.transition {
    display: table-cell;
    background-image: linear-gradient(
        85deg,
        rgba(0, 0, 0, 0) 0%,
        rgba(0, 0, 0, 0) 50%,
        lightgrey 50%,
        lightgrey 100%
    );
    width: 10vw;
    height: 100%;
}

.right-part {
    display: table-cell;
    width: 20vw;
    height: 100vh;
    background-color: lightgrey;
    text-align: left;
    vertical-align: middle;
    padding-right: 40px;
}
</style>
