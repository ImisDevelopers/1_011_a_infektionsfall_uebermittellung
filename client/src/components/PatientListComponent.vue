<template>
    <div>
        <a-card class="table-container">
            <a-form class="search-container" :model="form">
                <a-form-item label="Vorname">
                    <a-input v-model="form.firstName" placeholder="Vorname">
                        <a-icon slot="prefix" type="user"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Nachname">
                    <a-input v-model="form.lastName" placeholder="Nachname">
                        <a-icon slot="prefix" type="user"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Status">
                    <a-select style="width: 250px" placeholder="Status" v-model="form.patientStatus">
                        <a-select-option value="">Alle</a-select-option>
                        <a-select-option v-for="eventType in eventTypes" :key="eventType.id">
                            <a-icon :type="eventType.icon" style="margin-right: 5px"/>
                            {{eventType.label}}
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="ID">
                    <a-input v-model="form.id" placeholder="ID">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
                <a-button @click="toggleAdvancedSearch">
                    <a-icon :type="showAdvancedSearch ? 'down' : 'right'"/>
                    Erweiterte Suche
                </a-button>
                <a-button icon="search" type="primary" html-type="submit"
                          @click="handleSearch">
                    Suche
                </a-button>
            </a-form>
            <a-form class="search-container" :model="advancedForm" v-if="showAdvancedSearch">
                <a-form-item label="Geschlecht">
                    <a-select v-model="form.gender" style="width: 120px" placeholder="Geschlecht">
                        <a-select-option value="">Alle</a-select-option>
                        <a-select-option value="weiblich">
                            <a-icon type="woman" style="margin-right: 5px"/>
                            Weiblich
                        </a-select-option>
                        <a-select-option value="männlich">
                            <a-icon type="man" style="margin-right: 5px"/>
                            Männlich
                        </a-select-option>
                        <a-select-option value="sonstiges">Sonstiges</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="Stadt">
                    <a-input v-model="advancedForm.city" placeholder="Stadt">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="E-Mail">
                    <a-input v-model="advancedForm.email" placeholder="E-Mail">
                        <a-icon slot="prefix" type="mail"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Telefonnummer">
                    <a-input v-model="advancedForm.phoneNumber" placeholder="Telefonnummer">
                        <a-icon slot="prefix" type="phone"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Straße">
                    <a-input v-model="advancedForm.street" placeholder="Straße">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Hausnummer" style="width: 100px">
                    <a-input v-model="advancedForm.houseNumber" placeholder="Hausnr.">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Postleitzahl" style="width: 100px">
                    <a-input v-model="advancedForm.zip" placeholder="PLZ">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Versicherung">
                    <a-input v-model="advancedForm.insuranceCompany" placeholder="Versicherung">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Versicherungsnummer">
                    <a-input v-model="advancedForm.insuranceMembershipNumber" placeholder="Versicherungsnummer">
                        <a-icon slot="prefix" type="idcard"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Arzt-ID">
                    <a-input v-model="advancedForm.doctorId" placeholder="Arzt-ID">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Labor-ID">
                    <a-input v-model="advancedForm.laboratoryId" placeholder="Labor-ID">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
            </a-form>
            <a-table :columns="columns" :dataSource="data" :scroll="{x: 1, y: 0}" :pagination="false"
                     @change="handleTableChange">
                <div slot="patientStatus" slot-scope="patientStatus">
                    <a-icon :type="eventTypes.find(type => type.id === patientStatus).icon" style="margin-right: 5px"/>
                    {{eventTypes.find(type => type.id === patientStatus).label}}
                </div>
            </a-table>
            <div style="display: flex; width: 100%; margin: 15px 0; justify-content: flex-end; align-items: center">
                <a-button type="primary" style="margin-right: 50px" @click="downloadPatients">CSV exportieren</a-button>
                <span style="margin-right: 10px">{{count}} Patienten</span>
                <a-pagination
                        showSizeChanger
                        :pageSize.sync="form.pageSize"
                        @showSizeChange="onShowSizeChange"
                        :total="count"
                        v-model="currentPage"
                />
            </div>
        </a-card>
    </div>
</template>

// TestStationen Ordnen ProbeIds und durchzuführrende Tests den Patienten zu

<script>
import Api from "../api/Api";
import { eventTypes } from "../util/event-types";
import { downloadCsv } from "../util/export-service";
import moment from "moment";

