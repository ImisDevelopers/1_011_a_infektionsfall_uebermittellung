class Api {  

    constructor() {
        if (location.host.includes("localhost") || location.host.includes("127.0.0.1")) {
            this.BASE_URL = ""
        } else [
            this.BASE_URL = "https://api.imis-prototyp.de"
        ]
    }

    getCall(url) {
        return fetch(encodeURI(`${this.BASE_URL}/${url}`), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        })
    }


    postCall(url, body) {
        return fetch(encodeURI(`${this.BASE_URL}/${url}`), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then((response) => {
            return response.json();
        })
    }

    putCall(url, body) {
        return fetch(encodeURI(`${this.BASE_URL}/${url}`), {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then((response) => {
            return response.json();
        })
    }

    deleteCall(url) {
        return fetch(encodeURI(`${this.BASE_URL}/${url}`), {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then((response) => {
            return response.json();
        })
    }


    /*
     * ----------------------------------
     */

    postDoctorCreateAppointment({doctorId, laboratoryId, patientId}) {
        return this.postCall('/doctor/create_appointment', {
            doctorId,
            laboratoryId,
            patientId
        })
    }

    getTestReports(testId) {
        return this.getCall(`/test_report/${testId}`)
    }


    // TODO: postTestReport, putTestReport | how to handle multipart/form-data in fetch ?!


    getTestReport(testId) {
        return this.getCall(`/test_report/${testId}`)
    }

    deleteTestReport(testId) {
        return this.deleteCall(`/test_report/${testId}`)
    }

    postInstitution(institution) {
        return this.postCall('/institutions', institution)
    }

    postLabtest(labtest) {
        return this.postCall('/labtest', labtest)
    } 

    putLabtest({updatedTestStatus}) {
        return this.postCall('/labtest', updatedTestStatus)
    } 

    getLabtestByPatient(patientId) {
        return this.getCall(`/labtest/patient/${patientId}`)
    } 
    
    getPatients() {
        return this.getCall('/patients')
    }

    postPatient(patient) {
        return this.postCall('/patients', patient)
    }

    getPatient(id) {
        return this.getCall(`/patients/${id}`)
    }

    getStats(lowerBoundsZip, upperBoundsZip) {
        return this.getCall(`/stats?lowerBoundsZip=${lowerBoundsZip}&upperBoundsZips=${upperBoundsZip}`)
    }

}

export default new Api()
