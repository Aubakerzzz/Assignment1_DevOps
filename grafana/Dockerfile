# Use the official Grafana image as a base
FROM grafana/grafana:10.0.2

# Set working directory
WORKDIR /etc/grafana

# Copy provisioning files (datasources & dashboards)
COPY grafana/provisioning/ /etc/grafana/provisioning/

# Expose Grafana's default port
EXPOSE 3000

# Set environment variables for Grafana admin user
ENV GF_SECURITY_ADMIN_USER=admin
ENV GF_SECURITY_ADMIN_PASSWORD=grafana

# Start Grafana
CMD ["/run.sh"]
