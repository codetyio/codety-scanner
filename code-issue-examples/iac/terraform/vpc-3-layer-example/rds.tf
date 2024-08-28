# #################################
# ####  database subnet group #####
# #################################
# resource "aws_db_subnet_group" "database-subnet-group" {
#   name        = "database subnets"
#   subnet_ids  = [aws_subnet.private-db-subnet-1.id, aws_subnet.private-db-subnet-2.id]
#   description = "Subnet group for database instance"
#
#   tags = {
#     Name = "Database Subnets"
#   }
# }
# #################################
# ####  database instance      #####
# #################################
# resource "aws_db_instance" "database-instance" {
#   allocated_storage      = 10
#   engine                 = "mysql"
#   engine_version         = "5.7"
#   instance_class         = var.database-instance-class
#   db_name                = "sqldb"
#   username               = "joesql"
#   password               = "qwertypoiu12"
#   parameter_group_name   = "default.mysql5.7"
#   skip_final_snapshot    = true
#   availability_zone      = "us-east-1b"
#   db_subnet_group_name   = aws_db_subnet_group.database-subnet-group.name
#   multi_az               = var.multi-az-deployment
#   vpc_security_group_ids = [aws_security_group.database-security-group.id]
# }