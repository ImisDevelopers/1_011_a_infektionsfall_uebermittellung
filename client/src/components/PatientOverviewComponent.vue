<template style="margin: auto">
    <div>
        <a-input-search placeholder="Suche Patienten" style="width: 100%; max-width: 1020px"/>
        <div style="max-width: 1020px; margin: auto">
            <a-tabs defaultActiveKey="1">
                <a-tab-pane tab="Stammdaten" key="1">
                    <!-- display user data here-->
                    <div>
                        <a-row :gutter="8">
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
                                <a-card title="Kontakt & Versicherung" bordered="false" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Telefonnummer:</td><td>{{phoneNumber}}</td>
                                        </tr>
                                        <tr>
                                            <td>Email:</td><td><a href="">{{email}}</a></td>
                                        </tr>
                                        <tr>
                                            <td>Versicherung:</td><td>{{insuranceCompany}}</td>
                                        </tr>
                                        <tr>
                                            <td>V-Nr:</td><td>{{insuranceMembershipNumber}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                        </a-row>
                        <a-row :gutter="8" style="margin-top: 8px;">
                            <a-col span="24">
                                <a-card title="Status: Genesen" align="left">
                                    <a-row :gutter="8" style="margin-top: 8px;">
                                        <a-col span="6">
                                            Erstaufnahme:
                                        </a-col>
                                        <a-col span="6">
                                            10.3.2020
                                        </a-col>
                                        <a-col span="6">
                                            Testdatum:
                                        </a-col>
                                        <a-col span="6">
                                            22.3.2020
                                        </a-col>
                                    </a-row>
                                    <a-divider />
                                    <a-row type="flex" justify="end" :gutter="8" style="margin-top: 8px;">
                                        <a-col>
                                            <a-button type="primary" @click="requestTestAgain('success')">Test erneut anordnen</a-button>
                                        </a-col>
                                    </a-row>
                                </a-card>
                            </a-col>
                        </a-row>
                    </div>
                    <br>
                    <br>
                    <!--<a-table :columns="columns" :dataSource="data"> </a-table>-->
                </a-tab-pane>
                <a-tab-pane tab="Verlauf" key="2" forceRender>
                    <a-card>
                        <a-timeline style="text-align: left; margin-left: 40px">
                            <!-- List all the events recorded corresponding to the patient over time -->
                            <a-timeline-item>10.03.2020 Patient aufgenommen, Test angeordnet</a-timeline-item>
                            <a-timeline-item>11.03.2020 Coronavirus Test: ID-12yt2109t9023810293</a-timeline-item>
                            <a-timeline-item color="red">11.03.2020 Befund positiv (ID-12yt2109t9023810293)</a-timeline-item>
                            <a-timeline-item>11.03.2020 Gesundheitamt Neustadt verhängt Quarantäne</a-timeline-item>
                            <a-timeline-item>15.03.2020 Erneute Testanordnung nach Quarantäne</a-timeline-item>
                            <a-timeline-item>15.03.2020 Coronavirus Test: ID-12yt2109t1523810293</a-timeline-item>
                            <a-timeline-item color="green">16.03.2020 Befund unauffällig (ID-12yt2109t1523810293)</a-timeline-item>
                            <a-timeline-item>21.03.2020 Erneute Testanordnung nach Quarantäne</a-timeline-item>
                            <a-timeline-item>21.03.2020 Coronavirus Test: ID-12yt2109t152ab10293</a-timeline-item>
                            <a-timeline-item color="green">22.03.2020 Befund unauffällig (ID-12yt2109t152ab10293)</a-timeline-item>
                            <a-timeline-item color="green">22.03.2020 Patient genesen</a-timeline-item>
                        </a-timeline>
                    </a-card>
                </a-tab-pane>
            </a-tabs>
        </div>
    </div>
</template>

<script>

    const uuid = "#2685896023";
    const firstName = "Max";
    const lastName = "Mustermann";
    const dateOfBirth = "21.3.1995";
    const gender = "männlich";
    const email = "max@mustermann.de";
    const phoneNumber = "+49 12354566";
    const street = "Musterstraße";
    const zip = "12345";
    const houseNumber = "1";
    const city = "Musterstadt";
    const insuranceCompany = "Musterversicherung";
    const insuranceMembershipNumber = "45687912";

    export default {
        name: "PatientOverviewComponent",
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
                uuid,
                insuranceCompany,
                insuranceMembershipNumber
            };
        },
        methods: {
            requestTestAgain(type) {
                // const patID = "12389384"

                // Check notification type (success, info, warning, error)
                if (type === "success") {
                var notification = {
                    message: 'Der Test wurde erneut angefordert.',
                    // description:
                    // `Patienten ID: ${patID}`,
                }
                }

                // Show notification
                this.$notification[type](notification);
            },
        },
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
