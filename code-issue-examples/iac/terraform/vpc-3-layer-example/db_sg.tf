# ###################################
# ## SG  Database Tier         ###
# ###################################
#
# resource "aws_security_group" "database-security-group" {
#   name        = "Database server Security Group"
#   description = "Enable MYSQL access on port 3306"
#   vpc_id      = aws_vpc.vpc_prod.id
#
#   ingress {
#     description     = "MYSQL access"
#     from_port       = 3306
#     to_port         = 3306
#     protocol        = "tcp"
#     security_groups = ["${aws_security_group.webserver-security-group.id}"]
#   }
#
#   egress {
#     from_port   = 0
#     to_port     = 0
#     protocol    = "-1"
#     cidr_blocks = ["0.0.0.0/0"]
#   }
#
#   tags = {
#     Name = "Database Security group"
#   }
# }