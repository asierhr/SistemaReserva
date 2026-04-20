#!/bin/bash
set -x

echo "--- EMPEZANDO SCRIPT ---"

sudo yum update -y
sudo yum install -y docker git

sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

sudo mkdir -p /usr/local/lib/docker/cli-plugins/
sudo curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
sudo chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
sudo ln -sf /usr/local/lib/docker/cli-plugins/docker-compose /usr/bin/docker-compose

cd /home/ec2-user
git clone https://github.com/asierhr/SistemaReserva.git
cd SistemaReserva/Docker-Compose-AWS

sudo docker-compose up -d

echo "--- SCRIPT FINALIZADO ---"
