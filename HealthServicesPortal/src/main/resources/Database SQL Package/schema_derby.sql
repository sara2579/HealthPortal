CREATE TABLE users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50),
    password_hash VARCHAR(255),
    full_name VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE appointments (
    appointment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_username VARCHAR(50),
    department VARCHAR(100),
    appointment_date TIMESTAMP,
    symptoms VARCHAR(500),
    status VARCHAR(20)
);