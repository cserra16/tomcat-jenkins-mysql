# Aplicació d'Exemple CI/CD

Aquesta és una aplicació Java d'exemple per validar el funcionament de l'entorn Docker amb Tomcat i MariaDB.

## 📦 Estructura del Projecte

```
exemple/
├── pom.xml                           # Configuració Maven
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       └── TestServlet.java  # Servlet de prova
│       └── webapp/
│           ├── index.html            # Pàgina principal
│           └── WEB-INF/
```

## 🔨 Compilar el Projecte

Des del directori `exemple`:

```bash
mvn clean package
```

Això generarà el fitxer WAR a `target/cicd-test.war`

## 🚀 Desplegar a Tomcat

### Opció 1: Copiar manualment
```bash
docker cp target/cicd-test.war tomcat:/usr/local/tomcat/webapps/
```

### Opció 2: Desplegar des de Jenkins
1. Configurar un Job Maven a Jenkins
2. Apuntar al repositori Git
3. Goal de Maven: `clean package`
4. Post-build: Deploy to Tomcat container

## 🧪 Provar l'Aplicació

Un cop desplegada, accedir a:
- **Pàgina principal**: http://localhost:8888/cicd-test/
- **Pàgina de proves**: http://localhost:8888/cicd-test/test

## ✅ Funcionalitats

El servlet de prova realitza les següents validacions:

1. **Test de Tomcat**: Verifica que el servidor està funcionant
2. **Connexió a Base de Dades**: Connecta amb MariaDB
3. **Operacions CRUD**: Crea una taula de prova i insereix dades
4. **Consultes**: Llegeix i mostra dades de la base de dades

## 🔧 Configuració de Base de Dades

L'aplicació es connecta a:
- **Host**: `mariadb` (nom del contenidor)
- **Port**: 3306
- **Base de dades**: `cicd_db`
- **Usuari**: `cicd_user`
- **Contrasenya**: `cicd_password`

## 📝 Notes per a Estudiants

- Aquest és un exemple bàsic d'aplicació Java EE
- Utilitza Servlets per gestionar peticions HTTP
- Demostra la connexió JDBC a MariaDB
- Serveix com a base per a projectes més complexos
- Pot ser estès amb JSP, Spring, etc.

## 🐛 Solució de Problemes

### Error de connexió a la base de dades
- Verificar que el contenidor MariaDB està corrent: `docker-compose ps`
- Verificar les credencials al servlet

### WAR no es desplega
- Revisar logs de Tomcat: `docker-compose logs tomcat`
- Verificar que el WAR es va copiar correctament

### Compilació falla
- Verificar que Maven està instal·lat
- Executar `mvn clean` abans de compilar
