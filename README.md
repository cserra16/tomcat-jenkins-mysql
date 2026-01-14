# Entorno CI/CD con Docker para Estudiantes de Java

Este proyecto proporciona un entorno completo de CI/CD con Jenkins, Tomcat 9 y MariaDB para realizar pruebas de integración y despliegue continuo de aplicaciones Java.

## 🚀 Servicios Incluidos

- **Jenkins** (puerto 8080): Servidor de integración continua
- **Tomcat 9** (puerto 8888): Servidor de aplicaciones Java
- **MariaDB** (puerto 3306): Base de datos relacional

## 📋 Requisitos Previos

- Docker Desktop instalado ([Descargar aquí](https://www.docker.com/products/docker-desktop))
- Al menos 4GB de RAM disponible
- Puertos 8080, 8888 y 3306 libres

## 🔧 Instalación y Uso

### Iniciar los Servicios

```bash
docker-compose up -d
```

### Verificar el Estado

```bash
docker-compose ps
```

### Ver Logs

```bash
# Logs de todos los servicios
docker-compose logs

# Logs de un servicio específico
docker-compose logs jenkins
docker-compose logs tomcat
docker-compose logs mariadb
```

### Detener los Servicios

```bash
docker-compose down
```

### Detener y Eliminar Volúmenes (⚠️ Eliminará todos los datos)

```bash
docker-compose down -v
```

## 🔑 Credenciales por Defecto

### Jenkins
- **URL**: http://localhost:8080
- **Usuario inicial**: Se genera automáticamente
- **Contraseña inicial**: Obtener con el comando:
  ```bash
  docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
  ```

### Tomcat
- **URL**: http://localhost:8888
- **Manager**: Acceso no configurado por defecto (se puede configurar editando `tomcat-users.xml`)

### MariaDB
- **Host**: localhost (o `mariadb` desde otros contenedores)
- **Puerto**: 3306
- **Usuario root**: `root`
- **Contraseña root**: `root`
- **Base de datos**: `cicd_db`
- **Usuario aplicación**: `cicd_user`
- **Contraseña aplicación**: `cicd_password`

## 🎓 Configuración para Estudiantes

### Conectar desde una Aplicación Java a MariaDB

```properties
# application.properties (Spring Boot)
spring.datasource.url=jdbc:mariadb://localhost:3306/cicd_db
spring.datasource.username=cicd_user
spring.datasource.password=cicd_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

### Desplegar una Aplicación WAR en Tomcat

1. Copiar el archivo WAR al contenedor:
   ```bash
   docker cp tu-aplicacion.war tomcat:/usr/local/tomcat/webapps/
   ```

2. La aplicación estará disponible en:
   ```
   http://localhost:8888/tu-aplicacion
   ```

### Configurar Jenkins para Despliegue Automático

1. Acceder a Jenkins: http://localhost:8080
2. Instalar plugins necesarios:
   - Maven Integration
   - Deploy to container
   - Git
3. Crear un nuevo Job de tipo "Maven project"
4. Configurar el SCM (Git)
5. En "Build", configurar los goals de Maven: `clean package`
6. En "Post-build Actions", agregar "Deploy war/ear to a container":
   - WAR/EAR files: `target/*.war`
   - Context path: `/tu-app`
   - Containers: Tomcat 9.x
   - Manager URL: `http://tomcat:8080`

## 🔌 Conectar Servicios entre Contenedores

Los contenedores están en la misma red Docker (`cicd-network`), por lo que pueden comunicarse usando sus nombres de servicio:

- Desde Jenkins a Tomcat: `http://tomcat:8080`
- Desde Jenkins/Tomcat a MariaDB: `mariadb:3306`

## 📦 Volúmenes Persistentes

Los datos se almacenan en volúmenes Docker:
- `jenkins_home`: Configuración y trabajos de Jenkins
- `tomcat_webapps`: Aplicaciones desplegadas en Tomcat
- `mariadb_data`: Datos de la base de datos

## 🛠️ Comandos Útiles

### Acceder a la Consola de un Contenedor

```bash
docker exec -it jenkins bash
docker exec -it tomcat bash
docker exec -it mariadb bash
```

### Conectar a MariaDB con Cliente MySQL

```bash
docker exec -it mariadb mysql -uroot -proot
```

### Reiniciar un Servicio Específico

```bash
docker-compose restart jenkins
docker-compose restart tomcat
docker-compose restart mariadb
```

## 🐛 Solución de Problemas

### Puerto ya en uso
Si algún puerto está ocupado, editar `docker-compose.yml` y cambiar el puerto externo (primer número del mapeo).

### Jenkins no inicia
Verificar logs:
```bash
docker-compose logs jenkins
```

### No se puede conectar a MariaDB
Verificar que el contenedor esté corriendo:
```bash
docker-compose ps mariadb
```

## 📚 Recursos Adicionales

- [Documentación de Jenkins](https://www.jenkins.io/doc/)
- [Documentación de Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/)
- [Documentación de MariaDB](https://mariadb.org/documentation/)

## 📄 Licencia

Este proyecto está diseñado para fines educativos.
