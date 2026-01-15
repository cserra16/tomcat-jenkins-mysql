#!/bin/bash
# Script para inicializar Tomcat con las aplicaciones Manager y Host-Manager

# Copiar aplicaciones manager si no existen
if [ ! -d "/usr/local/tomcat/webapps/manager" ]; then
  echo "Copiando aplicación Manager..."
  cp -R /usr/local/tomcat/webapps.dist/manager /usr/local/tomcat/webapps/
fi

if [ ! -d "/usr/local/tomcat/webapps/host-manager" ]; then
  echo "Copiando aplicación Host-Manager..."
  cp -R /usr/local/tomcat/webapps.dist/host-manager /usr/local/tomcat/webapps/
fi

# Copiar archivos de configuración si existen
if [ -f "/tmp/manager-context.xml" ]; then
  echo "Aplicando configuración de Manager..."
  cp /tmp/manager-context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
  cp /tmp/manager-context.xml /usr/local/tomcat/webapps/host-manager/META-INF/context.xml
fi

# Iniciar Tomcat
echo "Iniciando Tomcat..."
catalina.sh run
