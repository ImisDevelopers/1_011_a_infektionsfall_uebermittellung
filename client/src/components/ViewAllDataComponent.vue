<template>
  <div>
    <div class="table-container">
      <a-table :columns="columns" :dataSource="data" :scroll="{ x: 4050 }">
        <span slot="riskAreas" slot-scope="riskAreas">
          <a-tag
            v-for="riskArea in riskAreas"
            :key="riskArea"
          >
            {{riskArea}}
          </a-tag>
        </span>
        <span slot="preIllnesses" slot-scope="preIllnesses">
          <a-tag
            v-for="preIllness in preIllnesses"
            :key="preIllness"
          >
            {{preIllness}}
          </a-tag>
        </span>
      </a-table>
    </div>

  </div>
</template>

// TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu 

<script>
const columns = [
  {
    title: 'Nachname',
    dataIndex: 'lastName',
    key: 'lastName',
    fixed: 'left',
    width: 150,
  },
  {
    title: 'Vorname',
    dataIndex: 'firstName',
    key: 'firstName',
    // width: 150,
  },
  {
    title: 'Geschlecht',
    dataIndex: 'gender',
    key: 'gender',
    width: 150,
  },
  {
    title: 'Geburtsdatum',
    dataIndex: 'birthDate',
    key: 'birthDate',
    width: 150,
  },
  {
    title: 'Straße',
    dataIndex: 'street',
    key: 'street',
    width: 150,
  },
  {
    title: 'Hausnummer',
    dataIndex: 'houseNumber',
    key: 'houseNumber',
    width: 150,
  },
  {
    title: 'PLZ',
    dataIndex: 'zip',
    key: 'zip',
    width: 150,
  },
  {
    title: 'Stadt',
    dataIndex: 'city',
    key: 'city',
    width: 150,
  },
  {
    title: 'E-Mail',
    dataIndex: 'email',
    key: 'email',
    width: 150,
  },
  {
    title: 'Telefon',
    dataIndex: 'phone',
    key: 'phone',
    width: 150,
  },
  {
    title: 'Krankenkasse',
    dataIndex: 'insuranceCompany',
    key: 'insuranceCompany',
    width: 150,
  },
  {
    title: 'Versicherungsnummer',
    dataIndex: 'insuranceMembershipNumber',
    key: 'insuranceMembershipNumber',
    width: 150,
  },
  {
    title: 'Husten',
    dataIndex: 'cough',
    key: 'cough',
    width: 150,
  },
  {
    title: 'Abgeschlagenheit',
    dataIndex: 'fatigue',
    key: 'fatigue',
    width: 150,
  },
  {
    title: 'Fieber',
    dataIndex: 'fever',
    key: 'fever',
    width: 150,
  },
  {
    title: 'Luftnot',
    dataIndex: 'shortnessOfBreath',
    key: 'shortnessOfBreath',
    width: 150,
  },
  {
    title: 'Halskratzen',
    dataIndex: 'soreThroat',
    key: 'soreThroat',
    width: 150,
  },
  {
    title: 'Schnupfen',
    dataIndex: 'cold',
    key: 'cold',
    width: 150,
  },
  {
    title: 'Gliederschmerzen',
    dataIndex: 'limbPain',
    key: 'limbPain',
    width: 150,
  },
  {
    title: 'Kopfschmerzen',
    dataIndex: 'headaches',
    key: 'headaches',
    width: 150,
  },
  {
    title: 'Durchfall',
    dataIndex: 'diarrhea',
    key: 'diarrhea',
    width: 150,
  },
  {
    title: 'Beschwerden Auftrittsgeschw.',
    dataIndex: 'symptoms',
    key: 'symptoms',
    width: 150,
  },
  {
    title: 'Risikogebiet für Coronavirus',
    dataIndex: 'riskAreas',
    key: 'riskAreas',
    scopedSlots: { customRender: 'riskAreas' },
    width: 150,
  },
  {
    title: 'Kontakt mit Corona-Patienten',
    dataIndex: 'coronaContacts',
    key: 'coronaContacts',
    width: 150,
  },
  {
    title: 'Vorerkrankungen',
    dataIndex: 'preIllnesses',
    key: 'preIllnesses',
    scopedSlots: { customRender: 'preIllnesses' },
    width: 150,
  },
  {
    title: 'Geschwächtes Immunsystem',
    dataIndex: 'weakenedImmuneSystem',
    key: 'weakenedImmuneSystem',
    width: 150,
  },
  {
    title: 'Influenza-Impfung (2019/2020)',
    dataIndex: 'fluImmunization',
    key: 'fluImmunization',
    width: 150,
  },
];

const data = [
  {
    gender: "weiblich",
    birthDate: "2020-03-01",
    lastName: "LastName",
    firstName: "FirstName",
    street: "Street",
    houseNumber: "HouseNr",
    zip: "PLZ",
    city: "City",
    email: "email@address.com",
    phone: "telefonNr",
    insuranceCompany: "insurance",
    insuranceMembershipNumber: "insuranceMembershipNr",
    cough: "true",
    fatigue: "true",
    fever: "less-38deg",
    shortnessOfBreath: "true",
    soreThroat: "false",
    cold: "true",
    limbPain: "true",
    headaches: "true",
    diarrhea: "false",
    symptoms: "slow",
    riskAreas: [
      "Southcorea",
      "China",
      "France"
    ],
    coronaContacts: "false",
    preIllnesses: [
      "cancer",
      "circulatory disorder"
    ],
    weakenedImmuneSystem: "false",
    fluImmunization: "true"
  }
];

export default {
  name: 'TestingComponent',
  props: {
    msg: String
  },
  data() {
    return {
      entities: [],
      content: "",
      columns,
      data
    }
  },
  methods: {
    create(e) {
      e.preventDefault();
      if (this.content.length > 0) {
        fetch('/exampleEntities',
                {
                  method: 'POST',
                  body: JSON.stringify({ content: this.content }),
                  headers: {
                    'Content-Type': 'application/json'
                  }
                })
        .then((response) => {
          return response.json();
        })
        .then((entity) => {
          this.entities.push(entity);
        });
        this.content = "";
      }
    }
  },
  created() {
    fetch('/exampleEntities')
    .then((response) => {
      return response.json();
    })
    .then((entities) => {
      this.entities.push(...entities);
    });
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 20px 10px;
}
span {
  margin: 10px;
}

.table-container {
  width: 100%;
  overflow-x: scroll;
  scroll-behavior: smooth;
}

</style>