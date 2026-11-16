USE health_portal_db;

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('stu_ahmed', '$2a$10$qmFv0LZvX42sEEZErV9nzOXyrBx0LH/c86UxkI/825Ynlel6L4sUa', 'Ahmed Ali', 'PATIENT'),
('stu_reem', '$2a$10$pRtgwIbHnIcgtDoZVoTXx.8XgHDfyvxs4AWxpST1P6SGmchXtpEOq', 'Reem Mohammed', 'PATIENT'),
('stu_karam', '$2a$10$7TYDKtu48AFs8c5ptxIAxOl4tiNp6O6fcvqXfAbOalZ4J7MEG6YJi', 'Karam Abbas', 'PATIENT'),
('stu_farah', '$2a$10$klxtAcIfTqCRe9rVWRyOheK4jemmX94Co3BnqeYgWNZlTXH7ekmUK', 'Farah Jamal', 'PATIENT'),
('dr_omar', '$2a$10$pPlIns1KppsKvHh4v6VTreP7JEVZT/uZde1lMfi8dWtQvue75aVhq', 'Dr. Omar Hassan', 'STAFF'),
('dr_mariam', '$2a$10$wgPRUkBtf8J62iWAffnbd.G7WQqkYZantYp6eqXGs5WuGl/FcSsoe', 'Dr. Mariam Khalid', 'STAFF');

INSERT INTO appointments (patient_username, department, appointment_date, symptoms, status)
VALUES
('stu_ahmed', 'General Medicine', '2026-11-20 10:00:00', 'Headache and fever', 'PENDING'),
('stu_reem', 'Dentistry', '2026-11-22 12:30:00', 'Tooth pain', 'CONFIRMED'),
('stu_farah', 'General Medicine', '2026-11-21 09:00:00', 'Stomach pain', 'COMPLETED');