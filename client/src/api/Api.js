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
   * -----------IMPLEMENTED IN UI-----------
   */

  postInstitution(request) {
    return this.executeRequest("/institutions", METHOD.POST, request);
  }

  postPatient(request) {
    return this.executeRequest("/patients", METHOD.POST, request);
  }

  postLabTest(request) {
    return this.executeRequest("/labtests", METHOD.POST, request);
  }

  putLabTest(testId, request) {
    return this.executeRequest(`/labtests/${testId}`, METHOD.PUT, request);
  }

  /*
   * ---------NOT IMPLEMENTED IN UI---------
   */

  postDoctorCreateAppointment({ doctorId, laboratoryId, patientId }) {
    return this.executeRequest("/doctor/create_appointment", METHOD.POST, {
      doctorId,
      laboratoryId,
      patientId
    });
  }

  getLabTestByPatient(patientId) {
    return this.executeRequest(`/labtest/patient/${patientId}`, METHOD.GET);
  }

  getPatients() {
    return this.executeRequest("/patients", METHOD.GET);
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
