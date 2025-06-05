# 🛰️ Spring Boot & Kafka

Este proyecto demuestra cómo integrar **Apache Kafka** con **Spring Boot**, utilizando **Docker** para simular un entorno distribuido con múltiples brokers y un Zookeeper.

## ⚙️ Tecnologías utilizadas

- Spring Boot
- Apache Kafka
- Docker & Docker Compose
- JUnit y Mockito (pruebas unitarias e integración)

## 🚀 Descripción

El proyecto expone una serie de **APIs REST** que permiten:

- Enviar mensajes a un tópico de Kafka (Productor)
- Escuchar y procesar mensajes desde el tópico (Consumidor)
- Simular escenarios reales de comunicación asíncrona con Kafka

Además, incluye pruebas automatizadas para validar el comportamiento del sistema en los distintos puntos de integración.

## 🐳 Entorno Docker

El entorno se levanta con:

- 🐘 **3 Brokers de Kafka**
- 🧠 **1 Zookeeper** como coordinador

```bash
docker-compose up --build
```

Una vez levantado, los servicios Kafka estarán listos para aceptar conexiones desde la aplicación Spring Boot.

## 📮 API Endpoints

Ejemplos de endpoints implementados:

| Método | Ruta           | Descripción                        |
|--------|----------------|------------------------------------|
| POST   | `/kafka/send`  | Envía un mensaje a un tópico Kafka|
| GET    | `/kafka/listen`| Verifica el estado del consumidor |

## 🧪 Pruebas

El proyecto contiene:

- Pruebas unitarias de lógica de negocio
- Pruebas de integración que simulan la comunicación vía Kafka

---

## 👨‍💻 Autor
[![LinkedIn](https://img.shields.io/badge/LinkedIn-André%20Llumiquinga-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/andre-llc/)  
[![GitHub](https://img.shields.io/badge/GitHub-André%20Llumiquinga-black?style=flat&logo=github)](https://github.com/andrefernandoec2608)
