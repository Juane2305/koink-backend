# 💰 Koink - Backend

Koink es una aplicación web de **gestión de finanzas personales y familiares**, diseñada para ayudarte a controlar tus ingresos, gastos, y presupuestos de forma clara, segura y profesional.

Este repositorio contiene el backend desarrollado en **Java 17 + Spring Boot 3.4.4**, utilizando autenticación JWT, arquitectura limpia basada en dominios, y preparado para internacionalización y multimoneda.

---

## 🚀 Funcionalidades actuales

### 🔐 Autenticación
- Registro con email y contraseña
- Login seguro con JWT
- Protección de rutas privadas
- Endpoint `/user/me` para obtener perfil autenticado
- Ocultamiento de datos sensibles (`password`, `provider`)

### 💸 Transacciones
- CRUD completo de ingresos y egresos
- Validaciones de fecha, propiedad y categoría
- Asociación con usuario autenticado
- Tipado por enum (`INCOME`, `EXPENSE`)

### 🗂️ Categorías
- Predeterminadas + personalizadas
- Propiedad por usuario
- Reutilizables en transacciones

### 🌐 Moneda e Idioma
- Moneda configurable por usuario
- Preparado para multilenguaje (español / inglés)

---

## 🧱 Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.4
- Spring Security + JWT
- JPA (Hibernate) + MySQL
- Maven
- Postman (para testing)
- GitHub (versionado)

---

## 🔒 Seguridad

- Passwords hasheadas con BCrypt
- JWT firmado con clave secreta
- Protección contra acceso no autorizado
- Filtros personalizados de autenticación

---

## 📦 Instalación y ejecución local

```bash
git clone https://github.com/tu-usuario/koink-backend.git
cd koink-backend
```

Asegurate de tener configurado tu archivo `application.properties` con:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/koink
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
app.jwt.secret=tu_clave_secreta
app.jwt.expiration=86400000
spring.jpa.hibernate.ddl-auto=update
```

Luego ejecutá:

```bash
./mvnw spring-boot:run
```

---

## ✨ Próximas funcionalidades

- CRUD de presupuestos por categoría
- Dashboard financiero por mes
- Exportación de movimientos (PDF, CSV)
- Login con Google
- Interfaz web con React + Context/Redux

---

## 🧠 Autor

**Juane Elizondo** – desarrollador fullstack apasionado por crear soluciones útiles, ordenadas y escalables.

📫 Contacto: [juane.elizondo23@gmail.com](mailto:juane.elizondo23@gmail.com)
💼 Portfolio: www.juaneelizondo.com

---
