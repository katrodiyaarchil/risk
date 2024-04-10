# Use a base image with Maven and JDK 21
FROM maven:3.9-eclipse-temurin-21-jammy AS build

# Install necessary dependencies including Xvfb
RUN apt-get update && \
    apt-get install -y \
    xvfb \
    xauth \
    libxi6 \
    libxrender1 \
    libxtst6 \
    libgtk-3-0 \
    libxss1 \
    libasound2 \
    libcanberra-gtk-module \
    libcanberra-gtk3-module && \
    rm -rf /var/lib/apt/lists/*

# Set environment variable for Xvfb display
ENV DISPLAY=:99

# Create a directory for the app and set it as the working directory
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY . .

# Set the Java target and source versions
RUN mvn -B -e -ntp initialize -Dmaven.compiler.release=21

# Run Xvfb and Maven test suite
CMD Xvfb :99 -screen 0 1024x768x16 & \
    mvn test & mvn javadoc:javadoc