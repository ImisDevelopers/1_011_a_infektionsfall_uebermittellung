<template>
    <div>
        <a-input-search placeholder="Suchen" style="width: 100%; margin: 10px 0"/>
        <a-card class="table-container">
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
            <div style="display: flex; width: 100%; justify-content: flex-end; margin-bottom: 1rem;">
                <a-button type="primary">CSV exportieren</a-button>
            </div>
        </a-card>
    </div>
</template>

// TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu

<script>
    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        },
        {
            title: 'Nachname',
            dataIndex: 'lastName',
            key: 'lastName'
        },
        {
            title: 'Vorname',
            dataIndex: 'firstName',
            key: 'firstName'
        },
        {
            title: 'Geschlecht',
            dataIndex: 'gender',
            key: 'gender'
        },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status'
        },
        {
            title: 'Zeitpunkt',
            dataIndex: 'timestamp',
            key: 'timestamp'
        },
        {
            title: 'Geburtsdatum',
            dataIndex: 'birthDate',
            key: 'birthDate'
        },
        /*  {
            title: 'Straße',
            dataIndex: 'street',
            key: 'street'
          },
          {
            title: 'Hausnummer',
            dataIndex: 'houseNumber',
            key: 'houseNumber'
          },
          {
            title: 'PLZ',
            dataIndex: 'zip',
            key: 'zip',
            width: 150,
          },*/
        {
            title: 'Stadt',
            dataIndex: 'city',
            key: 'city'
        },
        {
            title: 'E-Mail',
            dataIndex: 'email',
            key: 'email'
        },
        {
            title: 'Telefon',
            dataIndex: 'phone',
            key: 'phone'
        },
        /*  {
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
          },*/
    ];

    const data = [
        {
            id: "2837917348",
            gender: "weiblich",
            birthDate: "15.4.1982",
            status: "Testergebnis ausstehend",
            timestamp: "22.3.2020 11:20",
            lastName: "Musterfrau",
            firstName: "Marie",
            city: "Musterstadt",
            email: "marie.musterfrau@gmail.com",
            phone: "0151 123456789"
        },
        {
            id: "2837916548",
            status: "Verdachtsfall",
            timestamp: "20.3.2020 09:20",
            birthDate: "05.11.1985",
            phone: "0154 78082517",
            "lastName": "Klein",
            "firstName": "Kai",
            "gender": "m\u00e4nnlich",
            "dateOfBirth": "1927-4-5",
            "email": "K@gmail.de",
            "phoneNumber": "55510674222",
            "street": "Nordstra\u00dfe",
            "houseNumber": 43,
            "zip": "6053824",
            "city": "Bochum",
            "insuranceCompany": "IKK Nord",
            "insuranceMembershipNumber": "B676434522",
            "fluImmunization": "Nein",
            "speedOfSymptomsOutbreak": "Langsam",
            "symptoms": ["Schnupfen", "Kopfschmerzen", "Fieber"],
            "coronaContacts": "Ja",
            "riskAreas": ["GrandEst"],
            "weakenedImmuneSystem": false,
            "preIllnesses": ["cancer"]
        },
        {
            id: "2837917350",
            status: "Verdachtsfall",
            timestamp: "20.3.2020 10:20",
            birthDate: "05.10.1987",
            phone: "0154 78082517",
            "lastName": "Bauer",
            "firstName": "Annika",
            "gender": "weiblich",
            "dateOfBirth": "1945-09-24 04",
            "email": "A@posteo.de",
            "phoneNumber": "702109108075",
            "street": "Haidth\u00f6heAarweg",
            "houseNumber": 31,
            "zip": "47701080",
            "city": "M\u00fcnchen",
            "insuranceCompany": "Techniker Krankenkasse",
            "insuranceMembershipNumber": "G594080200",
            "fluImmunization": false,
            "speedOfSymptomsOutbreak": "Mittel",
            "symptoms": ["Husten", "Halschmerzen", "Kopfschmerzen", "Schnupfen", "Erk\u00e4ltung", "Atemschwierigkeiten"],
            "coronaContacts": true,
            "riskAreas": ["Hubei"],
            "weakenedImmuneSystem": false,
            "preIllnesses": [""]
        },
        {
            id: "2837917350",
            status: "Positiv getestet",
            timestamp: "20.3.2020 10:15",
            birthDate: "17.6.1990",
            phone: "040 880066",
            "lastName": "Schr\u00f6der",
            "firstName": "Marie",
            "gender": "weiblich",
            "dateOfBirth": "1961-07-29 11",
            "email": "M@gmail.de",
            "phoneNumber": "0265321169",
            "street": "Niekampsweg",
            "houseNumber": 43,
            "zip": "46103402",
            "city": "K\u00f6ln",
            "insuranceCompany": "Techniker Krankenkasse",
            "insuranceMembershipNumber": "M636452605",
            "fluImmunization": false,
            "speedOfSymptomsOutbreak": "Schnell",
            "symptoms": ["Halschmerzen", "Fieber", "Erk\u00e4ltung"],
            "coronaContacts": true,
            "riskAreas": ["New York"],
            "weakenedImmuneSystem": false,
            "preIllnesses": ["circulatory disorder"]
        },
        {
            id: "2837917350",
            status: "Genesen",
            timestamp: "20.3.2020 10:15",
            birthDate: "17.6.1955",
            phone: "011 880066",
            "lastName": "Winter",
            "firstName": "Kai",
            "gender": "m\u00e4nnlich",
            "dateOfBirth": "1941-02-20 05",
            "email": "K@t-online.de",
            "phoneNumber": "3466899914",
            "street": "Nordtstra\u00dfe",
            "houseNumber": 28,
            "zip": "1523439",
            "city": "D\u00fcsseldorf",
            "insuranceCompany": "Barmer",
            "insuranceMembershipNumber": "K462880630",
            "fluImmunization": true,
            "speedOfSymptomsOutbreak": "Langsam",
            "symptoms": [],
            "coronaContacts": false,
            "riskAreas": ["Madrid"],
            "weakenedImmuneSystem": false,
            "preIllnesses": ["cancer"]
        },
        {
          id: "1024142341",
          status: "Genesen",
          timestamp: "18.3.2020 10:50",
          birthDate: "17.3.1980",
          phone: "011 41241",
          "lastName": "Winkler", 
          "firstName": "Karl", 
          "gender": "m\u00e4nnlich", 
          "dateOfBirth": "1981-06-29 07", 
          "email": "K@gmx.de", 
          "phoneNumber": "797701058010", 
          "street": "Haidth\u00f6heAarweg", 
          "houseNumber": 90, 
          "zip": "26410040", 
          "city": "Bochum", 
          "insuranceCompany": "AOK", 
          "insuranceMembershipNumber": "Y541473732", 
          "fluImmunization": false, 
          "speedOfSymptomsOutbreak": "Langsam", 
          "symptoms": ["Fieber", "Halschmerzen", "Erk\u00e4ltung", "Schnupfen", "Husten", "Gelenkschmerzen", "Kopfschmerzen", "Atemschwierigkeiten"], 
          "coronaContacts": false, 
          "riskAreas": ["Hubei"], 
          "weakenedImmuneSystem": true, 
          "preIllnesses": ["cancer"]
        },
        {
          id: "5124142341",
          status: "Verdachtsfall",
          timestamp: "21.3.2020 12:50",
          birthDate: "17.6.1970",
          phone: "551 12412",
          "lastName":"Wagner",
          "firstName":"Susanne",
          "gender":"weiblich",
          "dateOfBirth":"1932-09-07 12",
          "email":"S@t-online.de",
          "phoneNumber":"0669898850",
          "street":"Nordufer",
          "houseNumber":84,
          "zip":"82510744",
          "city":"Kiel",
          "insuranceCompany":"IKK Nord",
          "insuranceMembershipNumber":"M644638868",
          "fluImmunization":true,
          "speedOfSymptomsOutbreak":"Langsam",
          "symptoms":[
              "Husten",
              "Halschmerzen",
              "Gelenkschmerzen"
          ],
          "coronaContacts":true,
          "riskAreas":[
              "GrandEst"
          ],
          "weakenedImmuneSystem":true,
          "preIllnesses":[
              "diabetes"
          ]
        },
        {
          id: "1241241201",
          status: "Negativ getestet",
          timestamp: "15.3.2020 14:50",
          birthDate: "12.9.1984",
          phone: "123 01241",
          "lastName":"Schulz",
          "firstName":"Daniela",
          "gender":"weiblich",
          "dateOfBirth":"1992-03-12 14",
          "email":"D@web.de",
          "phoneNumber":"8009156938",
          "street":"B\u00fcckerheide",
          "houseNumber":54,
          "zip":"10642805",
          "city":"Berlin",
          "insuranceCompany":"Techniker Krankenkasse",
          "insuranceMembershipNumber":"A725638005",
          "fluImmunization":true,
          "speedOfSymptomsOutbreak":"Langsam",
          "symptoms":[
              "Gelenkschmerzen",
              "Erk\u00e4ltung",
              "Fieber"
          ],
          "coronaContacts":true,
          "riskAreas":[
              "Madrid"
          ],
          "weakenedImmuneSystem":true,
          "preIllnesses":[
              "cancer"
          ]
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
                            body: JSON.stringify({content: this.content}),
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
        /* overflow-x: scroll;
        scroll-behavior: smooth; */
    }

</style>
