<template>
  <div>
    <div class="landing-header">
      <img
        src="../assets/logo.png"
        aspect-ratio="1.7"
        style="vertical-align: middle"
      />
      <h1 class="header-text" style="margin: 0 0 0 15px">Schnell. Einfach. Zuverlässig.</h1>
      <span style="flex: 1 1 auto"></span>
      <div class="header-buttons">
        <a-button type="primary" block="true" href="mailto:imis-team@gmx.de" icon="mail" shape="round">
          <span>Kontakt</span>
        </a-button>
        <a-button type="primary" block="true"
                  href="https://devpost.com/software/imis-infektions-melde-und-informations-system"
                  icon="bulb" shape="round">
          <span>DevPost</span>
        </a-button>
        <a-button type="primary" block="true" href="https://twitter.com/IMIS_team" icon="twitter" shape="round">
          <span>Twitter</span>
        </a-button>
        <a-button type="primary" block="true"
                  href="https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung"
                  icon="github" shape="round">
          <span>GitHub</span>
        </a-button>
      </div>
    </div>
    <!-- Header is fixed, so it is not included in layout -->
    <div style="height: 100px"></div>
    <div class="first-section" style="position: relative">
      <iframe width="560" height="315" src="https://www.youtube.com/embed/XIIlMh3Lbsc" frameborder="0"
              allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
              style="max-width: 100%"
              allowfullscreen></iframe>
      <div class="first-section-text">
        <div style="display: flex;">
          <h2 style="color: white;">Das Infektionsmelde- und Informationssystem</h2>
        </div>
        <div style="text-align: left; font-size: 16px">
          {{ paragraph1 }}
        </div>
        <br/>
        <div style="text-align: left">
          {{ paragraph2 }}
        </div>
      </div>
    </div>
    <div style="display: flex; justify-content: center; padding: 40px 20px; background: #d3d3d3">
      <router-link :to="{ name: 'app' }">
        <a-button
          style="flex:  0 1 300px; margin-left: 20px"
          type="primary"
          shape="round"
          icon="arrow-right"
          size="large"
          block
        >Hier geht's zur Live-Demo
        </a-button>
      </router-link>
    </div>
    <div class="second-section">
      <h1 style="margin: 0">Der IMIS-Meldeprozess - Einfach, Schnell, Zentral</h1>
      <img src="../assets/imis-workflow.png"/>
    </div>
      <div class="link-section">
      <a-button
        style="flex:  0 1 400px; margin-left: 20px"
        type="primary"
        shape="round"
        icon="bulb"
        size="large"
        href="https://drive.google.com/file/d/1hoQtGvO21p7_pQ63iDLoWzXB-FFfvKQJ/view?usp=sharing"
        block
      >
        <span class="hidden-mobile">Hier geht's zu unserer Prozessübersicht</span>
        <span class="hidden-desktop">Zur Prozessübersicht</span>
      </a-button>
    </div>
    <div class="third-section">
      <div>
        <img
          src="../assets/logo.png"
          aspect-ratio="1.7"
          style="vertical-align: middle; height: 100px"
        />
      </div>
      <div>
        <span>Kontakt:</span>
        <a-divider style="margin: 5px 0"></a-divider>
        <span>Andrey Eganov</span>
        <span>0681 / 500 66 72 0</span>
        <span>imis-team@gmx.de</span>
      </div>
      <div style="justify-content: center">
        &copy; 2020 Das IMIS Team
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LandingPage',
  data() {
    return {
      paragraph1:
          'Das Sammeln und Verwalten der aktuellen Infektionszahlen erfordert extrem viel Zeit und Ressourcen. IMIS ist unsere Lösung für eine schnelle, sichere und einfache Infektionsfall-Erfassung für Ärzte, Gesundheitsämter und Labore. Damit ermöglichen wir, dass wichtige Ressourcen frei werden, die dann an anderer Stelle gewinnbringend eingesetzt werden können.',
      paragraph2:
          'Die COVID-19-Pandemie stellt für das bestehende Infektionsmeldewesen in Deutschland eine immense Herausforderung dar. Ärzte und über 400 Gesundheitsämter benutzen gegenwärtig einen uneinheitlichen Mix aus Telefon, Fax, E-Mails und Excel-Tabellen, um Fälle zusammenzutragen. Eine starke Auslastung der Gesundheitsämter und verspätete Meldungen sind die Folge. So erhalten Entscheider häufig nur lückenhafte Statistiken. Diese Lage sorgt für Unsicherheit und macht es schwer, angemessen auf die Krise zu reagieren. ',

    }
  },
}
</script>

<style>

  .landing-header {
    background: white;
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    z-index: 9000;
    display: flex;
    align-items: center;
    padding: 0 25px;
    height: 100px;
    box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2), 0px 6px 10px 0px rgba(0, 0, 0, 0.14), 0px 1px 18px 0px rgba(0, 0, 0, 0.12);
  }

  .landing-header > img {
    height: 80%;
  }

  .header-buttons {
    display: flex;
    flex-wrap: wrap;
  }

  .header-buttons > .ant-btn {
    margin-left: 10px;
    margin-top: 5px;
    margin-bottom: 5px;
    flex: 0;
  }

  .first-section {
    display: flex;
    padding: 50px 50px 75px 50px;
    height: 600px;
    justify-content: center;
    align-items: center;
    /*background: #2c3e50;*/
    background: linear-gradient(
      rgba(0, 0, 0, 0.3),
      rgba(0, 0, 0, 0.3)
    ), url("../assets/wave-bg.svg");
    background-size: cover;
    color: rgba(255, 255, 255, 0.87);
  }

  .first-section-text {
    flex: 1 1 auto;
    max-width: 600px;
    padding-left: 50px;
  }

  .second-section {
    padding: 60px 50px 100px 50px;
    text-align: center;
    background: white;
  }

  .second-section > img {
    max-width: 1200px;
    width: 80%;
    margin-top: 50px;
  }

  .third-section {
    padding: 60px 50px;
    background: #2c3e50;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
  }

  .third-section > div {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    color: white;
    margin: 10px 25px;
  }

  .link-section {
    display: flex;
    justify-content: center;
    padding: 40px 20px;
    background: #d3d3d3;
  }

  .link-section span {
    margin-left: 5px;
  }

  @media (min-width: 1000px) {
    .hidden-desktop {
      display: none!important;
    }
  }

  @media (max-width: 1000px) {
    .first-section {
      flex-direction: column;
      height: auto;
      padding: 25px;
    }

    .first-section-text {
      padding-left: 0;
      padding-top: 50px;
    }

    .header-text {
      display: none;
    }

    .header-buttons > .ant-btn span {
      display: none;
    }

    .second-section {
      padding: 60px 25px 100px 25px;
    }

    .hidden-mobile {
      display: none !important;
    }
  }

</style>
