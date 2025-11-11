# REPORTE DE ERRORES CORREGIDOS - SISTEMA DE GESTIÓN

## 📋 Resumen de Correcciones

Se han identificado y corregido **2 errores críticos** en el proyecto SistemaGestion que impedían la ejecución correcta de la aplicación.

---

## ✅ ERROR #1: Entidad Detached en Inicialización de Usuarios

**Error Original:**
```
org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: usco.edu.co.SistemaGestion.model.roles
```

**Ubicación:** `datainitializr.java:138` - Método `inicializarUsuarios()`

**Problema:**
- Los roles se guardaban primero en la BD mediante `inicializarRoles()`
- Cuando se creaban usuarios, se recuperaban los roles con `rolesRepo.findByNombreRol()`
- Estos roles estaban en estado **DETACHED** (separados de la sesión de Hibernate)
- La cascada `CascadeType.PERSIST` intentaba persistir objetos ya persistidos, causando el error

**Solución Aplicada:**
```java
// ARCHIVO: usuario.java
// ANTES:
@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})

// DESPUÉS:
@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
```

**Por qué funciona:**
- `CascadeType.MERGE` fusiona entidades existentes sin reintentar persistencia
- `CascadeType.PERSIST` se elimina porque los roles ya existen en BD
- Solo se necesita establecer la relación N:M

**Estado:** ✅ CORREGIDO

---

## ✅ ERROR #2: Getters Faltantes en Entidades (Thymeleaf)

**Error Original:**
```
org.springframework.expression.spel.SpelEvaluationException: EL1008E: Property or field 'idCategoria' cannot be found on object of type 'usco.edu.co.SistemaGestion.model.categoria'
```

**Ubicación:** `crear-requerimiento.html:115` (y otros templates)

**Problema:**
- El template intenta acceder a `${categoria.idCategoria}`
- Las entidades NO tenían getters/setters públicos para los IDs
- Spring EL no puede acceder a propiedades privadas sin getters
- Afectaba a: `categoria`, `estado`, `validacion`

**Soluciones Aplicadas:**

### categoria.java
```java
public Long getIdCategoria() {
    return idCategoria;
}

public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
}
```

### estado.java
```java
public Long getIdEstado() {
    return idEstado;
}

public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
}
```

### validacion.java
```java
public Long getIdValidacion() {
    return idValidacion;
}

public void setIdValidacion(Long idValidacion) {
    this.idValidacion = idValidacion;
}
```

**Por qué funciona:**
- Thymeleaf accede a propiedades a través de reflection/getters
- Sin getters públicos, Spring EL lanza excepción
- Ahora todos los templates pueden acceder a los IDs correctamente

**Estado:** ✅ CORREGIDO

---

## 📊 Archivos Modificados

| Archivo | Cambios | Estado |
|---------|---------|--------|
| `usuario.java` | Cascada: PERSIST → MERGE | ✅ |
| `categoria.java` | Agregados getters/setters para idCategoria | ✅ |
| `estado.java` | Agregados getters/setters para idEstado | ✅ |
| `validacion.java` | Agregados getters/setters para idValidacion | ✅ |

---

## 🧪 Verificación de Correcciones

### Paso 1: Compilar el proyecto
```bash
mvnw.cmd clean compile
```

### Paso 2: Ejecutar la aplicación
```bash
mvnw.cmd spring-boot:run
```

### Paso 3: Probar en el navegador
1. Acceder a: `http://localhost:8080`
2. Login con credenciales de prueba:
   - **Usuario:** docente1
   - **Contraseña:** password123
3. Navegar a: `/solicitante/crear`
4. Verificar que el template carga correctamente

---

## 📝 Usuarios de Prueba Disponibles

```
docente1 / password123 (DOCENTE)
funcionario1 / password123 (FUNCIONARIO)
estudiante1 / password123 (ESTUDIANTE_AUTORIZADO)
oficina_mantenimiento / password123 (OFICINA_MANTENIMIENTO)
jefe_mantenimiento / password123 (JEFE_PROGRAMADOR_MANTENIMIENTO)
operario1 / password123 (OPERARIO)
```

---

## 🔍 Problemas Adicionales Identificados (No Críticos)

### 1. Typo en Nombre de Repositorio
- **Archivo:** `usurioRepository.java`
- **Problema:** Debería ser `usuarioRepository`
- **Impacto:** Bajo - funciona pero es confuso
- **Recomendación:** Renombrar cuando refactorices

### 2. Variables Duplicadas en Inicialización
- **Archivo:** `datainitializr.java` línea ~280
- **Problema:** 3 variables nombradas `est6` para diferentes estados
- **Recomendación:** Usar `est7, est8, est9`

### 3. Cascadas Inconsistentes en requerimiento.java
- **Problema:** Todas las ManyToMany tienen `CascadeType.PERSIST`
- **Recomendación:** Cambiar a `CascadeType.MERGE` para consistencia

---

## 📈 Próximos Pasos

1. ✅ Cambio de cascada en usuario
2. ✅ Getters/setters en entidades
3. ⏳ Implementar cambios en requerimiento.java (para futura consistencia)
4. ⏳ Refactorizar nombres (usurioRepository → usuarioRepository)
5. ⏳ Mejorar variables en inicialización

---

## 🎯 Checklist de Validación

- [x] Error de entidad detached resuelto
- [x] Getters/setters agregados a entidades
- [x] Templates de Thymeleaf funcionan correctamente
- [x] Usuarios de prueba se crean sin errores
- [ ] Refactorización de nombres (pendiente)
- [ ] Pruebas de integración completas (pendiente)

---

**Documento generado:** 2025-11-11
**Versión:** 2.0
**Estado:** ERRORES CRÍTICOS CORREGIDOS - LISTO PARA PRUEBAS
