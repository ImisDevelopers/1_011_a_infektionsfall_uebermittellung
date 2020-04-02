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
                <a-form-item label="Stadt">
                    <a-input v-model="form.city" placeholder="Stadt">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="ID">
                    <a-input v-model="form.id" placeholder="ID">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
                <a-button icon="search" type="primary" style="margin-bottom: 5px" html-type="submit"
                          @click="handleSearch">
                    Suche
                </a-button>
            </a-form>
            <a-button @click="toggleAdvancedSearch" style="margin-bottom: 15px">
                <a-icon :type="showAdvancedSearch ? 'down' : 'right'"/>
                Erweiterte Suche
            </a-button>
            <!--            gender: "",-->
            <!--            city: "",-->
            <!--            email: "",-->
            <!--            phoneNumber: "",-->
            <!--            street: "",-->
            <!--            houseNumber: "",-->
            <!--            zip: "",-->
            <!--            insuranceCompany: "",-->
            <!--            insuranceMembershipNumber: "",-->
            <!--            doctorId: "",-->
            <!--            laboratoryId: "",-->
            <a-form class="search-container" :model="advancedForm" v-if="showAdvancedSearch">
                <a-form-item label="Geschlecht">
                    <a-select v-model="form.gender" style="width: 120px" placeholder="Geschlecht">
                        <a-select-option value="">Alle</a-select-option>
                        <a-select-option value="weiblich">Weiblich</a-select-option>
                        <a-select-option value="männlich">Männlich</a-select-option>
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
                        <a-icon slot="prefix" type="street"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Hausnummer">
                    <a-input v-model="advancedForm.houseNumber" placeholder="Hausnummer">
                        <a-icon slot="prefix" type="houseNumber"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Postleitzahl">
                    <a-input v-model="advancedForm.zip" placeholder="Postleitzahl">
                        <a-icon slot="prefix" type="zip"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Versicherung">
                    <a-input v-model="advancedForm.insuranceCompany" placeholder="Versicherung">
                        <a-icon slot="prefix" type="insurance"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="Versicherungsnummer">
                    <a-input v-model="advancedForm.insuranceMembershipNumber" placeholder="Versicherungsnummer">
                        <a-icon slot="prefix" type="insuranceNumber"/>
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
            <a-table :columns="columns" :dataSource="data" :scroll="{x: 1, y: 0}" :pagination="false"></a-table>
            <div
                    style="display: flex; width: 100%; margin: 15px 0; justify-content: flex-end"
            >
                <a-button type="primary" style="margin-right: 50px">CSV exportieren</a-button>
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

const eventTypes = [
    {
        id: "REGISTERED",
        label: "Registriert",
        icon: "login"
    }, {
        id: "SUSPECTED",
        label: "Verdachtsfall",
        icon: "search"
    }, {
        id: "SCHEDULED_FOR_TESTING",
        label: "Wartet auf Test",
        icon: "team"
    }, {
        id: "TEST_SUBMITTED_IN_PROGRESS",
        label: "Test eingereicht",
        icon: "clock-circle"
    }, {
        id: "TEST_FINISHED_POSITIVE",
        label: "Test positiv",
        icon: "check"
    }, {
        id: "TEST_FINISHED_NEGATIVE",
        label: "Test negativ",
        icon: "stop"
    }, {
        id: "TEST_FINISHED_INVALID",
        label: "Test invalide",
        icon: "warning"
    }, {
        id: "TEST_FINISHED_RECOVERED",
        label: "Getestet und erholt",
        icon: "rollback"
    }, {
        id: "TEST_FINISHED_NOT_RECOVERED",
        label: "Getestet und nicht erholt",
        icon: "logout"
    }, {
        id: "PATIENT_DEAD",
        label: "Verstorben",
        icon: "cloud"
    }, {
        id: "DOCTORS_VISIT",
        label: "Arztbesuch",
        icon: "reconciliation"
    },
];

const columns = [
    {
        title: "Nachname",
        sorter: (a, b) => a.lastName.localeCompare(b.lastName),
        dataIndex: "lastName",
        key: "lastName"
    },
    {
        title: "Vorname",
        sorter: (a, b) => a.firstName.localeCompare(b.firstName),
        dataIndex: "firstName",
        key: "firstName"
    },
    {
        title: "Geschlecht",
        dataIndex: "gender",
        key: "gender"
    },
    {
        title: "Status",
        sorter: (a, b) => a.status.localeCompare(b.status),
        dataIndex: "status",
        key: "status"
    },
    {
        title: "Stadt",
        dataIndex: "city",
        key: "city"
    },
    {
        title: "E-Mail",
        dataIndex: "email",
        key: "email"
    },
    {
        title: "ID",
        dataIndex: "id",
        key: "id",
        defaultSortOrder: "ascend"
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
                pageSize: 4,
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
            eventTypes,
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
            console.log(`Loading ${formValues.pageSize} patients of page ${formValues.offsetPage}`);
            Api.countPatients(formValues).then(count => {
                this.count = count;
            });
            Api.queryPatients(formValues).then(result => {
                this.data = result;
            });
        },
        toggleAdvancedSearch() {
            this.showAdvancedSearch = !this.showAdvancedSearch;
        }
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
</style>
