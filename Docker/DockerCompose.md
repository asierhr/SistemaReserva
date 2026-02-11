# Docker Compose
Este archivo docker-compose.yaml define la infraestructura necesaria para ejecutar un sistema de reservas basado en Spring Boot, utilizando los siguientes servicios:
* Aplicacion (Spring Boot)
* MySQL (Base de datos)
*  Redis (Cache y coordinacion)
* Kafka (Mensajeria)
* Zookeeper(Coordinacion de Kafka)
-------------------------------------------------------
## Servicios definidos
-----------------------------------------------------------------------
### Servicio app
<pre>app :
	build: . 
	image:sistemareserva
</pre>
+ Construye la imagen Docker desde el Dockerfile en el directorio actual
+ Asigna el nombre sistemareserva a la imagen
<pre> ports:
      - "8080:8080"
</pre>
+ Expone el puerto 8080 del contenedor al puerto 8080 del host

##### Variables de entorno
Configura las propiedades necesarias para la aplicacion
+ MAIL_USERNAME, MAIL_PASSWORD → Configuracion de correo
+ SPRING_DATASOURCE_* → Configuracion a MySQL
+ SPRING_DATA_REDIS_* → Configuracion de Redis
+ SPRING_KAFKA_BOOTSTRAP_SERVERS → Servidor de Kafka

Ejemplo importante:
<pre> SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/reservas_db </pre>
Aqui se usa el nombre del servicio mysql como hostname interno de Docker
##### Dependencias
<pre>
depends_on:
      mysql:
        condition: service_healthy
</pre>
+ Esperamos a que MySQL este saludable
+ Redis y Kafka deben estar iniciados antes de arrancar la aplicacion

##### Reinicio automatico
<pre> restart: on-failure </pre>
+ Reinicia el contenedor si falla

---------------------------------------------------
### Servicio zookeeper

<pre>
zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
</pre>
+ Necesario para gestionar Kafka
+ Expone el puerto 2181
------------------------------------------
### Servicio Kafka
<pre>
kafka:
    image: confluentinc/cp-kafka:7.5.0
</pre>
##### Dependencia
<pre>
depends_on:
      - zookeeper
</pre>
Depende de zookeeper
##### Puerto
<pre>- "9092:9092"</pre>
##### Configuracion clave
+ KAFKA_ZOOKEEPER_CONNECT → Conexion a Zookeper
+ KAFKA_ADVERTISED_LISTENERS → Direccion acesible desde otros contenedores
+ KAFKA_AUTO_CREATE_TOPICS_ENABLE→ crea topics automaticamente
+  KAFKA_DELETE_TOPIC_ENABLE → Permite eliminar topics

--------------
### Servicio Redis
<pre>image: redis:7-alpine</pre>
+ Base de datos en memoria usada como cache
+ Expone el puerto 6379
##### Volumen
<pre>
volumes:
      - redis-data:/data
</pre>
Permite la persistencia de datos

-------
### Servicio MySQL
<pre>
image: mysql:8.0
container_name: mysql-reservas
ports:
      - "3307:3306"
</pre>
+ El puerto 3306 del contenedor se expone como 3307 en el host
+ Basicamente para evitar conflictos si tienes MySQL instalado
##### Variables de entorno
+ MYSQL_ROOT_PASSWORD
+ MYSQL_DATABASE
+ MYSQL_USER
+ MYSQL_PASSWORD
##### Volumenes
<pre>
volumes:
      - mysql-data:/var/lib/mysql
      - ./mysql-dumps:/docker-entrypoint-initdb.d
</pre>
+ Persistencia de datos
+ Inicializacion automatica de scripts SQL al arrancar
##### Healthcheck
<pre>
healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-uasiderhr", "-pasierhr"]
</pre>
Verifica que MySQL este listo antes de permitir que la aplicacion arranque

------------------------
## Volumenes
<pre>
volumes:
  redis-data:
  mysql-data:
</pre>
Permiten persistencia de datos aunque los contenedores se eliminen

------------------------------------------
## Como levantar el entorno
<pre>
docker-compose up --build
</pre>
-----------------
