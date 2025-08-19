# MinutaNutricionalApp

Aplicación Android (Jetpack Compose) para gestionar una minuta semanal con recetas veganas. Incluye flujo de autenticación básico (inicio de sesión, registro y recuperación de contraseña), una pantalla principal con filtros y grilla de recetas, y una pantalla de detalle.

## Características principales

* Cuatro pantallas: Login, Registro, Recuperar contraseña y Minuta semanal.
* Detalle de receta al tocar “Ver receta completa”.
* Filtros en la minuta: día de la semana, opción de bajas calorías y orden por calorías asc/desc.
* Grilla adaptativa de recetas usando `LazyVerticalGrid`.
* Navegación con `androidx.navigation:navigation-compose`.
* UI con Material 3 y paleta con acento rosado.
* Diseño pensado para usuarios con baja experiencia informática: inputs claros, botones visibles y textos directos.

## Requisitos

* Android Studio Ladybug o superior.
* JDK 17 (el IDE lo gestiona).
* Gradle y Kotlin DSL (proyecto ya configurado).
* SDK Android 24+ (la app apunta a API recientes, pero corre desde Nougat en adelante).

## Tecnologías y librerías

* Kotlin y Jetpack Compose.
* Material 3 (`androidx.compose.material3`).
* Navigation Compose (`androidx.navigation:navigation-compose`).
* Foundation / Lazy grid para grillas adaptativas.

## Estructura relevante

```
app/
 └─ src/main/java/com/example/minutanutricionalapp2/
     ├─ MainActivity.kt
     ├─ NavGraph.kt
     ├─ LoginScreen.kt
     ├─ RegisterScreen.kt
     ├─ RecoverScreen.kt
     ├─ MinutaScreen.kt
     └─ DetailScreen.kt
```

## Configuración y ejecución

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle si el IDE lo solicita.
3. Seleccionar un dispositivo virtual (AVD) o un teléfono físico con depuración USB.
4. Ejecutar la configuración `app`.

La app abre en la pantalla de Login. Con correo y contraseña no vacíos se ingresa a la Minuta.

## Navegación

* El grafo de navegación está en `NavGraph.kt`.
* Rutas definidas: `login`, `register`, `recover`, `minuta`, `detail/{title}/{tips}`.
* Para el detalle se codifican parámetros con `Uri.encode` y se decodifican en `DetailScreen`.

Ejemplo de navegación desde la minuta al detalle:

```kotlin
val encodedTitle = Uri.encode(recipe.title)
val encodedTips  = Uri.encode("Consejo: hidrátate bien y prioriza verduras de hoja.")
navController.navigate("detail/$encodedTitle/$encodedTips")
```

## Datos de ejemplo

`MinutaScreen.kt` incluye una lista `veganRecipes` con recetas de muestra (título, día, calorías, etiquetas y notas). Está pensada para cumplir con el requisito de “array con al menos 5 recetas y recomendaciones”.

## Diseño y estilo

* Material 3 con superficies y controles estándar.
* Tono rosado aplicado en la paleta del tema del módulo `ui.theme`.
* Tipografías y tamaños por defecto de Material para asegurar legibilidad.
* Botones de acción con `Text`, `OutlinedTextField` con `leadingIcon` en formularios y `AssistChip` para etiquetas de receta.

## Accesibilidad y usabilidad

* Etiquetas descriptivas en campos y botones.
* Jerarquía tipográfica clara: títulos, cuerpo, chips informativos.
* Navegación predecible con barra superior y flecha de retorno en pantallas secundarias.

## Calidad y mantenimiento

* Código organizado por pantallas y navegación.
* Dependencias actualizadas a versiones estables.
* Sin dependencias experimentales en producción.
* Warning de `menuAnchor()` resuelto usando `MenuAnchorType.PrimaryNotEditable`.

## Pruebas manuales sugeridas

* Ingresar con email y contraseña vacíos para verificar el aviso.
* Flujo Login → Minuta → “Ver receta completa” → volver.
* Registro y Recuperación: navegación y carga visual.
* Filtros de la minuta: seleccionar día, alternar “≤ 500 kcal”, cambiar orden.
* Rotación de dispositivo y comprobación de que la UI se adapta.

## Problemas comunes

* Emulador atascado: usar Cold Boot desde **Device Manager**.
* Diferencia entre JAVA\_HOME y JDK de Gradle: alinear la versión desde Settings → Build Tools → Gradle → Gradle JDK.
* Antivirus impactando compilación: excluir carpetas `.gradle`, `AndroidStudioProjects`, `Sdk` y el directorio del IDE.

## Próximos pasos

* Persistencia local (Room) para guardar recetas y preferencias de filtros.
* Autenticación real (Firebase Auth) y validaciones de formularios.
* Edición y creación de recetas propias.
* Tests instrumentados para navegación y UI.

## Licencia

Este proyecto se entrega con fines académicos. Ajustar licencia según las necesidades del curso o del autor.
