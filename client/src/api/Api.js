class Api {
  constructor() {
    if (
      location.host.includes("localhost") ||
      location.host.includes("127.0.0.1")
    ) {
      this.BASE_URL = "https://api.imis-prototyp.de";
    } else {
      this.BASE_URL = "https://api.imis-prototyp.de";
    }
  }

  getCall(url) {
    return fetch(`${this.BASE_URL}${encodeURI(url)}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => {
      return response.json();
    });
  }

  postCall(url, body) {
    return fetch(`${this.BASE_URL}${encodeURI(url)}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(response => {
      return response.json();
    });
  }

  putCall(url, body) {
    return fetch(`${this.BASE_URL}${encodeURI(url)}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(response => {
      return response.json();
    });
  }

  deleteCall(url) {
    return fetch(`${this.BASE_URL}${encodeURI(url)}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => {
      return response.json();
    });
  }

  /*
   * ----------------------------------
   */

  postDoctorCreateAppointment({ doctorId, laboratoryId, patientId }) {
    return this.postCall("/doctor/create_appointment", {
      doctorId,
      laboratoryId,
      patientId
    });
  }

  getTestReports(testId) {
    return this.getCall(`/test_report/${testId}`);
  }

  postTestReport(id, file) {

    const data = new FormData()
    data.append('file', file)


    return fetch(`${this.BASE_URL}/test_reports/${encodeURI(id)}`, {
          method: "POST",          
          body: data
        }).then(response => {
          return response.json();
        });
  }

  getTestReport(testId) {
    return this.getCall(`/test_reports/${testId}`);
  }

  deleteTestReport(testId) {
    return this.deleteCall(`/test_reports/${testId}`);
  }

  postInstitution(institution) {
    return this.postCall("/institutions", institution);
  }

  postLabtest(labtest) {
    return this.postCall("/labtest", labtest);
  }

  putLabtest({ updatedTestStatus }) {
    return this.postCall("/labtest", { updatedTestStatus });
  }

  getLabtestByPatient(patientId) {
    return this.getCall(`/labtest/patient/${patientId}`);
  }

  getPatients() {
    return this.getCall("/patients");
  }

  postPatient(patient) {
    return this.postCall("/patients", patient);
  }

  getPatient(id) {
    return this.getCall(`/patients/${id}`);
  }

  getStats(lowerBoundsZip, upperBoundsZip) {
    return this.getCall(
      `/stats?lowerBoundsZip=${lowerBoundsZip}&upperBoundsZips=${upperBoundsZip}`
    );
  }
}

export default new Api();
