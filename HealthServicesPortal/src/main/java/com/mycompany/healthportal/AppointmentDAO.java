package com.mycompany.healthportal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AppointmentDAO {

    // Set to true for Apache Derby, or false for MySQL (XAMPP)
    private static final boolean USE_DERBY = true;

    private static final String DERBY_URL = "jdbc:derby://localhost:1527/health_portal_db;create=true";
    private static final String DERBY_USER = "app";
    private static final String DERBY_PASSWORD = "app";

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/health_portal_db";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "";

    private Connection getConnection() throws Exception {
        if (USE_DERBY) {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(DERBY_URL, DERBY_USER, DERBY_PASSWORD);
        } else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        }
    }

    public void createAppointment(Appointment appointment) throws Exception {

        String sql = "INSERT INTO appointments "
                + "(patient_username, department, appointment_date, symptoms, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
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

        try (Connection con = getConnection();
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

        try (Connection con = getConnection();
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

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        }
    }
}