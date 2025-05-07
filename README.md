# 📍 GeolocationAPI – Guía rápida de ejecución

## ✅ Requisitos previos

Antes de ejecutar el proyecto, asegurate de tener instalado lo siguiente en tu máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)  
  *(En versiones modernas, ya viene incluido con Docker Desktop)*

También es necesario tener conexión a internet para descargar las imágenes si es la primera vez que se ejecuta.

---

## 🚀 Levantar el contenedor

Para construir y levantar la aplicación utilizando Docker, ejecutá el siguiente comando en la raíz del proyecto:

```bash
docker-compose up --build
```

## 🧹 Eliminar volúmenes generados
Si querés detener los servicios y eliminar también los volúmenes creados (por ejemplo, datos persistentes de la base de datos), usá el siguiente comando:

```bash
docker-compose down --volumes
```
