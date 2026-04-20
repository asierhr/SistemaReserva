resource "aws_iam_role" "backend_role" {
    name = "backend-role-reservas"
    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [{
            Action = "sts:AssumeRole"
            Effect = "Allow"
            Principal = { Service = "ec2.amazonaws.com" }
        }]
    })
}

resource "aws_iam_instance_profile" "backend_profile" {
    name = "backend-instance-profile"
    role = aws_iam_role.backend_role.name
}

resource "aws_iam_role_policy" "read_ssm" {
    name = "AllowReadSSM"
    role = aws_iam_role.backend_role.id
    policy = jsonencode({
        Version = "2012-10-17"
        Statement = [{
            Effect = "Allow"
            Action = ["ssm:GetParameters", "ssm:GetParameter"]
            Resource = "arn:aws:ssm:*:*:parameter/config/SistemaReservas/*"
        }]
    })
}