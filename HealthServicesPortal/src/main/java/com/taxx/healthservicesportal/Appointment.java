package com.taxx.healthservicesportal;

public class Appointment {

    private int appointmentId;
    private String patientUsername;
    private String department;
    private String appointmentDate;
    private String symptoms;
    private String status;

    public Appointment(int appointmentId, String patientUsername,
            String department, String appointmentDate,
            String symptoms, String status) {

        this.appointmentId = appointmentId;
        this.patientUsername = patientUsername;
        this.department = department;
        this.appointmentDate = appointmentDate;
        this.symptoms = symptoms;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public String getDepartment() {
        return department;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getStatus() {
        return status;
    }
}