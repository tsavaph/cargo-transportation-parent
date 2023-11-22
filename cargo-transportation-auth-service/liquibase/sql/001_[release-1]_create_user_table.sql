--liquibase formatted sql

--changeset tsavaph:create-user-table
CREATE SEQUENCE IF NOT EXISTS user_table_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE user_table
(
    id BIGINT PRIMARY KEY DEFAULT nextval('user_table_id_seq'),
    phone_number VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
COMMENT ON TABLE user_table IS 'Таблица пользователей';
COMMENT ON COLUMN user_table.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN user_table.phone_number IS 'Номер телефона/логин пользователя';
COMMENT ON COLUMN user_table.password IS 'Зашифрованный пароль пользователя';
COMMENT ON COLUMN user_table.role IS 'Роль пользователя';
-- rollback DROP TABLE user_table; DROP SEQUENCE user_table_id_seq;

--changeset tsavaph:create-user-table-phone-number-password-index
CREATE INDEX idx_user_table_phone_number_password ON user_table(phone_number, password);
--rollback DROP INDEX idx_user_table_phone_number_password;
