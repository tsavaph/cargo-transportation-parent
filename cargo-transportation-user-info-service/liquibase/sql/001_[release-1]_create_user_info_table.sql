--liquibase formatted sql

--changeset tsavaph:create-user-info-table
CREATE SEQUENCE IF NOT EXISTS user_info_table_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE user_info_table
(
    id BIGINT PRIMARY KEY DEFAULT nextval('user_info_table_id_seq'),
    login VARCHAR(12),
    name VARCHAR(255),
    email VARCHAR(255)
);
COMMENT ON TABLE user_info_table IS 'Таблица с информацией о пользователе';
COMMENT ON COLUMN user_info_table.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN user_info_table.login IS 'Логин пользователя';
COMMENT ON COLUMN user_info_table.name IS 'Имя пользователя';
COMMENT ON COLUMN user_info_table.email IS 'Почта пользователя';
-- rollback DROP TABLE user_info_table; DROP SEQUENCE user_info_table_id_seq;

--changeset tsavaph:create-user-info-login-index
CREATE INDEX idx_user_info_table_login ON user_info_table(login);
--rollback DROP INDEX idx_user_info_table_login;
