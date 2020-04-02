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
                <a-form-item label="Geschlecht">
                    <a-select v-model="form.gender" style="width: 120px" placeholder="Geschlecht">
                        <a-select-option value="">Alle</a-select-option>
                        <a-select-option value="weiblich">Weiblich</a-select-option>
                        <a-select-option value="männlich">Männlich</a-select-option>
                        <a-select-option value="sonstiges">Sonstiges</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="Status">
                    <a-select style="width: 250px" placeholder="Status" v-model="form.status" >
                        <a-select-option value="">Alle</a-select-option>
                        <a-select-option v-for="eventType in eventTypes" :key="eventType.id">
                            <a-icon :type="eventType.icon" style="margin-right: 5px"/>
                            {{eventType.label}}
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="Stadt">
                    <a-input v-model="form.city"  placeholder="Stadt">
                        <a-icon slot="prefix" type="home"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="E-Mail">
                    <a-input v-model="form.email" placeholder="E-Mail">
                        <a-icon slot="prefix" type="mail"/>
                    </a-input>
                </a-form-item>
                <a-form-item label="ID">
                    <a-input v-model="form.id"  placeholder="ID">
                        <a-icon slot="prefix" type="hdd"/>
                    </a-input>
                </a-form-item>
                <a-button icon="search" type="primary" style="margin-bottom: 5px" html-type="submit"
                          @click="handleSearch">
                    Suche
                </a-button>
            </a-form>
            <a-table :columns="columns" :dataSource="data" :scroll="{x: 1, y: 0}">
            </a-table>
            <div
                    style="display: flex; width: 100%; justify-content: flex-end; margin-bottom: 1rem;"
            >
                <a-button type="primary">CSV exportieren</a-button>
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
                gender: "",
                status: "",
                city: "",
                email: "",
                id: ""
            },
            content: "",
            columns,
            data: [], // data
            searchOpen: false,
            eventTypes
        };
    },
    created() {
        Api.getPatients()
            .then(patients => {
                this.data = patients.reverse()
                    .map(patient => ({
                        ...patient,
                        status: patient.events ? patient.events[patient.events.length - 1].eventType : '',
                    }));
            });
    },
    methods: {
        handleSearch() {
            console.log(this.form);
            Api.queryPatients(this.form);
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
