package com.taxx.healthservicesportal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AppointmentDAO {

    private String url = "jdbc:mysql://localhost:3306/health_portal_db";
    private String dbUser = "root";
    private String dbPassword = "";

    public void createAppointment(Appointment appointment) throws Exception {

        String sql = "INSERT INTO appointments "
                + "(patient_username, department, appointment_date, symptoms, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, appointment.getPatientUsername());
            ps.setString(2, appointment.getDepartment());
            ps.setTimestamp(3, Timestamp.valueOf(
                    appointment.getAppointmentDate().replace("T", " ") + ":00"));
            ps.setString(4, appointment.getSymptoms());
            ps.setString(5, "PENDING");

            ps.executeUpdate();
        }
    }
    
    public ArrayList<Appointment> getAppointmentsByPatient(String username) throws Exception {

        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_username = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getString("patient_username"),
                            rs.getString("department"),
                            rs.getString("appointment_date"),
                            rs.getString("symptoms"),
                            rs.getString("status")
                    );
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }
    
    public ArrayList<Appointment> getAllAppointments() throws Exception {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getString("patient_username"),
                        rs.getString("department"),
                        rs.getString("appointment_date"),
                        rs.getString("symptoms"),
                        rs.getString("status")
                );
                appointments.add(appointment);
            }
        }
        return appointments;
    }
    
    public void updateStatus(int appointmentId, String status) throws Exception {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        }
    }
}