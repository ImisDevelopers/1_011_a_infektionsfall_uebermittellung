<template>
  <div class="main">
    <div class="left-part">
      <div>
        <h1>
          <img
            src="../assets/logo.png"
            aspect-ratio="1.7"
            style="vertical-align: middle"
          />
          <span style="margin-left: 15px">Schnell. Einfach. Zuverlässig.</span>
        </h1>
        <br />
        <table>
          <tr>
            <td width="60%">
              <iframe
                allowfullscreen="true"
                id="youtube"
                v-bind:style="{ width: '100%', height: (38 / 16) * 9 + 'vw' }"
                src="https://www.youtube.com/embed/XIIlMh3Lbsc"
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
              <br />
              <a
                style="padding-left: 50px;"
                href="https://devpost.com/software/imis-infektions-melde-und-informations-system"
                >Mehr im DevPost</a
              >
            </td>
          </tr>
        </table>
      </div>
      <div>
        <img src="../assets/vision.png" width="75%" />
      </div>
      <br />
    </div>
    <div class="transition"></div>
    <div class="right-part">
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
        "Das Sammeln und Verwalten der aktuellen Infektionszahlen erfordert extrem viel Zeit und Ressourcen. IMIS ist unsere Lösung für eine einfache Infektionsfall-Erfassung für Ärzte, Gesundheitsämter und Labore. Damit ermöglichen wir, dass wichtige Ressourcen an anderer Stelle besser eingesetzt werden können.",
      paragraph2:
        "Die COVID-19 Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Fax, E-Mails und Excel Tabellen um Fälle zusammenzutragen. Verspätet Meldungen an das RKI und überlastete Gesundheitsämter sind die Folge. So wurden laut RKI von einigen Gesundheitsämtern sogar keine Zahlen für dieses Wochenende übermittelt. Diese ungenaue Datenlage sorgt für eine große Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren. "
    };
  },
  name: "LandingPage"
};
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
  display: table-cell;
  width: 70vw;
  height: 100vh;
  vertical-align: center;
  text-align: left;
  padding: 1%;
}

.transition {
  display: table-cell;
  background-image: linear-gradient(
    80deg,
    rgba(0, 0, 0, 0) 0%,
    rgba(0, 0, 0, 0) 50%,
    lightgrey 50%,
    lightgrey 100%
  );
  width: 10vw;
  height: 100vh;
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
