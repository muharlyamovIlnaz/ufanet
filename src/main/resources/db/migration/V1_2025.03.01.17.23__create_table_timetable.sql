CREATE TABLE timetable (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    data DATE NOT NULL,
    time TIME NOT NULL,
    client_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE timetable ADD CONSTRAINT timetable_client FOREIGN KEY (client_id) REFERENCES user_info(id);