################
##### VPC  #####
################


resource "aws_vpc" "vpc_prod" {
  cidr_block           = var.vpc_cidr
  instance_tenancy     = "default"
  enable_dns_hostnames = true

  tags = {
    Name = "blabla-vpc"
  }
}















