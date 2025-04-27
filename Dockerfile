FROM openjdk:17

WORKDIR /app

# Copy the Maven wrapper and pom.xml first for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Now copy your source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Expose the port your app runs on
EXPOSE 8080

# Run the jar file with corrected filename
CMD ["java", "-jar", "target/blog-0.0.1-SNAPSHOT.jar"]