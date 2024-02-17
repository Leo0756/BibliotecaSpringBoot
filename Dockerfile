FROM openjdk:21
# Crear un volumen /tmp
VOLUME /tmp
# Agregamos el archivo JAR de tu aplicación al contenedor
ADD out/BibliotecaSpringBoot.jar app.jar
# Ejecutamos un comando para establecer la marca de tiempo del archivo JAR
RUN sh -c 'touch /app.jar'
# Exponemos el puerto 8080 en el contenedor
EXPOSE 8080
# Establecemos el comando de entrada para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
