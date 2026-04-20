provider "aws" {
	region = "eu-west-3"
}

resource "aws_instance" "web1" {
	ami = "ami-0d8c303e6b2e300dd"
	instance_type = "t3.micro"

	subnet_id = aws_subnet.public1.id
	vpc_security_group_ids = [aws_security_group.sg_publico.id]
	key_name = "key-pair1"
	user_data = file("${path.module}/userData.sh")
	iam_instance_profile = aws_iam_instance_profile.backend_profile.name
	depends_on = [aws_db_instance.rds]
	tags = {
		Name = "mi-ec2-1"
	}
}

resource "aws_instance" "web2" {
	ami = "ami-0d8c303e6b2e300dd"
	instance_type = "t3.micro"

	subnet_id = aws_subnet.public2.id
	vpc_security_group_ids = [aws_security_group.sg_publico.id]
	key_name = "key-pair1"
	user_data = file("${path.module}/userData.sh")
	iam_instance_profile = aws_iam_instance_profile.backend_profile.name
	depends_on = [aws_db_instance.rds]
	
	tags = {
		Name = "mi-ec2-2"
	}
}