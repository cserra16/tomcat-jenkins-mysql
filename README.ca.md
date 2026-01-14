# Entorn CI/CD amb Docker per a Estudiants de Java

Aquest projecte proporciona un entorn complet de CI/CD amb Jenkins, Tomcat 9 i MariaDB per a realitzar proves d'integració i desplegament continu d'aplicacions Java.

## 🚀 Serveis Inclosos

- **Jenkins** (port 8080): Servidor d'integració contínua
- **Tomcat 9** (port 8888): Servidor d'aplicacions Java
- **MariaDB** (port 3306): Base de dades relacional

## 📋 Requisits Previs

- Docker Desktop instal·lat ([Descarregar aquí](https://www.docker.com/products/docker-desktop))
- Almenys 4GB de RAM disponible
- Ports 8080, 8888 i 3306 lliures

## 🔧 Instal·lació i Ús

### Iniciar els Serveis

```bash
docker-compose up -d
```

### Verificar l'Estat

```bash
docker-compose ps
```

### Veure Logs

```bash
# Logs de tots els serveis
docker-compose logs

# Logs d'un servei específic
docker-compose logs jenkins
docker-compose logs tomcat
docker-compose logs mariadb
```

### Aturar els Serveis

```bash
docker-compose down
```

### Aturar i Eliminar Volums (⚠️ Eliminarà totes les dades)

```bash
docker-compose down -v
```

## 🔑 Credencials per Defecte

### Jenkins
- **URL**: http://localhost:8080
- **Usuari inicial**: Es genera automàticament
- **Contrasenya inicial**: Obtenir amb la comanda:
  ```bash
  docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
  ```

### Tomcat
- **URL**: http://localhost:8888
- **Manager**: Accés no configurat per defecte (es pot configurar editant `tomcat-users.xml`)

### MariaDB
- **Host**: localhost (o `mariadb` des d'altres contenidors)
- **Port**: 3306
- **Usuari root**: `root`
- **Contrasenya root**: `root`
- **Base de dades**: `cicd_db`
- **Usuari aplicació**: `cicd_user`
- **Contrasenya aplicació**: `cicd_password`

## 🎓 Configuració per a Estudiants

### Connectar des d'una Aplicació Java a MariaDB

```properties
# application.properties (Spring Boot)
spring.datasource.url=jdbc:mariadb://localhost:3306/cicd_db
spring.datasource.username=cicd_user
spring.datasource.password=cicd_password
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

### Desplegar una Aplicació WAR a Tomcat

1. Copiar el fitxer WAR al contenidor:
   ```bash
   docker cp la-teva-aplicacio.war tomcat:/usr/local/tomcat/webapps/
   ```

2. L'aplicació estarà disponible a:
   ```
   http://localhost:8888/la-teva-aplicacio
   ```

### Configurar Jenkins per a Desplegament Automàtic

1. Accedir a Jenkins: http://localhost:8080
2. Instal·lar plugins necessaris:
   - Maven Integration
   - Deploy to container
   - Git
3. Crear un nou Job de tipus "Maven project"
4. Configurar el SCM (Git)
5. A "Build", configurar els goals de Maven: `clean package`
6. A "Post-build Actions", afegir "Deploy war/ear to a container":
   - WAR/EAR files: `target/*.war`
   - Context path: `/la-teva-app`
   - Containers: Tomcat 9.x
   - Manager URL: `http://tomcat:8080`

## 🔌 Connectar Serveis entre Contenidors

Els contenidors estan a la mateixa xarxa Docker (`cicd-network`), pel que poden comunicar-se usant els seus noms de servei:

- Des de Jenkins a Tomcat: `http://tomcat:8080`
- Des de Jenkins/Tomcat a MariaDB: `mariadb:3306`

## 📦 Volums Persistents

Les dades s'emmagatzemen en volums Docker:
- `jenkins_home`: Configuració i treballs de Jenkins
- `tomcat_webapps`: Aplicacions desplegades a Tomcat
- `mariadb_data`: Dades de la base de dades

## 🛠️ Comandes Útils

### Accedir a la Consola d'un Contenidor

```bash
docker exec -it jenkins bash
docker exec -it tomcat bash
docker exec -it mariadb bash
```

### Connectar a MariaDB amb Client MySQL

```bash
docker exec -it mariadb mysql -uroot -proot
```

### Reiniciar un Servei Específic

```bash
docker-compose restart jenkins
docker-compose restart tomcat
docker-compose restart mariadb
```

## 🐛 Solució de Problemes

### Port ja en ús
Si algun port està ocupat, editar `docker-compose.yml` i canviar el port extern (primer número del mapatge).

### Jenkins no inicia
Verificar logs:
```bash
docker-compose logs jenkins
```

### No es pot connectar a MariaDB
Verificar que el contenidor estigui corrent:
```bash
docker-compose ps mariadb
```

## 📚 Recursos Addicionals

- [Documentació de Jenkins](https://www.jenkins.io/doc/)
- [Documentació de Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/)
- [Documentació de MariaDB](https://mariadb.org/documentation/)

## 📄 Llicència

Aquest projecte està dissenyat per a fins educatius.
