<template>
    <div>
        <button>Export CSV</button><!-- dummy -->
        <div class="table-container">
            <a-table :columns="columns" :dataSource="data" >
            </a-table>
        </div>

    </div>
</template>

<script>
    const columns = [
        {
            title: 'PLZ',
            dataIndex: 'zip',
            key: 'zip',
            width: 10,
            sorter: (a, b) => a.zip - b.zip,
        },
        {
            title: 'Aktive Fälle (Infizierte)',
            dataIndex: 'active',
            key: 'active',
            width: 10,
            sorter: (a, b) => a.active - b.active,
        },
        {
            title: 'Verdachtsfälle',
            dataIndex: 'suspicion',
            key: 'suspicion',
            width: 10,
            sorter: (a, b) => a.suspicion - b.suspicion,
        },
        {
            title: 'Genesene Fälle',
            dataIndex: 'recovered',
            key: 'recovered',
            width: 10,
            sorter: (a, b) => a.recovered - b.recovered,
        },
        {
            title: 'Verstorbene Fälle',
            dataIndex: 'deaths',
            key: 'deaths',
            width: 10,
            sorter: (a, b) => a.deaths - b.deaths,
        },
    ];
    const data = [
        {
            zip: "12345",
            active: "320",
            suspicion: "742",
            recovered: "135",
            deaths: "12",
        },
        {
            zip: "12346",
            active: "920",
            suspicion: "342",
            recovered: "1435",
            deaths: "12",
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