resource "aws_vpc" "main" {
	cidr_block = "10.0.0.0/16"
    enable_dns_support   = true
    enable_dns_hostnames = true
}

resource "aws_subnet" "public1" {
    vpc_id = aws_vpc.main.id
    cidr_block = "10.0.0.0/24"
    map_public_ip_on_launch = true
    tags = {
      Name = "subnet-public1"
    }
}

resource "aws_subnet" "public2"{
	vpc_id = aws_vpc.main.id
	cidr_block = "10.0.1.0/24"
	map_public_ip_on_launch = true
    tags = {
        Name = "subnet-public2"
    }
}

resource "aws_subnet" "private1"{
	vpc_id = aws_vpc.main.id
	cidr_block = "10.0.2.0/24"
  availability_zone = "eu-west-3a"
  tags = {
    Name = "subnet-private1"
  }
}

resource "aws_subnet" "private2"{
	vpc_id = aws_vpc.main.id
	cidr_block = "10.0.3.0/24"
   availability_zone = "eu-west-3b"
  tags = {
    Name = "subnet-private2"
  }
}

resource "aws_internet_gateway" "gw" {
	vpc_id = aws_vpc.main.id
}

resource "aws_route_table" "rt-public" {
	vpc_id = aws_vpc.main.id
}

resource "aws_route_table" "rt-private" {
	vpc_id = aws_vpc.main.id
}

resource "aws_route" "internet_access" {
   route_table_id = aws_route_table.rt-public.id
   destination_cidr_block = "0.0.0.0/0"
   gateway_id = aws_internet_gateway.gw.id
}

resource "aws_route_table_association" "a1" {
   subnet_id = aws_subnet.public1.id
   route_table_id = aws_route_table.rt-public.id
}

resource "aws_route_table_association" "a2" {
   subnet_id = aws_subnet.public2.id
   route_table_id = aws_route_table.rt-public.id
}

resource "aws_route_table_association" "a3" {
   subnet_id = aws_subnet.private1.id
   route_table_id = aws_route_table.rt-private.id
}

resource "aws_route_table_association" "a4" {
   subnet_id = aws_subnet.private2.id
   route_table_id = aws_route_table.rt-private.id
}

resource "aws_network_acl" "acl_publico" {
  vpc_id = aws_vpc.main.id
  subnet_ids = [aws_subnet.public1.id, aws_subnet.public2.id]
  
  ingress {
    protocol   = "tcp"
    rule_no    = 100
    action     = "allow"
    cidr_block = "85.87.5.203/32"
    from_port  = 22
    to_port    = 22
  }

  ingress {
    protocol   = "tcp"
    rule_no    = 110
    action     = "allow"
    cidr_block = "0.0.0.0/0"
    from_port  = 80
    to_port    = 80
  }

  ingress {
    protocol = "tcp"
    rule_no = 120
    action = "allow"
    cidr_block = "10.0.2.0/24"
    from_port = 5432
    to_port = 5432
  }

  ingress {
    protocol = "tcp"
    rule_no = 130
    action = "allow"
    cidr_block = "10.0.3.0/24"
    from_port = 5432
    to_port = 5432
  }

  ingress {
    protocol   = "tcp"
    rule_no    = 140
    action     = "allow"
    cidr_block = "0.0.0.0/0"
    from_port  = 1024
    to_port    = 65535
  }

  egress {
    protocol   = "-1"
    rule_no    = 100
    action     = "allow"
    cidr_block = "0.0.0.0/0"
    from_port  = 0
    to_port    = 0
  }

  tags = {
    Name = "acl-publico"
  }
}

resource "aws_network_acl" "acl_privado" {
  vpc_id = aws_vpc.main.id
  subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id]
  
  ingress {
    protocol   = "tcp"
    rule_no    = 100
    action     = "allow"
    cidr_block = "10.0.0.0/24"
    from_port  = 5432
    to_port    = 5432
  }

  ingress {
    protocol   = "tcp"
    rule_no    = 110
    action     = "allow"
    cidr_block = "10.0.1.0/24"
    from_port  = 5432
    to_port    = 5432
  }
  
  egress {
    protocol   = "tcp"
    rule_no    = 100
    action     = "allow"
    cidr_block = "10.0.0.0/24"
    from_port  = 1024
    to_port    = 65535
  }

  egress {
    protocol   = "tcp"
    rule_no    = 110
    action     = "allow"
    cidr_block = "10.0.1.0/24"
    from_port  = 1024
    to_port    = 65535
  }

  tags = {
    Name = "acl-privado"
  }
}

resource "aws_security_group" "sg_publico" {
	vpc_id = aws_vpc.main.id

	ingress  {
		from_port = 22
		to_port = 22
		protocol = "tcp"
		cidr_blocks = ["85.87.5.203/32"]
	}

	ingress {
		from_port = 80
		to_port = 80
		protocol = "tcp"
		cidr_blocks = ["0.0.0.0/0"]
	}

	egress  {
		from_port = 0
		to_port = 0
		protocol = "-1"
		cidr_blocks = ["0.0.0.0/0"]
	}

    tags = {
      Name = "sg-publico"
    }
}

resource "aws_security_group" "sg-privado" {
    vpc_id = aws_vpc.main.id

    ingress {
        from_port = 5432
        to_port = 5432
        protocol = "tcp"
        security_groups = [aws_security_group.sg_publico.id]
    }

    egress {
        from_port = 0
        to_port = 0
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
      Name = "sg-privado"
    }
}