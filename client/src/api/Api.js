const METHOD = {
  GET: "GET",
  POST: "POST",
  PUT: "PUT",
  DELETE: "DELETE"
};

class Api {
  constructor() {
    if (
      location.host.includes("localhost") ||
      location.host.includes("127.0.0.1")
    ) {
      this.BASE_URL = "http://localhost:8080";
    } else {
      this.BASE_URL = "https://api.imis-prototyp.de";
    }
  }

  executeRequest(url, method, body) {
    const options = {
      method: method,
      headers: {
        "Content-Type": "application/json"
      }
    };

    if (body) {
      options.body = JSON.stringify(body);
    }

    return fetch(encodeURI(`${this.BASE_URL}/${url}`), options).then(
      response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error(response);
          throw new Error(`An error occured: Status ${response.status}`);
        }
      }
    );
  }

  /*
   * ----------------------------------
   */

  postDoctorCreateAppointment({ doctorId, laboratoryId, patientId }) {
    return this.executeRequest("/doctor/create_appointment", METHOD.POST, {
      doctorId,
      laboratoryId,
      patientId
    });
  }

  getTestReports(testId) {
    return this.executeRequest(`/test_report/${testId}`, METHOD.GET);
  }

  // TODO: postTestReport, putTestReport | how to handle multipart/form-data in fetch ?!

  getTestReport(testId) {
    return this.executeRequest(`/test_report/${testId}`, METHOD.GET);
  }

  deleteTestReport(testId) {
    return this.executeRequest(`/test_report/${testId}`, METHOD.DELETE);
  }

  postInstitution(institution) {
    return this.executeRequest("/institutions", METHOD.POST, institution);
  }

  postLabtest(labtest) {
    return this.executeRequest("/labtest", METHOD.POST, labtest);
  }

  putLabtest({ updatedTestStatus }) {
    return this.executeRequest("/labtest", METHOD.PUT, updatedTestStatus);
  }

  getLabtestByPatient(patientId) {
    return this.executeRequest(`/labtest/patient/${patientId}`, METHOD.GET);
  }

  getPatients() {
    return this.executeRequest("/patients", METHOD.GET);
  }

  postPatient(patient) {
    return this.executeRequest("/patients", METHOD.POST, patient);
  }

  getPatient(id) {
    return this.executeRequest(`/patients/${id}`, METHOD.GET);
  }

  getStats(lowerBoundsZip, upperBoundsZip) {
    return this.executeRequest(
      `/stats?lowerBoundsZip=${lowerBoundsZip}&upperBoundsZips=${upperBoundsZip}`,
      METHOD.GET
    );
  }
}

export default new Api();
