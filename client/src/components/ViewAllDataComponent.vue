<template>
  <div>
    <div>
      <a-table :columns="columns" :dataSource="data">
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
  },
  {
    title: 'Vorname',
    dataIndex: 'firstName',
    key: 'firstName',
  },
  {
    title: 'Geschlecht',
    dataIndex: 'gender',
    key: 'gender',
  },
  {
    title: 'Geburtsdatum',
    dataIndex: 'birthDate',
    key: 'birthDate',
  },
  {
    title: 'Straße',
    dataIndex: 'street',
    key: 'street',
  },
  {
    title: 'Hausnummer',
    dataIndex: 'houseNumber',
    key: 'houseNumber',
  },
  {
    title: 'PLZ',
    dataIndex: 'zip',
    key: 'zip',
  },
  {
    title: 'Stadt',
    dataIndex: 'city',
    key: 'city',
  },
  {
    title: 'E-Mail',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: 'Telefon',
    dataIndex: 'phone',
    key: 'phone',
  },
  {
    title: 'Krankenkasse',
    dataIndex: 'insuranceCompany',
    key: 'insuranceCompany',
  },
  {
    title: 'Versicherungsnummer',
    dataIndex: 'insuranceMembershipNumber',
    key: 'insuranceMembershipNumber',
  },
  {
    title: 'Husten',
    dataIndex: 'cough',
    key: 'cough',
  },
  {
    title: 'Abgeschlagenheit',
    dataIndex: 'fatigue',
    key: 'fatigue',
  },
  {
    title: 'Fieber',
    dataIndex: 'fever',
    key: 'fever',
  },
  {
    title: 'Luftnot',
    dataIndex: 'shortnessOfBreath',
    key: 'shortnessOfBreath',
  },
  {
    title: 'Halskratzen',
    dataIndex: 'soreThroat',
    key: 'soreThroat',
  },
  {
    title: 'Schnupfen',
    dataIndex: 'cold',
    key: 'cold',
  },
  {
    title: 'Gliederschmerzen',
    dataIndex: 'limbPain',
    key: 'limbPain',
  },
  {
    title: 'Kopfschmerzen',
    dataIndex: 'headaches',
    key: 'headaches',
  },
  {
    title: 'Durchfall',
    dataIndex: 'diarrhea',
    key: 'diarrhea',
  },
  {
    title: 'Beschwerden Auftrittsgeschw.',
    dataIndex: 'symptoms',
    key: 'symptoms',
  },
  {
    title: 'Risikogebiet für Coronavirus',
    dataIndex: 'riskAreas',
    key: 'riskAreas',
    scopedSlots: { customRender: 'riskAreas' },
  },
  {
    title: 'Kontakt mit Corona-Patienten',
    dataIndex: 'coronaContacts',
    key: 'coronaContacts',
  },
  {
    title: 'Vorerkrankungen',
    dataIndex: 'preIllnesses',
    key: 'preIllnesses',
    scopedSlots: { customRender: 'preIllnesses' },
  },
  {
    title: 'Geschwächtes Immunsystem',
    dataIndex: 'weakenedImmuneSystem',
    key: 'weakenedImmuneSystem',
  },
  {
    title: 'Influenza-Impfung (2019/2020)',
    dataIndex: 'fluImmunization',
    key: 'fluImmunization',
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

</style>