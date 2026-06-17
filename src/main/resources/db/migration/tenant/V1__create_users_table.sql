create type user_role as enum(
    'BPO', 'OPERATION_HEAD', 'SALES_TEAM', 'Manager-1','Manager-2'
);

CREATE TABLE employees
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY primary key,
    name       varchar(255),
    email      varchar(255),
    password   varchar(255),
    role       user_role Not null,
    created_at TIMESTAMPTZ default NOW()
);