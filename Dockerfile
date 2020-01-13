FROM circleci/openjdk:13.0-buster

WORKDIR /skyline-service

ADD . /skyline-service

USER root

RUN apt-get update

# add microsoft fonts
RUN mvn clean test

# provide entry-point
CMD ["/bin/sh"]