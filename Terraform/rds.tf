resource "aws_db_instance" "rds" {
    allocated_storage = 10
    db_name = "mydb"
    engine = "mysql"
    instance_class = "db.t3.micro"
    username = "asierhr"
    password = "asier5573"
    vpc_security_group_ids = [aws_security_group.sg-privado.id]
    db_subnet_group_name = aws_db_subnet_group.subnet-group.name
    skip_final_snapshot  = true
}

resource "aws_db_subnet_group" "subnet-group" {
    name = "main"
    subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id]

    tags = {
      Name = "subnet-group"
    }
}