########################
###    Data source   ###
########################

data "aws_ami" "amazon_linux_2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "owner-alias"
    values = ["amazon"]
  }

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm*"]
  }
}
########################
###    Ec2 Instance  ###
########################


resource "aws_instance" "PublicWebTemplate" {
  ami                    = data.aws_ami.amazon_linux_2.id
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.public-web-subnet-1.id
  vpc_security_group_ids = [aws_security_group.webserver-security-group.id]
  key_name               = "source_key"
  user_data              = file("ec2-init-script.sh")

  tags = {
    Name = "web-asg"
  }
}



##############################
#### EC2 instance Web Tier ###
##############################

/*resource "aws_instance" "PublicWebTemplate" {
  ami                    = "ami-052efd3df9dad4825"
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.public-web-subnet-1.id
  vpc_security_group_ids = [aws_security_group.webserver-security-group.id]
  key_name               = "source_key"
  user_data              = file("ec2-init-script.sh")

  tags = {
    Name = "web-asg"
  }
}*/


resource "aws_instance" "private-app-template" {
  ami                    = data.aws_ami.amazon_linux_2.id
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.private-app-subnet-1.id
  vpc_security_group_ids = [aws_security_group.ssh-security-group.id]
  key_name               = "source_key"

  tags = {
    Name = "app-asg"
  }
}

##############################
#### EC2 instance App Tier ###
##############################

/*resource "aws_instance" "private-app-template" {
  ami                    = "ami-052efd3df9dad4825"
  instance_type          = "t2.micro"
  subnet_id              = aws_subnet.private-app-subnet-1.id
  vpc_security_group_ids = [aws_security_group.ssh-security-group.id]
  key_name               = "source_key"

  tags = {
    Name = "app-asg"
  }
}*/




