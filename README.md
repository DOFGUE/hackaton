# 🔧 Sistema de Gestión de Requerimientos

Sistema web de gestión de requerimientos de mantenimiento desarrollado con **Spring Boot 3.5.7**, **MySQL**, **Thymeleaf** y **Bootstrap 5**.

---

## ⚡ Ejecución del Proyecto

### Requisitos Previos
- **Java 21+**
- **MySQL 8.0+**
- **Maven 3.6+** (incluido: `mvnw` / `mvnw.cmd`)

### Pasos para Ejecutar

#### 1. Configurar Base de Datos

Crear la base de datos:
```sql
CREATE DATABASE sistemagestiondb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistemagestiondb
spring.datasource.username=root
spring.datasource.password=prueba123
```

#### 2. Ejecutar la Aplicación

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

#### 3. Acceder a la Aplicación

```
http://localhost:8080
```

---

## 👥 Usuarios de Prueba por Rol

Al iniciar el proyecto, todos los datos se crean automáticamente. Usa estos usuarios para probar:

### 1️⃣ SOLICITANTE
- **Usuario**: `solicitante1`
- **Contraseña**: `password123`
- **Función**: Crea requerimientos
- **Dashboard**: http://localhost:8080/solicitante

### 2️⃣ OFICINA DE MANTENIMIENTO
- **Usuario**: `oficina_mantenimiento`
- **Contraseña**: `password123`
- **Función**: Valida requerimientos y gestiona completadas
- **Dashboard**: http://localhost:8080/oficina-mantenimiento

### 3️⃣ JEFE PROGRAMADOR
- **Usuario**: `jefe_mantenimiento`
- **Contraseña**: `password123`
- **Función**: Asigna operarios a tareas
- **Dashboard**: http://localhost:8080/jefe-programador-mantenimiento

### 4️⃣ OPERARIO
- **Usuario**: `operario1`
- **Contraseña**: `password123`
- **Función**: Ejecuta y completa tareas
- **Dashboard**: http://localhost:8080/operario

---

## 🔄 Flujo Recomendado para Probar

### Fase 1: Solicitante Crea Requerimiento
1. Ingresar con usuario `solicitante1`
2. Hacer clic en **"Crear Requerimiento"**
3. Llenar formulario y crear
4. ✅ Requerimiento en estado **ESPERA**

### Fase 2: Oficina Valida
1. Cerrar sesión y ingresar con `oficina_mantenimiento`
2. Ir a **"Requerimientos Pendientes"**
3. Seleccionar validación (Alta/Media/Baja)
4. Hacer clic en **"Asignar Validación"**
5. ✅ Requerimiento validado

### Fase 3: Jefe Asigna Operario
1. Cerrar sesión e ingresar con `jefe_mantenimiento`
2. Ir a **"Asignaciones"**
3. Hacer clic en **"Asignar"**
4. Seleccionar operario de la lista
5. Hacer clic en **"Asignar Operario"**
6. ✅ Operario asignado

### Fase 4: Operario Ejecuta
1. Cerrar sesión e ingresar con `operario1`
2. Ir a **"Mis Tareas"**
3. Hacer clic en **"Ejecutar"**
4. Registrar progreso:
   - Porcentaje: 50% (o más)
   - Estado: Seleccionar del dropdown
   - Observaciones: Agregar notas
5. Hacer clic en **"Guardar Progreso"**
6. Repetir múltiples veces si es necesario
7. Finalmente, hacer clic en **"Completar Tarea"**
8. ✅ Requerimiento en estado **COMPLETADO**

### Fase 5: Oficina Revisa Completadas
1. Ingresar con `oficina_mantenimiento`
2. Ir a **"Completadas"**
3. Ver requerimiento completado
4. **Opciones**:
   - **Reabrir**: Vuelve a estado ESPERA
   - **Eliminar**: Elimina permanentemente

---

## 📊 Estados del Sistema

- **ESPERA**: Requerimiento recién creado
- **EN VALIDACION**: Siendo validado por oficina
- **EN EJECUCION**: Siendo ejecutado por operario
- **COMPLETADO**: Finalizado por operario

---

## 🎯 Características Principales

✅ Gestión completa de requerimientos  
✅ Asignación de operarios automática  
✅ Seguimiento de progreso en tiempo real  
✅ Validaciones por importancia  
✅ Historial de requerimientos completados  
✅ Interfaz responsiva con Bootstrap 5  
✅ Autenticación segura con Spring Security  
✅ Base de datos relacional con JPA/Hibernate  

---

**Última actualización**: 11/11/2025
