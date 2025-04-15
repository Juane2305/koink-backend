# 💰 Koink - Backend

Koink es una aplicación web de **gestión de finanzas personales y familiares**, diseñada para ayudarte a controlar tus ingresos, gastos, y presupuestos de forma clara, segura y profesional.

Este repositorio contiene el backend desarrollado en **Java 17 + Spring Boot 3.4.4**, utilizando autenticación JWT, arquitectura limpia basada en dominios, y preparado para internacionalización y multimoneda.

---

## 🚀 Funcionalidades actuales

### 🔐 Autenticación
- Registro con email y contraseña
- Login seguro con JWT
- Login con Google (próxima versión)
- Endpoint `/user/me` para obtener perfil autenticado
- Protección de rutas privadas
- Ocultamiento de datos sensibles (`password`, `provider`)

### 💸 Transacciones
- CRUD completo de ingresos y egresos
- Tipado por enum (`INCOME`, `EXPENSE`)
- Validaciones de fecha, propiedad y categoría
- Asociación automática con el usuario autenticado

### 🗂️ Categorías
- Categorías predeterminadas + personalizadas
- CRUD completo con validación de ownership
- Asociación con usuario (o públicas)
- Reutilizables en transacciones y presupuestos

### 📊 Presupuestos
- Creación de presupuestos por categoría
- Períodos: `DAILY`, `WEEKLY`, `MONTHLY`, `ANNUAL`
- Cálculo automático de fecha de fin
- Asociación con usuario
- Control de acceso y edición segura
- Eliminación protegida si está en uso

### 📈 Dashboard financiero
- Ingresos y egresos totales
- Balance calculado automáticamente
- Transacciones recientes
- Presupuestos activos con progreso actual

### 🌐 Moneda e Idioma
- Moneda configurable por usuario (ej: ARS, USD, EUR)
- Estructura preparada para multilenguaje (español / inglés)

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
- Protección de rutas y datos sensibles
- Filtro de autenticación personalizado

---

## 📦 Instalación y ejecución local

```bash
git clone https://github.com/Juane2305/koink-backend.git
cd koink-backend
```

🔧 Asegurate de configurar tu archivo `application.properties` o `.env` con:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/koinkdb
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

app.jwt.secret=TU_SECRETO
app.jwt.expiration=3600000

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

▶️ Para ejecutar el proyecto localmente:

```bash
./mvnw spring-boot:run
```

---

## ✨ Próximas funcionalidades

- 📧 Alertas automáticas por mail al superar presupuestos
- 📤 Exportación de movimientos (CSV / PDF)
- 📅 Reportes y filtros por fecha y categoría
- 🌐 Login con Google
- 📊 Visualizaciones en frontend con gráficos e insights

---

## 🧠 Autor

**Juane Elizondo** – desarrollador fullstack apasionado por crear soluciones útiles, ordenadas y escalables.

📫 Contacto: [juane.elizondo23@gmail.com](mailto:juane.elizondo23@gmail.com)  
💼 Portfolio: [www.juaneelizondo.com](https://www.juaneelizondo.com)

---

## 🧠 Créditos adicionales

Este backend fue planificado y desarrollado como parte del proyecto **Koink**, una solución integral para el control de finanzas personales, buscando calidad profesional y aprendizaje profundo en tecnologías backend modernas.

---
