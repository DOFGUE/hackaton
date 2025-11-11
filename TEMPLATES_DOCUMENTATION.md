# DOCUMENTACIÓN DE TEMPLATES - SISTEMA DE GESTIÓN

## Resumen de Estructura Creada

Se han creado exitosamente todos los templates del proyecto SistemaGestion utilizando **Bootstrap 5.3.0** sin estilos personalizados.

---

## 📁 Estructura de Directorios

```
src/main/resources/templates/
├── base.html                          (Template base - NO USADO actualmente)
├── login.html                         (Página de autenticación)
├── dashboard.html                     (Dashboard principal post-login)
│
├── solicitante/
│   ├── index.html                    (Panel solicitante)
│   ├── crear-requerimiento.html      (Formulario crear requerimiento)
│   ├── mis-requerimientos.html       (Listado de requerimientos)
│   └── ver-requerimiento.html        (Detalle de requerimiento)
│
├── operario/
│   ├── index.html                    (Panel operario)
│   ├── mis-tareas.html               (Listado de tareas asignadas)
│   └── ejecutar-tarea.html           (Formulario ejecutar tarea con progreso)
│
├── oficina-mantenimiento/
│   ├── index.html                    (Panel oficina mantenimiento)
│   └── requerimientos.html           (Listado de requerimientos pendientes)
│
└── jefe-programador-mantenimiento/
    ├── index.html                    (Panel jefe programador)
    └── asignaciones.html             (Gestión de asignaciones a operarios)
```

---

## 🎨 Características Bootstrap Utilizadas

### Componentes:
- **Navbar**: Barra de navegación con tema de color por rol
- **Cards**: Para paneles, estadísticas e información
- **Tables**: Tablas responsivas con hover effects
- **Forms**: Formularios con validación Bootstrap
- **Alerts**: Alertas para mensajes de éxito/error
- **Badges**: Etiquetas para categorías y estados
- **Progress Bars**: Barras de progreso animadas
- **Modals**: Ventanas modales para asignaciones
- **Buttons**: Botones con colores temáticos
- **Grid System**: Layout responsivo col-md-12, col-md-6, etc.

### Iconos:
Utilizados **Bootstrap Icons 1.11.0** para todos los iconos del sistema

---

## 🔐 ROLES Y COLORES ASIGNADOS

| Rol | Color | Navbar | Badges |
|-----|-------|--------|--------|
| **Solicitante** | Azul (Primary) | bg-primary | badge bg-primary |
| **Operario** | Rojo (Danger) | bg-danger | badge bg-danger |
| **Oficina Mantenimiento** | Verde (Success) | bg-success | badge bg-success |
| **Jefe Programador** | Amarillo (Warning) | bg-warning | badge bg-warning |

---

## 📄 DESCRIPCIÓN DE TEMPLATES

### 1. **login.html** 
- Página de autenticación
- Formulario con campos: usuario, contraseña
- Diseño atractivo con gradiente
- Alertas para errores, logout, sesión expirada
- **Ruta**: `/login`

### 2. **dashboard.html**
- Panel principal post-autenticación
- Muestra cards para cada rol disponible
- Información del usuario autenticado
- Enlaces rápidos a cada panel de rol
- **Ruta**: `/dashboard`

---

## 👥 PANEL SOLICITANTE

### 3. **solicitante/index.html** (Dashboard)
- Estadísticas: Total requerimientos, En progreso, Completados
- Acciones rápidas: Crear nuevo, Ver todos
- Información del usuario
- **Ruta**: `/solicitante`

### 4. **solicitante/crear-requerimiento.html**
- **Formulario con campos**:
  - Descripción (textarea, máx 100 caracteres)
  - Ubicación
  - Dependencia
  - Fecha solicitud (auto-rellena fecha actual)
  - Categorías (select múltiple)
- JavaScript para validación
- **Ruta**: `POST /solicitante/crear`

### 5. **solicitante/mis-requerimientos.html**
- Tabla responsiva con requerimientos del solicitante
- Columnas: ID, Descripción, Ubicación, Dependencia, Fecha, Categorías, Acciones
- Botón "Ver" para cada requerimiento
- Alerta si no hay requerimientos
- **Ruta**: `/solicitante/mis-requerimientos`

