FROM maven:3.6-jdk-13

# Define a work directory
WORKDIR /skyline-service

# Add all files to work directory
ADD . /skyline-service

# Update libraries
RUN yum update -y

# -B = Run in non-interactive (batch)
# -T = Thread count = 4
RUN mvn -B -T 4 package -DskipTests

# Expose port for rest interface
EXPOSE 8090

# Provide entry-point
CMD ["/bin/bash", "./bin/entry-point.sh"]