const columns = [
    {
        title: "Nachname",
        dataIndex: "lastName",
        key: "lastName",
        sorter: true,
        defaultSortOrder: "ascend"
    },
    {
        title: "Vorname",
        dataIndex: "firstName",
        key: "firstName",
        sorter: true
    },
    {
        title: "Geschlecht",
        dataIndex: "gender",
        key: "gender",
        sorter: true
    },
    {
        title: "Status",
        dataIndex: "patientStatus",
        key: "patientStatus",
        scopedSlots: {customRender: 'patientStatus'},
        sorter: true,
    },
    {
        title: "Stadt",
        dataIndex: "city",
        key: "city",
        sorter: true
    },
    {
        title: "E-Mail",
        dataIndex: "email",
        key: "email",
        sorter: true
    },
    {
        title: "ID",
        dataIndex: "id",
        key: "id",
        sorter: true
    },
];

export default {
    name: "ViewAllDataComponent",
    props: {
        msg: String
    },
    data() {
        return {
            form: {
                firstName: "",
                lastName: "",
                patientStatus: "",
                id: "",
                order: "asc",
                orderBy: "lastName",
                offsetPage: 0,
                pageSize: 10,
            },
            advancedForm: {
                gender: "",
                city: "",
                email: "",
                phoneNumber: "",
                street: "",
                houseNumber: "",
                zip: "",
                insuranceCompany: "",
                insuranceMembershipNumber: "",
                doctorId: "",
                laboratoryId: "",
            },
            content: "",
            count: 0,
            currentPage: 1, // Starts at 1
            columns,
            data: [], // data
            showAdvancedSearch: false,
            eventTypes: eventTypes,
        };
    },
    watch: {
        currentPage() {
            this.loadPage();
        },
    },
    created() {
        this.loadPage();
    },
    methods: {
        handleSearch() {
            this.currentPage = 1;
            this.loadPage();
        },
        onShowSizeChange(current, pageSize) {
            this.currentPage = current;
            this.form.pageSize = pageSize;
            this.loadPage();
        },
        loadPage() {
            this.form.offsetPage = this.currentPage - 1;
            let formValues = {...this.form};
            if (this.showAdvancedSearch) {
                formValues = {...formValues, ...this.advancedForm}
            }
            if (!formValues.patientStatus) {
                // Backend fails on empty string
                formValues.patientStatus = null;
            }
            Api.countPatients(formValues).then(count => {
                this.count = count;
            });
            Api.queryPatients(formValues).then(result => {
                this.data = result;
            }).catch(error => {
                console.error(error);
                const notification = {
                    message: "Fehler beim Laden der Patientendaten.",
                    description: error.message
                };
                this.$notification["error"](notification);
            });
        },
        toggleAdvancedSearch() {
            this.showAdvancedSearch = !this.showAdvancedSearch;
        },
        downloadPatients() {
            let formValues = {...this.form};
            if (this.showAdvancedSearch) {
                formValues = {...formValues, ...this.advancedForm}
            }
            if (!formValues.patientStatus) {
                // Backend fails on empty string
                formValues.patientStatus = null;
            }
            Api.countPatients(formValues).then(count => {
                // Download all data that applies to the current filter
                formValues.offsetPage = 0;
                formValues.pageSize = count;
                Api.queryPatients(formValues).then(result => {
                    const header = "ID,Vorname,Nachname,Geschlecht,Status,Geburtsdatum,Stadt,E-Mail;Telefonnummer;" +
                        "Straße;Hausnummer;Stadt;Versicherung;Versichertennummer;";
                    const patients = result.map((patient) =>
                        `${patient.id};${patient.firstName};${patient.lastName};${patient.gender};${patient.patientStatus};`
                        + `${patient.dateOfBirth};${patient.city};${patient.email};${patient.phoneNumber};${patient.street};`
                        + `${patient.houseNumber};${patient.city};${patient.insuranceCompany};${patient.insuranceMembershipNumber}`
                    ).join("\n");
                    const filename = moment().format("YYYY_MM_DD") + "_patienten_export.csv";
                    downloadCsv(header + "\n" + patients, filename);
                }).catch(error => {
                    console.error(error);
                    const notification = {
                        message: "Fehler beim Laden der Patientendaten.",
                        description: error.message
                    };
                    this.$notification["error"](notification);
                });
            });

        },
        handleTableChange(pagination, filters, sorter) {
            const sortKey = sorter.field ? sorter.field : "lastName";
            let sortOrder = "asc";
            if (sorter.order === "descend") {
                sortOrder = "desc";
            }
            this.form.order = sortOrder;
            this.form.orderBy = sortKey;
            this.loadPage();
        },
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
    margin: 20px 10px;
}

.table-container {
    width: 100%;
}

.search-container {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 15px;
    align-items: flex-end;
}

.search-container > * {
    margin: 0 10px;
    max-width: 250px;
}

.search-container > button {
    margin-bottom: 5px;
    margin-top: 5px;
}
</style>
