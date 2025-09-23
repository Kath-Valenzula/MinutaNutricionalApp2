# Minuta Nutricional App

App Android en **Kotlin + Jetpack Compose** que sugiere una **minuta semanal** de recetas veganas.  
Incluye inicio de sesión/registro, vista de **Minuta**, **Detalle** de receta, **Ajustes** y un **resumen nutricional** con meta diaria configurable.

---

## Matriz de versiones
Android Studio Koala (2024.1.1) | AGP 8.5.1 | Gradle wrapper 8.7 | Kotlin 1.9.24 | JDK 21 | compileSdk 34 | targetSdk 34 | minSdk 24

---

## ¿Qué hace la app?
- **Autenticación simple**: Iniciar sesión, registrarse y recuperar acceso (en memoria para efectos del curso).
- **Minuta semanal**: Grilla adaptativa con recetas; filtros por día, opción “≤ 500 kcal” y orden por calorías.
- **Detalle de receta**: Ingredientes, pasos y tips.
- **Consumo diario**: Botón “Agregar a mi día” que acumula kcal y macros; **anillo de progreso** según meta.
- **Ajustes**: Preferencias básicas (volumen/mute de música si está activa, tema, datos del usuario).
- **Conexión a internet**: Banner de conectividad visible si no hay red.

---

## ¿Cómo se ejecuta?
1. Abre el proyecto en **Android Studio Koala (2024.1.1)** o superior.
2. Sincroniza Gradle y compila (API 24+).
3. Ejecuta en emulador o dispositivo con internet.
4. Flujo recomendado: **Splash → Login → Minuta → Detalle/Ajustes**.  
   Puedes crear una cuenta o usar los usuarios semilla definidos en `UserRepository.kt`.

---

## Cumplimiento (Semana 6)
- **Criterio 1 — UI y componentes Android/Compose**  
  Formularios (TextField, Button), filtros (Dropdown, Checkbox, Radio), Chips, grilla (`LazyVerticalGrid`) y navegación entre pantallas.
- **Criterio 2 — Repositorio Git**  
  Proyecto completo en un repo público, con historial y estructura de Android Studio.
- **Criterio 3 — Extensiones KTX**  
  Dependencias `core-ktx`, `lifecycle-runtime-ktx`, `activity-ktx` y uso de **`toUri()`** (Core KTX) para construir parámetros de navegación en Minuta → Detalle.

---

## Accesibilidad y usabilidad
- Controles Material 3 con **tamaños táctiles ≥ 48dp**.
- Textos legibles, contraste y uso de `semantics`/`contentDescription` en elementos clave.
- Flujos simples pensados para usuarios con baja experiencia tecnológica.

---

## Estructura del código (resumen)
app/src/main/java/com/example/minutanutricionalapp2/
├─ data/ # Repos in-memory: usuarios, recetas, meta calórica, tracker
├─ model/ # Modelos: Recipe, Nutrition, Totals, User
├─ ui/ # Pantallas Compose: Splash, Login, Register, Recover, Minuta, Detail, Settings
├─ util/ # Utilidades: NetworkBanner, drawableIdByName(...)
└─ NavGraph.kt# Rutas y argumentos de navegación


---

## Imágenes de recetas
- Carpeta: `app/src/main/res/drawable/`
- Formato: **WebP** (sRGB), ~900–1280 px ancho; peso objetivo 80–200 KB.
- Nombres en **snake_case** (sin acentos/espacios). Ejemplos:
quinoa_garbanzos_bowl.webp
tacos_lentejas.webp
curry_verduras_tofu.webp
pasta_pesto_vegano.webp
ensalada_porotos_negros.webp
garbanzos_curry.webp
lentejas_zapallo.webp
porotos_granados_vegano.webp


- Si falta una imagen, la app muestra un **placeholder**.

---

## Notas del curso
- Persistencia y autenticación se mantienen **en memoria** para enfocarnos en el Front End y Kotlin.
- La meta calórica puede definirse al entrar a **Minuta** y cambiarse desde el ícono del bowl.
- El objetivo es una interfaz **clara y accesible**, con navegación estable y componentes estándar.