### 6. **solicitante/ver-requerimiento.html**
- Detalle completo del requerimiento
- Información general
- Categorías asignadas (badges)
- Solicitantes
- Validaciones
- Estado actual
- **Ruta**: `/solicitante/ver/{id}`

---

## 🔧 PANEL OPERARIO

### 7. **operario/index.html** (Dashboard)
- Estadísticas: Tareas asignadas, En progreso, Completadas
- Acciones rápidas: Ver mis tareas
- Información contextual
- **Ruta**: `/operario`

### 8. **operario/mis-tareas.html**
- Tabla de tareas asignadas al operario
- Columnas: ID, Descripción, Ubicación, Dependencia, Fecha, Estado, Acciones
- Botón "Ejecutar" para cada tarea
- **Ruta**: `/operario/mis-tareas`

### 9. **operario/ejecutar-tarea.html**
- **Información de la tarea**: ID, Estado, Descripción, Ubicación, Dependencia, Fecha
- **Formulario de progreso**:
  - Porcentaje de avance (0-100%) con barra visual dinámica
  - Observaciones (textarea)
- **Botón completar tarea** con formulario separado:
  - Reporte final
  - Confirmación con diálogo
- JavaScript para actualizar barra de progreso en tiempo real
- Colores dinámicos en la barra (rojo < 50%, amarillo < 75%, azul < 100%, verde = 100%)
- **Rutas**: 
  - GET `/operario/ejecutar/{id}`
  - POST `/operario/ejecutar/{id}` (guardar progreso)
  - POST `/operario/completar/{id}` (completar tarea)

---

## 🏢 PANEL OFICINA DE MANTENIMIENTO

### 10. **oficina-mantenimiento/index.html** (Dashboard)
- Estadísticas: Total requerimientos, En proceso, Completados
- Acciones rápidas: Ver todos requerimientos
- Información contextual
- **Ruta**: `/oficina-mantenimiento`

### 11. **oficina-mantenimiento/requerimientos.html**
- Filtros avanzados:
  - Búsqueda por descripción
  - Filtro por estado
- Tabla responsiva con todos los requerimientos
- Columnas: ID, Descripción, Ubicación, Dependencia, Fecha, Estado, Acciones
- Botón "Ver" para cada requerimiento
- **Ruta**: `/oficina-mantenimiento/requerimientos-pendientes`

---

## 👨‍💼 PANEL JEFE PROGRAMADOR

### 12. **jefe-programador-mantenimiento/index.html** (Dashboard)
- Estadísticas: Tareas pendientes, En ejecución, Completadas
- Acciones rápidas: Gestionar asignaciones
- Información contextual
- **Ruta**: `/jefe-programador-mantenimiento`

### 13. **jefe-programador-mantenimiento/asignaciones.html**
- Filtros avanzados:
  - Búsqueda por ID
  - Selección de operario
  - Filtro por estado
- Tabla de asignaciones con:
  - ID Tarea, Descripción, Operario asignado
  - Barra de progreso visual
  - Estado, Acciones
- **Modal de asignación**:
  - Seleccionar operario
  - Establecer prioridad (Baja, Normal, Alta, Urgente)
  - Campo de notas
- Botón "Asignar" con modal interactivo
- **Ruta**: `/jefe-programador-mantenimiento/asignaciones`

---

## 📋 TABLAS DE BASES DE DATOS UTILIZADAS

Los templates hacen referencia a las siguientes tablas:

| Tabla | Campos Utilizados |
|-------|-------------------|
| **usuarios** | idUsuario, nombreUsuario, enabled |
| **requerimientos** | idRequerimiento, descripcionRequerimiento, ubicacionRequerimiento, dependenciaRequerimiento, fechaSolicitud |
| **categorias** | idCategoria, nombreCategoria |
| **validacion** | idValidacion, tipoValidacion |
| **estados** | idEstado, nombreEstado |
| **requerimientos_categorias** (Join) | idRequerimiento, idCategoria |
| **requerimientos_usuarios** (Join) | idRequerimiento, idUsuario |
| **requerimientos_validaciones** (Join) | idRequerimiento, idValidacion |

---

## 🛠️ CONTROLADORES ASOCIADOS

