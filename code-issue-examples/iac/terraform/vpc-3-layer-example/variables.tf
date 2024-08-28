####################
## Create this keypair before running.
####################
variable "ssh_key_pair_name" {
  default     = "robert_medium_jul15_2024"
  description = "SSH key pair name. "
  type        = string
}
####################
## VPC CIDR Block ##
####################
variable "vpc_cidr" {
  default     = "10.0.0.0/16"
  description = "VPC_cidr block"
  type        = string
}
##################################
## Presentation Tier CIDR Block 1 ##
##################################
variable "public-web-subnet-1-cidr" {
  default     = "10.0.1.0/24"
  description = "public_web_subnet1"
  type        = string
}
################################
## Presentation Tier CIDR Block 2##
###############################
variable "public-web-subnet-2-cidr" {
  default     = "10.0.2.0/24"
  description = "public_web_subnet2"
  type        = string
}
#########################
## App tier CIDR Block 1##
#########################
variable "private-app-subnet-1-cidr" {
  default     = "10.0.3.0/24"
  description = "private_app_subnet1"
  type        = string
}
############################
## App tier CIDR Block 2 ##
###########################
variable "private-app-subnet-2-cidr" {
  default     = "10.0.4.0/24"
  description = "private_app_subnet2"
  type        = string
}
####################
## DB CIDR Block 1 ##
####################
variable "private-db-subnet-1-cidr" {
  default     = "10.0.5.0/24"
  description = "private_db_subnet1"
  type        = string
}
####################
## DB CIDR Block 2 ##
####################
variable "private-db-subnet-2-cidr" {
  default     = "10.0.6.0/24"
  description = "private_db_subnet2"
  type        = string
}
############################
## App tier security group ##
############################
variable "ssh-locate" {
  default     = "8.8.8.8/32"
  description = "Your ip address"
  type        = string
}
####################
## DB Instance    ##
####################
variable "database-instance-class" {
  default     = "db.t2.micro"
  description = "The Database Instance type"
  type        = string
}
####################
##     multi_AZ    #
####################
variable "multi-az-deployment" {
  default     = false
  description = "Create a Standby DB Instance"
  type        = bool
}
