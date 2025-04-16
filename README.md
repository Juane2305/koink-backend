
# 💰 Koink - Backend

Koink es una aplicación web de **gestión de finanzas personales y familiares**, diseñada para ayudarte a controlar tus ingresos, gastos, y presupuestos de forma clara, segura y profesional.

Este repositorio contiene el backend desarrollado en **Java 17 + Spring Boot 3.4.4**, utilizando autenticación JWT, arquitectura limpia basada en dominios, y preparado para internacionalización y multimoneda.

---

## 🚀 Funcionalidades actuales

### 🔐 Autenticación
- Registro con email y contraseña
- Login seguro con JWT
- ✅ Login con Google totalmente funcional
- Endpoint `/user/me` para obtener perfil autenticado
- Protección de rutas privadas
- Ocultamiento de datos sensibles (`password`, `provider`)

### 💸 Transacciones
- CRUD completo de ingresos y egresos
- Tipado por enum (`INCOME`, `EXPENSE`)
- Validaciones de monto positivo y categoría válida
- Asociación automática con el usuario autenticado

### 🗂️ Categorías
- Categorías predeterminadas + personalizadas
- CRUD completo con validación de ownership
- Prevención de duplicados por nombre
- Reutilizables en transacciones y presupuestos

### 📊 Presupuestos
- Creación de presupuestos por categoría
- Períodos: `DAILY`, `WEEKLY`, `MONTHLY`, `ANNUAL`
- Cálculo automático de fecha de fin
- Prevención de solapamientos por categoría
- Eliminación protegida si está en uso

### 📈 Dashboard financiero
- Ingresos y egresos totales
- Balance calculado automáticamente
- Transacciones recientes
- Presupuestos activos con progreso actual

### 📤 Exportación de reportes (PDF)
- Exportación anual en formato PDF
- Tabla mensual con nombre del mes y total gastado
- Sumatoria final del año con formato argentino (`2.559.500,00`)
- Descargable vía endpoint protegido

### 📬 Alertas por presupuesto superado
- Revisión automática cada hora con `@Scheduled`
- Envío de email a usuarios con presupuestos excedidos
- Usa Mailtrap como entorno de testing
- Respeta preferencia del usuario (`alertsByEmail`)

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
- OpenPDF (para exportar reportes)
- Mailtrap (testing de email)
- Postman (para testing)
- GitHub (versionado)

---

## 🔒 Seguridad

- Passwords hasheadas con BCrypt
- JWT firmado con clave secreta
- Protección de rutas y datos sensibles
- Filtro de autenticación personalizado
- Validaciones de propiedad y datos por usuario

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

spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=TU_MAILTRAP_USER
spring.mail.password=TU_MAILTRAP_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

▶️ Para ejecutar el proyecto localmente:

```bash
./mvnw spring-boot:run
```

---

## ✨ Próximas funcionalidades (v2.0)

- 📊 Reportes filtrables por categoría y fecha
- 🧾 Exportación por categoría y por mes (PDF / CSV)
- 🧠 Sugerencias automáticas basadas en hábitos de gasto
- 🌐 Login con GitHub
- 🧮 Cálculo de ahorro mensual y metas financieras

---

## 🧠 Autor

**Juane Elizondo** – desarrollador fullstack apasionado por crear soluciones útiles, ordenadas y escalables.

📫 Contacto: [juane.elizondo23@gmail.com](mailto:juane.elizondo23@gmail.com)  
💼 Portfolio: [www.juaneelizondo.com](https://www.juaneelizondo.com)

---

## 🧠 Créditos adicionales

Este backend fue planificado y desarrollado como parte del proyecto **Koink**, una solución integral para el control de finanzas personales, buscando calidad profesional y aprendizaje profundo en tecnologías backend modernas.

---
