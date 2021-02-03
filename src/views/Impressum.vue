<template>
  <div>
    <div class="landing-header">
      <img
          aspect-ratio="1.7"
          src="../assets/logo.png"
          style="vertical-align: middle"
      />
      <a href="/">
        <h1 class="header-text" style="margin: 0 0 0 15px">
          Schnell. Einfach. Zuverlässig.
        </h1>
      </a>

      <span style="flex: 1 1 auto"></span>
      <div class="header-buttons">
        <a-button
          :block="true"
          href="mailto:imis-team@gmx.de"
          icon="mail"
          shape="round"
          type="primary"
        >
          <span>Kontakt</span>
        </a-button>
        <a-button
          :block="true"
          href="https://twitter.com/IMIS_team"
          icon="twitter"
          shape="round"
          type="primary"
        >
          <span>Twitter</span>
        </a-button>
        <a-button
          :block="true"
          href="https://www.startnext.com/imis-infektionsmanagement"
          icon="euro"
          shape="round"
          type="primary"
        >
          <span>Unterstützen</span>
        </a-button>
      </div>
    </div>
    <!-- Header is fixed, so it is not included in layout -->
    <div style="height: 100px;"></div>
    <div class="first-section" style="position: relative">
      <div
          style="max-width: 100%"
          width="560"
      ></div>
      <div>
        <div>
          <span>IMIS GbR</span>
          <a-divider style="margin: 5px 0"></a-divider>
          <span>Vertreten duch: Achim Löbke</span><br>
          <span>Tüleck 1, 48336 Sassenberg</span><br>
          <span>0681 / 500 66 72 0</span><br>
          <span>imis-team@gmx.de</span><br>
        </div>
        <br>
        <br>
        <div style="text-align: left; font-size: 16px">
          Die Nutzung unserer Webseite ist in der Regel ohne Angabe personenbezogener Daten möglich. Soweit auf unseren Seiten personenbezogene Daten (beispielsweise Name, Anschrift oder eMail-Adressen) erhoben werden, erfolgt dies, soweit möglich, stets auf freiwilliger Basis. Diese Daten werden ohne Ihre ausdrückliche Zustimmung nicht an Dritte weitergegeben.
          Wir weisen darauf hin, dass die Datenübertragung im Internet (z.B. bei der Kommunikation per E-Mail) Sicherheitslücken aufweisen kann. Ein lückenloser Schutz der Daten vor dem Zugriff durch Dritte ist nicht möglich.
          Der Nutzung von im Rahmen der Impressumspflicht veröffentlichten Kontaktdaten durch Dritte zur Übersendung von nicht ausdrücklich angeforderter Werbung und Informationsmaterialien wird hiermit ausdrücklich widersprochen. Die Betreiber der Seiten behalten sich ausdrücklich rechtliche Schritte im Falle der unverlangten Zusendung von Werbeinformationen, etwa durch Spam-Mails, vor.
           </div>
        <br>
        <div style="text-align: left; font-size: 16px">
          Unser Angebot enthält Links zu externen Webseiten Dritter, auf deren Inhalte wir keinen Einfluss haben. Deshalb können wir für diese fremden Inhalte auch keine Gewähr übernehmen. Für die Inhalte der verlinkten Seiten ist stets der jeweilige Anbieter oder Betreiber der Seiten verantwortlich. Die verlinkten Seiten wurden zum Zeitpunkt der Verlinkung auf mögliche Rechtsverstöße überprüft. Rechtswidrige Inhalte waren zum Zeitpunkt der Verlinkung nicht erkennbar. Eine permanente inhaltliche Kontrolle der verlinkten Seiten ist jedoch ohne konkrete Anhaltspunkte einer Rechtsverletzung nicht zumutbar. Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Links umgehend entfernen.
        </div>
        </div>
      <br>
      <br>

    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'LandingPage',
  data() {
    return {
      paragraph1:
        'Wir wollen die Corona-Pandemie in den Griff bekommen und Leben retten. Unser Ansatz? Gesundheitsämter mit einer maßgeschneiderten digitalen Lösung ausstatten und durch schnelle Prozesse die Ausbreitung des Virus stoppen! ',
      paragraph2:
          'Gesundheitsämter sind der Dreh- und Angelpunkt der Corona-Bekämpfung. Doch viele Arbeitsschritte gehen dort noch händisch und daher schleppend langsam vonstatten. Papierberge, Fax-Stapel, Zeitdruck und Frustration stehen schnellen Maßnahmen im Weg. Unser Ziel ist es, Arbeitsabläufe mit Bezug zum Infektionsschutz in Gesundheitsämtern zu digitalisieren. Dazu kooperieren wir mit dem Helmholtz-Zentrum für Infektionsforschung, indem wir aktiv Beträge zum Gesundheitsmanagement-System SORMAS Leisten. ',
      paragraph3:
          'Als Open-Source-Projekt ist unser Ziel, in Kooperation mit anderen Projekten zur Schaffung einheitlicher Standards und der Entwicklung von hochwertigen Lösungen zur Pandemie-Bekämpfung in Deutschland und international beizutragen.',
    }
  },
})
</script>

