<template>
  <div>
    <div class="landing-header">
      <img
        aspect-ratio="1.7"
        src="../assets/logo.png"
        style="vertical-align: middle"
      />
      <h1 class="header-text" style="margin: 0 0 0 15px">Schnell. Einfach. Zuverlässig.</h1>
      <span style="flex: 1 1 auto"></span>
      <div class="header-buttons">
        <a-button :block="true" href="mailto:imis-team@gmx.de" icon="mail" shape="round" type="primary">
          <span>Kontakt</span>
        </a-button>
        <a-button :block="true" href="https://devpost.com/software/imis-infektions-melde-und-informations-system"
                  icon="bulb"
                  shape="round" type="primary">
          <span>DevPost</span>
        </a-button>
        <a-button :block="true" href="https://twitter.com/IMIS_team" icon="twitter" shape="round" type="primary">
          <span>Twitter</span>
        </a-button>
        <a-button :block="true" href="https://github.com/ImisDevelopers/1_011_a_infektionsfall_uebermittellung"
                  icon="github"
                  shape="round" type="primary">
          <span>GitHub</span>
        </a-button>
      </div>
    </div>
    <!-- Header is fixed, so it is not included in layout -->
    <div style="height: 100px"></div>
    <div class="first-section" style="position: relative">
      <iframe allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen frameborder="0" height="315"
              src="https://www.youtube.com/embed/XIIlMh3Lbsc"
              style="max-width: 100%"
              width="560"></iframe>
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
    <div class="button-container">
      <router-link :to="{ name: 'public-register' }" style="margin: 10px 20px 10px 0">
        <a-button
          block
          icon="arrow-right"
          shape="round"
          size="large"
          style="flex:  0 1 300px; margin-left: 20px"
          type="primary"
        >Zur Sebstregistrierung
        </a-button>
      </router-link>
      <router-link :to="{ name: 'app' }" style="margin: 10px 0">
        <a-button
          block
          icon="arrow-right"
          shape="round"
          size="large"
          style="flex:  0 1 300px; margin-left: 20px"
          type="primary"
        >Zur Live-Demo
        </a-button>
      </router-link>
    </div>
    <div class="second-section">
      <h1 style="margin: 0">Der IMIS-Meldeprozess - Einfach, Schnell, Zentral</h1>
      <img src="../assets/imis-workflow.png"/>
    </div>
    <div class="link-section">
      <a-button
        block
        href="https://drive.google.com/file/d/1hoQtGvO21p7_pQ63iDLoWzXB-FFfvKQJ/view?usp=sharing"
        icon="bulb"
        shape="round"
        size="large"
        style="flex:  0 1 400px; margin-left: 20px"
        type="primary"
      >
        <span class="hidden-mobile">Hier geht's zu unserer Prozessübersicht</span>
        <span class="hidden-desktop">Zur Prozessübersicht</span>
      </a-button>
    </div>
    <div class="solution-enabler-section">
      <h1>Gemeinsam gegen COVID-19</h1>
      <h2 style="max-width: 800px; text-align: center;">IMIS ist aus dem <a href="https://wirvsvirushackathon.org/">#WirVsVirus</a>-Hackathon
        hervorgegangen und
        wird nun von einem Team aus über 30 Leuten aktiv weiterentwickelt.</h2>
      <!--      <a-divider style="max-width: 1000px; min-width: 0"></a-divider>-->
      <div class="solution-enabler-program">
        <img src="../assets/Solution-Enabler.png"/>
        <div>
          <h2>Als eines von 130 aus über 1500 Projekten ist IMIS in den
            <a href="https://wirvsvirushackathon.org/weiterfuehrung-2/">Solution Enabler</a>
            aufgenommen worden.</h2>
          <h3>Dadurch erhalten wir Zugriff auf Coaching- und Unterstützungsangebote sowie Ressourcen und
            Expertise.</h3>
        </div>
      </div>
      <!--      <a-divider style="max-width: 1000px; min-width: 0"></a-divider>-->
      <div class="solution-enabler-our-team">
        <div>
          <h2>Eine anspruchsvolle Aufgabe.</h2>
          <h3>Und deshalb sind wir ein vielfältiges Team aus Organisatoren und Projektkoordinatoren, Frontend- und
            Backend-Entwicklern, UI-Designern, Datenbankspezialisten und vielen mehr.</h3>
        </div>
        <a-icon :style="{ fontSize: '128px', color: '#2c3e50' }" style="margin-bottom: 25px" type="team"/>
      </div>
    </div>
    <div class="third-section">
      <div>
        <img
          aspect-ratio="1.7"
          src="../assets/logo.png"
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

