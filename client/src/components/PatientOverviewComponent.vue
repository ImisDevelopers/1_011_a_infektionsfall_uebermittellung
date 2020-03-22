<template style="margin: auto">
    <div>
        <a-input-search placeholder="Suche Patienten" style="width: 200px"/>
        <a-card style="max-width: 1020px; margin: auto">
            <a-tabs defaultActiveKey="1">
                <a-tab-pane tab="Stammdaten" key="1">
                    <!-- display user data here-->
                    <div>
                        <row gutter="16">
                            <a-col span="8">
                                <a-card title="Allgemein" bordered="false" align="left" :extra="uuid">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Vorname:</td><td>{{firstName}}</td>
                                        </tr>
                                        <tr>
                                            <td>Nachname:</td><td>{{lastName}}</td>
                                        </tr>
                                        <tr>
                                            <td>Geburtsdatum:</td><td>{{dateOfBirth}}</td>
                                        </tr>
                                        <tr>
                                            <td>Geschlecht:</td><td>{{gender}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                            <a-col span="8">
                                <a-card title="Adresse" bordered="false" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Straße:</td><td>{{street}}</td>
                                        </tr>
                                        <tr>
                                            <td>Hausnummer:</td><td>{{houseNumber}}</td>
                                        </tr>
                                        <tr>
                                            <td>PLZ:</td><td>{{zip}}</td>
                                        </tr>
                                        <tr>
                                            <td>Ort:</td><td>{{city}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                            <a-col span="8">
                                <a-card title="Kontakt" bordered="false" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Telefonnummer:</td><td>{{phoneNumber}}</td>
                                        </tr>
                                        <tr>
                                            <td>Email:</td><td>{{email}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                        </row>
                        <row gutter="16">
                            <a-col span="24">
                                <a-card title="Status: Verdachtsfall" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>COVID-19-Test:</td><td>Ausstehend</td> <td>Testdatum:</td><td>-</td>
                                        </tr>
                                        <tr>
                                            <td>Erstaufnahme:</td><td>2020-02-22</td>
                                        </tr>
                                        <tr>
                                            <td><a-button>Test erneut anordnen</a-button></td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                        </row>
                    </div>
                    <br>
                    <br>
                    <!--<a-table :columns="columns" :dataSource="data"> </a-table>-->
                </a-tab-pane>
                <a-tab-pane tab="Untersuchungen" key="2" forceRender>
                    <a-timeline>
                        <!-- List all the events recorded corresponding to the patient over time -->
                        <a-timeline-item>2020-03-01 Stammdaten Angelegt</a-timeline-item>
                        <a-timeline-item>2020-03-01 Patient als Risikokontakt zur Erstanamnese vorstellig geworden</a-timeline-item>
                        <a-timeline-item>2020-03-01 Coronavirus Test: ID-12yt2109t9023810293</a-timeline-item>
                        <a-timeline-item
                                color="green"
                        >2020-03-02 Befund unauffällig (Test: ID-12yt2109t9023810293)</a-timeline-item>
                        <a-timeline-item>2020-03-08 Patient aufgrund von akuten Beschwerden erneut vorstellig geworden</a-timeline-item>
                        <a-timeline-item>2020-03-08 Coronavirus Test: ID-581a-bqwlkab9031499</a-timeline-item>
                        <a-timeline-item color="red">2020-03-09 Befund positiv (Test: ID-581a-bqwlkab9031499)</a-timeline-item>
                        <a-timeline-item>2020-03-09 Gesundheitamt Neustadt verhängt Quarantäne</a-timeline-item>
                        <a-timeline-item>2020-03-17 Patient zur Coronakontrolluntersuchung vorstellig geworden</a-timeline-item>
                        <a-timeline-item
                                slot="dot"
                                type="clock-circle-o"
                                style="font-size: 16px;"
                        >2020-03-17 Coronavirus Test: ID-581a-bqwlkab9031499</a-timeline-item>
                    </a-timeline>
                </a-tab-pane>
            </a-tabs>
        </a-card>
    </div>
</template>

<script>

    const uuid = "#2685896";
    const firstName = "Max";
    const lastName = "Mustermann";
    const dateOfBirth = "1995-03-21";
    const gender = "männlich";
    const email = "max@mustermann.de";
    const phoneNumber = "+49 12354566";
    const street = "Musterstraße";
    const zip = "12345";
    const houseNumber = "1";
    const city = "Musterstadt";

    export default {
        name: "TestingComponent",
        props: {
            msg: String
        },
        data() {
            return {
                entities: [],
                content: "",
                firstName,
                lastName,
                dateOfBirth,
                gender,
                email,
                phoneNumber,
                street,
                zip,
                houseNumber,
                city,
                uuid
            };
        },
        methods: {},
        components: {

        }
    };
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
