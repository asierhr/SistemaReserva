Este Dockerfile implementa un patron de construccion en multiples etapas para crear una imagen de contenedor minima, segura y lista para produccion de una aplicacion Java Spring Boot. Utiliza Eclipse Temurin con JDK/JRE sobre Alpine Linux para conseguir una huella peque

## Características clave

**Construcción en múltiples etapas** – Separa el entorno de compilación del entorno de ejecución  
**Usuario no root** – Ejecuta la aplicación con el usuario `spring` con privilegios mínimos  
**Caché de dependencias** – Optimiza el uso de capas de Docker para compilaciones más rápidas  **Basado en Alpine** – Imagen base minimalista que reduce la superficie de ataque  
**Maven Wrapper** – Garantiza una versión consistente de Maven en todos los entornos  
**Omisión de pruebas** – La compilación para producción excluye la ejecución de pruebas


## Etapa 1: Compilacion

| Instruccion                                     | Proposito                                                                                                                                                               |
| ----------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| FROM eclipse-temurin:17-jdk-alpine AS builder   | Usa JDK 17 sobre Alpine Linux como entorno de compilacion. AS builder nombra esta etapa para referenciarla despues                                                      |
| WORKDIR /build                                  | Establece el directorio de trabajo para operaciones de compilacion                                                                                                      |
| COPY pom.xml .<br>COPY .mvn .mvn<br>COPY mvnw . | Copia primero la configuracion de Maven para aprovechar el cache de capas de Docker. Los cambios en pom.xml invalidan el cache, pero los cambios en el codigo fuente no |
| RUN chmod +x ./mvnw                             | Hace ejecutable el *wrapper* de Maven                                                                                                                                   |
| RUN ./mvnw dependency:go-offline                | Descarga previamente las dependencias antes de copiar el codigo fuente - esencial para un cache eficiente                                                               |
| COPY src ./src                                  | Copia el codigo fuente de la aplicacion despues de que las dependencias esten en cache                                                                                  |
| RUN ./mvnw clean package -DskipTests            | Construye el archivo JAR; omite las pruebas para compilaciones de produccion                                                                                            |
## Etapa 2: Entorno de ejecucion

| Instruccion                                                            | Proposito                                                                                                           |
| ---------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| FROM eclipse-temurin:17-jre-alpine                                     | Usa solo el JRE (sin JDK) para reducir el tamaño de la imagen en tiempo de ejecución (~50% más pequeño que con JDK) |
| RUN addgroup -S spring && adduser -S spring -G spring                  | Crea un usuario/grupo spring sin privilegios                                                                        |
| WORKDIR /app                                                           | Establece el directorio de trabajo en tiempo de ejecucion                                                           |
| COPY --from=builder --chown==spring:spring /build/target/*.jar app.jar | Copia solo el JAR construido en la etapa anterior, con la propiedad correcta                                        |
| USER spring:spring                                                     | Cambia al usuario no root antes de ejecutar la aplicacion(buena practica de seguridad)                              |
| EXPOSE 8080                                                            | Documenta el puerto en el que escucha la aplicacion, no lo publica, eso se hace al ejecutar el contenedor           |
| ENTRYPOINT ["java", "-jar", "app.jar"]                                 | Define el comando inmutable de inicio; permite pasar argumentos de JVM mediante sobreescritura de CMD               |
## Consideraciones de seguridad

| Practica                         | Beneficio                                                                               |
| -------------------------------- | --------------------------------------------------------------------------------------- |
| Ejecutar sin privilegios de root | Mitiga riesgos de escape del contenedor si la aplicacion es comprometida                |
| JRE en lugar de JDK              | Reduce la superficie de ataque al excluir herramientas de desarrollo                    |
| Alpine Linux                     | Huella minima de paquetes con configuraciones predeterminadas orientadas a la seguridad |
| Construccion en multiples etapas | Las herramientas y dependencias de compilacion nunca estan presentes en la imagen final |
| Aislamiento de dependencias      | Las dependencias de Maven se resuelven en una etapa aislada                             |