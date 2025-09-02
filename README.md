
# MinutaNutricionalApp2

Aplicación Android en **Kotlin + Jetpack Compose** para gestionar una minuta nutricional semanal, con navegación, filtros, detalle nutricional y pantalla de bienvenida con logo y audio.


## Estructura principal
```

app/src/main/java/com/example/minutanutricionalapp2/
├─ data/
│  ├─ RecipesRepository.kt
│  ├─ NutritionRepository.kt
│  └─ IntakeTracker.kt
├─ model/
│  └─ Recipe.kt
│  └─ Nutrition.kt
├─ ui/
│  ├─ SplashScreen.kt
│  ├─ MinutaScreen.kt
│  ├─ DetailScreen.kt
│  └─ (LoginScreen.kt, RegisterScreen.kt, RecoverScreen.kt)\*
├─ NavGraph.kt
└─ MainActivity.kt
app/src/main/res/
├─ drawable/        (logo kath\_cl.png)
└─ raw/             (tema\_pokemon.ogg)

---

## Funcionalidades clave

- **Pantalla de bienvenida (Splash)**: fondo rosa, logo y mensaje; audio OGG durante 10 s; navegación automática a Login.
- **Minuta semanal**:
  - Selector de **día** con `ExposedDropdownMenuBox`.
  - **Checkbox** ≤ 500 kcal.
  - **RadioButtons** para orden por calorías (↑/↓).
  - Grilla adaptativa con `LazyVerticalGrid`.
  - Tarjeta por receta (título, kcal, tags) con botón “**Agregar a mi día**”.
  - **Barra de Totales** (kcal, proteínas, carbohidratos, grasas) acumulada en runtime.
  - **Snackbar** de confirmación al agregar.
  - Bloque de **estadísticas Kotlin** (uso de `fold`, `when`, `while`, `break/continue`).
- **Detalle de receta**: tabla nutricional (kcal, macros y vitaminas).

---

## Componentes de UI

- **Inputs/Texto**: `OutlinedTextField`, `Text`, tipografías Material 3.
- **Combo**: `ExposedDropdownMenuBox` + `DropdownMenuItem`.
- **Checklist**: `Checkbox` (filtro ≤ 500 kcal).
- **Radio**: `RadioButton` (orden asc/desc).
- **Botones**: `Button`, `OutlinedButton`, `TextButton`.
- **Grilla/Tabla**: `LazyVerticalGrid(GridCells.Adaptive)`, filas clave-valor en detalle.
- **Navegación**: `NavController` + `NavHost` + `composable` con argumentos.
- **Accesibilidad**: `semantics { contentDescription = ... }`, targets ≥48dp, contraste `primary`/`onPrimary`.

---

## Accesibilidad
- **Semánticas** descriptivas para TalkBack en pantallas, tarjetas y botones.
- **Contraste** garantizado usando `MaterialTheme.colorScheme.primary` / `onPrimary`.
- **Tamaño táctil** de acciones ≥48dp.
- Flujo **Arriba/Atrás** coherente (`popBackStack()`).

---

## Logo y audio del Splash
- Colocar el **logo** en `app/src/main/res/drawable/kath_cl.png`.
- Colocar el **audio** en `app/src/main/res/raw/tema_pokemon.ogg`.  
  - Recomendado: **OGG Vorbis**, 44.1 kHz, 128–160 kbps.  
  - Nombre en minúsculas, sin espacios ni tildes.
- Nota de licencia: confirma que tienes autorización para uso académico del audio/logos.

---

## Créditos y licencias

* UI: Material 3 + Jetpack Compose.
* El **logo** y el **audio** son propiedad de kath y la cancion de Pokemón. Úsalos con fines educativos, respetando derechos de autor.
* Código del estudiante, uso académico.

