FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY *.jar ReportesMS.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
RUN addgroup -S spring && adduser -S appuser -G spring
USER appuser
EXPOSE 8082
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar ReportesMS.jar" ]
