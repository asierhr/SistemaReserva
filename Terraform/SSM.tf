resource "aws_ssm_parameter" "db_endpoint" {
  name = "/config/SistemaReservas/spring.datasource.url"
  type = "String"
  value = "jdbc:mysql://${aws_db_instance.rds.address}/my_db"
}

resource "aws_ssm_parameter" "db_user" {
    name = "/config/SistemaReservas/spring.datasource.username"
    type = "String"
    value = aws_db_instance.rds.username
}

resource "aws_ssm_parameter" "db_password"{
    name = "/config/SistemaReservas/spring.datasource.password"
    type = "SecureString"
    value = aws_db_instance.rds.password
}
