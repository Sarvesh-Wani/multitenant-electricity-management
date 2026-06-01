CREATE TABLE employee(
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    state VARCHAR(100),
    district VARCHAR(100),
    city VARCHAR(100),
    user_id BIGINT NOT NULL
);