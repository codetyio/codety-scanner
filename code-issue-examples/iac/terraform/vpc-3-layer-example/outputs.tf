#########################
### Load balancer DNS ###
#########################

output "lb_dns_name" {
  description = "DNS name of the load balancer"
  value       = aws_lb.application-load-balancer.dns_name
}