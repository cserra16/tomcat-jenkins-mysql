# Aplicación de Ejemplo CI/CD

Esta es una aplicación Java de ejemplo para validar el funcionamiento del entorno Docker con Tomcat y MariaDB.

## 📦 Estructura del Proyecto

```
exemple/
├── pom.xml                           # Configuración Maven
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       └── TestServlet.java  # Servlet de prueba
│       └── webapp/
│           ├── index.html            # Página principal
│           └── WEB-INF/
```

## 🔨 Compilar el Proyecto

Desde el directorio `exemple`:

```bash
mvn clean package
```

Esto generará el archivo WAR en `target/cicd-test.war`

## 🚀 Desplegar en Tomcat

### Opción 1: Copiar manualmente
```bash
docker cp target/cicd-test.war tomcat:/usr/local/tomcat/webapps/
```

### Opción 2: Desplegar desde Jenkins
1. Configurar un Job Maven en Jenkins
2. Apuntar al repositorio Git
3. Goal de Maven: `clean package`
4. Post-build: Deploy to Tomcat container

## 🧪 Probar la Aplicación

Una vez desplegada, acceder a:
- **Página principal**: http://localhost:8888/cicd-test/
- **Página de pruebas**: http://localhost:8888/cicd-test/test

## ✅ Funcionalidades

El servlet de prueba realiza las siguientes validaciones:

1. **Test de Tomcat**: Verifica que el servidor está funcionando
2. **Conexión a Base de Datos**: Conecta con MariaDB
3. **Operaciones CRUD**: Crea una tabla de prueba e inserta datos
4. **Consultas**: Lee y muestra datos de la base de datos

## 🔧 Configuración de Base de Datos

La aplicación se conecta a:
- **Host**: `mariadb` (nombre del contenedor)
- **Puerto**: 3306
- **Base de datos**: `cicd_db`
- **Usuario**: `cicd_user`
- **Contraseña**: `cicd_password`

## 📝 Notas para Estudiantes

- Este es un ejemplo básico de aplicación Java EE
- Utiliza Servlets para manejar peticiones HTTP
- Demuestra la conexión JDBC a MariaDB
- Sirve como base para proyectos más complejos
- Puede ser extendido con JSP, Spring, etc.

## 🐛 Solución de Problemas

### Error de conexión a la base de datos
- Verificar que el contenedor MariaDB está corriendo: `docker-compose ps`
- Verificar las credenciales en el servlet

### WAR no se despliega
- Revisar logs de Tomcat: `docker-compose logs tomcat`
- Verificar que el WAR se copió correctamente

### Compilación falla
- Verificar que Maven está instalado
- Ejecutar `mvn clean` antes de compilar