<style scoped lang="scss">
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
  box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2),
    0px 6px 10px 0px rgba(0, 0, 0, 0.14), 0px 1px 18px 0px rgba(0, 0, 0, 0.12);

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
  height: calc(100vh - 100px);
  justify-content: center;
  align-items: center;
  /*background: #2c3e50;*/
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)),
    url('../assets/wave-bg.svg');
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
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
  flex-wrap: wrap;
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
    box-shadow: 0px 3px 5px -1px rgba(0, 0, 0, 0.2),
      0px 6px 10px 0px rgba(0, 0, 0, 0.14), 0px 1px 18px 0px rgba(0, 0, 0, 0.12);
    border-radius: 25px;
    margin: 30px 0;

    img {
      width: 100%;
      max-width: 500px;
      margin-bottom: 25px;
      margin-top: 25px;
    }

    div {
      flex: 1 0 1px;
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
  background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)),
    url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 2000 1500'%3E%3Cdefs%3E%3CradialGradient id='a' gradientUnits='objectBoundingBox'%3E%3Cstop offset='0' stop-color='%23122b7d'/%3E%3Cstop offset='1' stop-color='%23195eff'/%3E%3C/radialGradient%3E%3ClinearGradient id='b' gradientUnits='userSpaceOnUse' x1='0' y1='750' x2='1550' y2='750'%3E%3Cstop offset='0' stop-color='%231645be'/%3E%3Cstop offset='1' stop-color='%23195eff'/%3E%3C/linearGradient%3E%3Cpath id='s' fill='url(%23b)' d='M1549.2 51.6c-5.4 99.1-20.2 197.6-44.2 293.6c-24.1 96-57.4 189.4-99.3 278.6c-41.9 89.2-92.4 174.1-150.3 253.3c-58 79.2-123.4 152.6-195.1 219c-71.7 66.4-149.6 125.8-232.2 177.2c-82.7 51.4-170.1 94.7-260.7 129.1c-90.6 34.4-184.4 60-279.5 76.3C192.6 1495 96.1 1502 0 1500c96.1-2.1 191.8-13.3 285.4-33.6c93.6-20.2 185-49.5 272.5-87.2c87.6-37.7 171.3-83.8 249.6-137.3c78.4-53.5 151.5-114.5 217.9-181.7c66.5-67.2 126.4-140.7 178.6-218.9c52.3-78.3 96.9-161.4 133-247.9c36.1-86.5 63.8-176.2 82.6-267.6c18.8-91.4 28.6-184.4 29.6-277.4c0.3-27.6 23.2-48.7 50.8-48.4s49.5 21.8 49.2 49.5c0 0.7 0 1.3-0.1 2L1549.2 51.6z'/%3E%3Cg id='g'%3E%3Cuse href='%23s' transform='scale(0.12) rotate(60)'/%3E%3Cuse href='%23s' transform='scale(0.2) rotate(10)'/%3E%3Cuse href='%23s' transform='scale(0.25) rotate(40)'/%3E%3Cuse href='%23s' transform='scale(0.3) rotate(-20)'/%3E%3Cuse href='%23s' transform='scale(0.4) rotate(-30)'/%3E%3Cuse href='%23s' transform='scale(0.5) rotate(20)'/%3E%3Cuse href='%23s' transform='scale(0.6) rotate(60)'/%3E%3Cuse href='%23s' transform='scale(0.7) rotate(10)'/%3E%3Cuse href='%23s' transform='scale(0.835) rotate(-40)'/%3E%3Cuse href='%23s' transform='scale(0.9) rotate(40)'/%3E%3Cuse href='%23s' transform='scale(1.05) rotate(25)'/%3E%3Cuse href='%23s' transform='scale(1.2) rotate(8)'/%3E%3Cuse href='%23s' transform='scale(1.333) rotate(-60)'/%3E%3Cuse href='%23s' transform='scale(1.45) rotate(-30)'/%3E%3Cuse href='%23s' transform='scale(1.6) rotate(10)'/%3E%3C/g%3E%3C/defs%3E%3Cg %3E%3Cg transform=''%3E%3Ccircle fill='url(%23a)' r='3000'/%3E%3Cg opacity='0.5'%3E%3Ccircle fill='url(%23a)' r='2000'/%3E%3Ccircle fill='url(%23a)' r='1800'/%3E%3Ccircle fill='url(%23a)' r='1700'/%3E%3Ccircle fill='url(%23a)' r='1651'/%3E%3Ccircle fill='url(%23a)' r='1450'/%3E%3Ccircle fill='url(%23a)' r='1250'/%3E%3Ccircle fill='url(%23a)' r='1175'/%3E%3Ccircle fill='url(%23a)' r='900'/%3E%3Ccircle fill='url(%23a)' r='750'/%3E%3Ccircle fill='url(%23a)' r='500'/%3E%3Ccircle fill='url(%23a)' r='380'/%3E%3Ccircle fill='url(%23a)' r='250'/%3E%3C/g%3E%3Cg transform=''%3E%3Cuse href='%23g' transform='rotate(10)'/%3E%3Cuse href='%23g' transform='rotate(120)'/%3E%3Cuse href='%23g' transform='rotate(240)'/%3E%3C/g%3E%3Ccircle fill-opacity='0.13' fill='url(%23a)' r='3000'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
  background-size: cover;
  /*background: #2c3e50;*/
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;


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
  padding-top: 15px;
  padding-bottom: 15px;
  background: #d3d3d3;

  span {
    margin-left: 5px;
  }
}

.pressreference {
  margin:1vw;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 5px;
  padding-bottom: 5px;
  vertical-align: middle;
  horiz-align: center;
  height: 100px;
  width: 16vw;
  max-width: 200px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  border-radius: 5px;
  object-fit: contain;
  background-color: #ffffff
}

.supporter-logo {
  margin:1%;
  vertical-align: middle;
  horiz-align: center;
  height: 80px;
  width: 20%;
  max-width: 200px;
  border-radius: 5px;
  object-fit: contain;
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
