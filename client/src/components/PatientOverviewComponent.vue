<template style="margin: auto">
    <div>
<!--        <a-input-search placeholder="Suche Patienten" style="width: 100%; max-width: 1020px"/>-->
        <div style="max-width: 1020px; margin: 0 auto; padding: 0 1rem">
            <a-tabs defaultActiveKey="1" v-if="patient">
                <a-tab-pane tab="Stammdaten" key="1">
                    <!-- display user data here-->
                    <div>
                        <a-row :gutter="8" >
                            <a-col :span="24" :md="12">
                                <a-card title="Allgemein" align="left" :extra="this.patient.id">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Vorname:</td><td>{{patient.firstName}}</td>
                                        </tr>
                                        <tr>
                                            <td>Nachname:</td><td>{{patient.lastName}}</td>
                                        </tr>
                                        <tr>
                                            <td>Geburtsdatum:</td><td>{{patient.dateOfBirth}}</td>
                                        </tr>
                                        <tr>
                                            <td>Geschlecht:</td><td>{{patient.gender}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                            <a-col span="24" :md="12">
                                <a-card title="Adresse" bordered="false" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Straße:</td><td>{{patient.street}}</td>
                                        </tr>
                                        <tr>
                                            <td>Hausnummer:</td><td>{{patient.houseNumber}}</td>
                                        </tr>
                                        <tr>
                                            <td>PLZ:</td><td>{{patient.zip}}</td>
                                        </tr>
                                        <tr>
                                            <td>Ort:</td><td>{{patient.city}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                            <a-col span="24">
                                <a-card title="Kontakt & Versicherung" bordered="false" align="left">
                                    <table style="border-collapse: separate; border-spacing:15px">
                                        <tr>
                                            <td>Telefonnummer:</td><td>{{patient.phoneNumber}}</td>
                                        </tr>
                                        <tr>
                                            <td>Email:</td><td><a href="">{{patient.email}}</a></td>
                                        </tr>
                                        <tr>
                                            <td>Versicherung:</td><td>{{patient.insuranceCompany}}</td>
                                        </tr>
                                        <tr>
                                            <td>V-Nr:</td><td>{{patient.insuranceMembershipNumber}}</td>
                                        </tr>
                                    </table>
                                </a-card>
                            </a-col>
                        </a-row>
                        <a-row :gutter="8" style="margin-top: 8px;">
                            <a-col span="24">
                                <a-card :title="'Status: '+ this.patient.events[].eventType" align="left">
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
                        <a-timeline style="text-align: left; margin-left: 40px" v-if="patient.events.length">
                            <!-- List all the events recorded corresponding to the patient over time -->
                            <a-timeline-item
                                    v-for="event in this.patient.events"
                                    :key="event.id"
                                    :color="timelineColor(event.eventType)"
                            >
                                {{ new Date(event.eventTimestamp).toLocaleString() }}, {{ event.eventType }}
                            </a-timeline-item>
                        </a-timeline>
                    </a-card>
                </a-tab-pane>
            </a-tabs>
        </div>
    </div>
</template>

<script>
import Api from "../api/Api";
// const uuid = "#2685896023";
// const firstName = "Max";
// const lastName = "Mustermann";
// const dateOfBirth = "21.3.1995";
// const gender = "männlich";
// const email = "max@mustermann.de";
// const phoneNumber = "+49 12354566";
// const street = "Musterstraße";
// const zip = "12345";
// const houseNumber = "1";
// const city = "Musterstadt";
// const insuranceCompany = "Musterversicherung";
// const insuranceMembershipNumber = "45687912";

export default {
    name: "PatientOverviewComponent",
    props: {
        msg: String,
    },
    data() {
        return {
            content: "",
            patient: null,
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
        timelineColor(eventType) {
            switch(eventType) {
                case 'TEST_FINISHED_POSITIVE': return 'red';
                case 'TEST_FINISHED_NEGATIVE': return 'green'
                default: return 'grey';
            }
        }
    },
    async created() {
        this.patient = await Api.getPatient(this.$route.params.id)
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
