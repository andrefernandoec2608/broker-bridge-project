# 🧩 Kafka Messaging System with Spring Boot & Docker

Este repositorio agrupa dos aplicaciones independientes —**Producer** y **Consumer**— desarrolladas en **Spring Boot** que interactúan entre sí mediante **Apache Kafka**, utilizando **Docker** para levantar el entorno de mensajería distribuido.

## 📦 Contenido del proyecto

```
kafka-messaging-system/
├── producer-service/      # Servicio encargado de enviar mensajes a Kafka
├── consumer-service/      # Servicio encargado de consumir y procesar los mensajes
└── README.md              # Este archivo
```

## 🚀 Descripción general

Este proyecto demuestra la implementación de un sistema de mensajería asíncrona basado en **Kafka**, utilizando buenas prácticas en arquitectura backend. Ambos servicios (Producer y Consumer) están desacoplados, lo que permite escalabilidad, modularidad y pruebas independientes.

- 🧪 **Pruebas unitarias y de integración** incluidas en ambos servicios
- 🐳 **Entorno simulado con Docker**, que incluye Zookeeper y múltiples brokers Kafka
- 🌐 **APIs REST** expuestas en ambos proyectos para facilitar el envío y monitoreo de mensajes
- 🛠️ **Arquitectura limpia** basada en Spring Boot

## 🐳 Levantar el entorno

Cada subproyecto incluye su propio archivo `docker-compose.yml` o puede conectarse a un entorno Kafka centralizado. Asegúrate de tener Docker instalado y ejecutar:

```bash
docker-compose up --build
```

Desde el proyecto de tu elección.

## 🧭 Cómo usar este repositorio

1. Ingresa al directorio del servicio `producer-service/` o `consumer-service/`
2. Sigue las instrucciones en el README de cada uno (cada uno incluye sus endpoints y lógica)
3. Puedes correrlos de forma independiente o conjunta para probar la interacción vía Kafka

---

## 👨‍💻 Autor  
[![LinkedIn](https://img.shields.io/badge/LinkedIn-André%20Llumiquinga-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/andre-llc/)  
[![GitHub](https://img.shields.io/badge/GitHub-André%20Llumiquinga-black?style=flat&logo=github)](https://github.com/andrefernandoec2608)