### LoginController
- GET `/login` → login.html
- GET `/dashboard` → dashboard.html
- GET `/` → redirect:/dashboard

### SolicitanteController
- GET `/solicitante` → solicitante/index.html
- GET `/solicitante/crear` → solicitante/crear-requerimiento.html
- POST `/solicitante/crear` → Crear requerimiento
- GET `/solicitante/mis-requerimientos` → solicitante/mis-requerimientos.html
- GET `/solicitante/ver/{id}` → solicitante/ver-requerimiento.html

### OperarioController
- GET `/operario` → operario/index.html
- GET `/operario/mis-tareas` → operario/mis-tareas.html
- GET `/operario/ejecutar/{id}` → operario/ejecutar-tarea.html
- POST `/operario/ejecutar/{id}` → Guardar progreso
- POST `/operario/completar/{id}` → Completar tarea

### OficinaMantenimientoController
- GET `/oficina-mantenimiento` → oficina-mantenimiento/index.html
- GET `/oficina-mantenimiento/requerimientos-pendientes` → oficina-mantenimiento/requerimientos.html

### JefeProgramadorMantenimientoController
- GET `/jefe-programador-mantenimiento` → jefe-programador-mantenimiento/index.html
- GET `/jefe-programador-mantenimiento/asignaciones` → jefe-programador-mantenimiento/asignaciones.html

---

## 🔒 SEGURIDAD Y ROLES

Los templates responden a roles configurados en **securityConfig.java**:

- `SOLICITANTE`, `DOCENTE`, `FUNCIONARIO`, `ESTUDIANTE_AUTORIZADO`
- `OFICINA_MANTENIMIENTO`
- `JEFE_PROGRAMADOR_MANTENIMIENTO`
- `OPERARIO`

Cada template tiene acceso condicionado mediante Thymeleaf security:
```html
<div sec:authorize="hasRole('OPERARIO')">...</div>
```

---

## 📱 CARACTERÍSTICAS RESPONSIVAS

- Todos los templates utilizan Bootstrap grid (col-md-12, col-md-6, col-md-4, etc.)
- Tablas responsivas con `.table-responsive`
- Navbar togglable para dispositivos móviles
- Cards adaptativas
- Formularios responsivos

---

## 🎯 FUNCIONALIDADES JAVASCRIPT

1. **login.html**: Ninguno (estándar)
2. **operario/ejecutar-tarea.html**: 
   - Barra de progreso dinámica con colores
   - Auto-relleno de fecha actual
3. **solicitante/crear-requerimiento.html**:
   - Auto-relleno de fecha actual

---

## 📝 NOTAS IMPORTANTES

1. Los templates están totalmente basados en **Bootstrap 5.3.0** sin CSS personalizado
2. Todos los iconos provienen de **Bootstrap Icons 1.11.0**
3. Los templates usan **Thymeleaf** como motor de plantillas
4. Se utiliza **Thymeleaf Security** para control de acceso condicional
5. Las alertas (success, error, info) aparecen al inicio de cada página
6. El footer es consistente en todas las páginas
7. La navegación varía según el rol del usuario
8. Las tablas contienen datos dinámicos desde el backend
9. Los formularios están listos para procesar datos POST
10. Se incluyen validaciones básicas HTML5 (required, min, max)

---

## ✅ CHECKLIST DE IMPLEMENTACIÓN

- ✅ Login con autenticación
- ✅ Dashboard central con selector de roles
- ✅ Panel Solicitante (crear, listar, ver requerimientos)
- ✅ Panel Operario (tareas, ejecutar, completar)
- ✅ Panel Oficina Mantenimiento (gestionar requerimientos)
- ✅ Panel Jefe Programador (asignar tareas)
- ✅ Navegación por rol
- ✅ Colores temáticos por rol
- ✅ Tablas responsivas
- ✅ Formularios validados
- ✅ Alertas de usuario
- ✅ Modal para asignaciones
- ✅ Barra de progreso dinámica

---

**Fecha de creación**: 2024
**Versión**: 1.0
**Framework**: Spring Boot 3.5.7
**Template Engine**: Thymeleaf
**UI Framework**: Bootstrap 5.3.0
**Icons**: Bootstrap Icons 1.11.0
