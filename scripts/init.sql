CREATE DATABASE IF NOT EXISTS reservas_db;
CREATE DATABASE IF NOT EXISTS reservas_db_test;

GRANT ALL PRIVILEGES ON reservas_db.* TO 'asierhr'@'%';
GRANT ALL PRIVILEGES ON reservas_db_test.* TO 'asierhr'@'%';