<style lang="scss">

  .button-container {
    display: flex;
    justify-content: center;
    padding: 30px 20px;
    background: #d3d3d3;
    flex-wrap: wrap;
  }

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

    > img {
      height: 80%;
    }
  }

  .header-buttons {
    display: flex;
    flex-wrap: wrap;

    > .ant-btn {
      margin-left: 10px;
      margin-top: 5px;
      margin-bottom: 5px;
      flex: 0;
    }
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

    .first-section-text {
      flex: 1 1 auto;
      max-width: 600px;
      padding-left: 50px;
    }
  }

  .second-section {
    padding: 60px 50px 100px 50px;
    text-align: center;
    background: white;

    > img {
      max-width: 1200px;
      width: 80%;
      margin-top: 50px;
    }
  }

  .solution-enabler-section {
    padding: 60px 50px 100px 50px;
    display: flex;
    flex-direction: column;
    align-items: center;

    > h2 {
      margin-bottom: 30px;
    }

    .solution-enabler-program {
      display: flex;
      flex-wrap: wrap;
      max-width: 1000px;
      align-items: center;
      box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2), 0px 6px 10px 0px rgba(0, 0, 0, 0.14), 0px 1px 18px 0px rgba(0, 0, 0, 0.12);
      border-radius: 25px;
      margin: 30px 0;

      img {
        width: 100%;
        max-width: 500px;
        margin-bottom: 25px;
        margin-top: 25px;
      }

      div {
        flex: 1 0 300px;
        text-align: left;
        margin: 25px;
      }
    }

    .solution-enabler-our-team {
      display: flex;
      flex-wrap: wrap;
      max-width: 800px;
      align-items: center;
      padding-top: 50px;

      img {
        display: flex;
        flex-wrap: wrap;
        max-width: 800px;
        align-items: center;
        padding-top: 75px;
      }

      div {
        flex: 1 0 300px;
        text-align: left;
        margin-right: 25px;
      }
    }

  }

  .third-section {
    padding: 100px 50px;
    background: linear-gradient(
        rgba(0, 0, 0, 0.3),
        rgba(0, 0, 0, 0.3)
    ), url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 2000 1500'%3E%3Cdefs%3E%3CradialGradient id='a' gradientUnits='objectBoundingBox'%3E%3Cstop offset='0' stop-color='%23122b7d'/%3E%3Cstop offset='1' stop-color='%23195eff'/%3E%3C/radialGradient%3E%3ClinearGradient id='b' gradientUnits='userSpaceOnUse' x1='0' y1='750' x2='1550' y2='750'%3E%3Cstop offset='0' stop-color='%231645be'/%3E%3Cstop offset='1' stop-color='%23195eff'/%3E%3C/linearGradient%3E%3Cpath id='s' fill='url(%23b)' d='M1549.2 51.6c-5.4 99.1-20.2 197.6-44.2 293.6c-24.1 96-57.4 189.4-99.3 278.6c-41.9 89.2-92.4 174.1-150.3 253.3c-58 79.2-123.4 152.6-195.1 219c-71.7 66.4-149.6 125.8-232.2 177.2c-82.7 51.4-170.1 94.7-260.7 129.1c-90.6 34.4-184.4 60-279.5 76.3C192.6 1495 96.1 1502 0 1500c96.1-2.1 191.8-13.3 285.4-33.6c93.6-20.2 185-49.5 272.5-87.2c87.6-37.7 171.3-83.8 249.6-137.3c78.4-53.5 151.5-114.5 217.9-181.7c66.5-67.2 126.4-140.7 178.6-218.9c52.3-78.3 96.9-161.4 133-247.9c36.1-86.5 63.8-176.2 82.6-267.6c18.8-91.4 28.6-184.4 29.6-277.4c0.3-27.6 23.2-48.7 50.8-48.4s49.5 21.8 49.2 49.5c0 0.7 0 1.3-0.1 2L1549.2 51.6z'/%3E%3Cg id='g'%3E%3Cuse href='%23s' transform='scale(0.12) rotate(60)'/%3E%3Cuse href='%23s' transform='scale(0.2) rotate(10)'/%3E%3Cuse href='%23s' transform='scale(0.25) rotate(40)'/%3E%3Cuse href='%23s' transform='scale(0.3) rotate(-20)'/%3E%3Cuse href='%23s' transform='scale(0.4) rotate(-30)'/%3E%3Cuse href='%23s' transform='scale(0.5) rotate(20)'/%3E%3Cuse href='%23s' transform='scale(0.6) rotate(60)'/%3E%3Cuse href='%23s' transform='scale(0.7) rotate(10)'/%3E%3Cuse href='%23s' transform='scale(0.835) rotate(-40)'/%3E%3Cuse href='%23s' transform='scale(0.9) rotate(40)'/%3E%3Cuse href='%23s' transform='scale(1.05) rotate(25)'/%3E%3Cuse href='%23s' transform='scale(1.2) rotate(8)'/%3E%3Cuse href='%23s' transform='scale(1.333) rotate(-60)'/%3E%3Cuse href='%23s' transform='scale(1.45) rotate(-30)'/%3E%3Cuse href='%23s' transform='scale(1.6) rotate(10)'/%3E%3C/g%3E%3C/defs%3E%3Cg %3E%3Cg transform=''%3E%3Ccircle fill='url(%23a)' r='3000'/%3E%3Cg opacity='0.5'%3E%3Ccircle fill='url(%23a)' r='2000'/%3E%3Ccircle fill='url(%23a)' r='1800'/%3E%3Ccircle fill='url(%23a)' r='1700'/%3E%3Ccircle fill='url(%23a)' r='1651'/%3E%3Ccircle fill='url(%23a)' r='1450'/%3E%3Ccircle fill='url(%23a)' r='1250'/%3E%3Ccircle fill='url(%23a)' r='1175'/%3E%3Ccircle fill='url(%23a)' r='900'/%3E%3Ccircle fill='url(%23a)' r='750'/%3E%3Ccircle fill='url(%23a)' r='500'/%3E%3Ccircle fill='url(%23a)' r='380'/%3E%3Ccircle fill='url(%23a)' r='250'/%3E%3C/g%3E%3Cg transform=''%3E%3Cuse href='%23g' transform='rotate(10)'/%3E%3Cuse href='%23g' transform='rotate(120)'/%3E%3Cuse href='%23g' transform='rotate(240)'/%3E%3C/g%3E%3Ccircle fill-opacity='0.13' fill='url(%23a)' r='3000'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
    background-size: cover;
    /*background: #2c3e50;*/
    display: flex;
    justify-content: center;
    flex-wrap: wrap;

    > div {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      color: white;
      margin: 10px 25px;
    }
  }

  .link-section {
    display: flex;
    justify-content: center;
    padding: 40px 20px;
    background: #d3d3d3;

    span {
      margin-left: 5px;
    }
  }

  @media (min-width: 1000px) {
    .hidden-desktop {
      display: none !important;
    }
  }

  @media (max-width: 550px) {

    .first-section {
      .first-section-text {
        padding-left: 0;
      }
    }

    .solution-enabler-section {
      .solution-enabler-our-team {
        flex-direction: column-reverse;
      }

      .solution-enabler-program {
        div {
          margin: 15px;
        }
      }
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
