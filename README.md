# MovieApp 🎬

Aplicación móvil Android desarrollada con **Kotlin** y **Jetpack Compose** que consume la API de **TMDB** (The Movie Database) para mostrar películas populares, permitiendo guardar favoritos y consultar detalles.

## 📱 Funcionalidades

- **Pantalla de inicio** animada con nombre del desarrollador
- **Listado de películas populares** con póster, título, rating y sinopsis
- **Detalle de película** con información completa (sinopsis, géneros, duración, rating)
- **Favoritos** persistidos localmente con Room
- **Búsqueda de películas**
- **Modo oscuro/claro** configurable
- **Navegación** entre pestañas (Películas, Favoritos, Ajustes)

## 🛠️ Tecnologías

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje principal |
| Jetpack Compose | UI declarativa |
| Material 3 | Diseño de interfaz |
| Navigation Compose | Navegación entre pantallas |
| Retrofit + Gson | Consumo de API REST |
| Room | Base de datos local (SQLite) |
| SharedPreferences | Preferencias de usuario |
| Coil | Carga de imágenes |
| ViewModel + StateFlow | Arquitectura MVVM |
| Corrutinas | Operaciones asíncronas |

## 📷 Capturas de pantalla

*(Agrega aquí capturas de tu app)*

## 📦 Requisitos

- Android SDK 26+
- API Key de [TMDB](https://www.themoviedb.org/settings/api)

## 🔧 Configuración

1. Clona el repositorio
2. Obtén una API Key en https://www.themoviedb.org/settings/api
3. Abre `app/src/main/java/com/example/movieapp/util/Constants.kt`
4. Reemplaza el valor de `TMDB_API_KEY` con tu clave
5. Abre el proyecto en Android Studio y ejecuta

## 🚀 Compilar e instalar

```bash
./gradlew clean assembleDebug
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## 📁 Estructura del proyecto

```
app/src/main/java/com/example/movieapp/
├── MainActivity.kt
├── MovieApp.kt
├── data/
│   ├── api/             # Retrofit + TMDB API
│   ├── local/           # Room (FavoriteMovie, DAO, Database)
│   ├── model/           # Clases de datos (Movie, MovieDetail)
│   └── repository/      # MovieRepository
├── ui/
│   ├── components/      # Componentes reutilizables
│   ├── navigation/      # NavGraph + rutas
│   ├── screens/
│   │   ├── detail/      # Pantalla de detalle
│   │   ├── list/        # Pantalla de listado
│   │   ├── settings/    # Pantalla de configuración
│   │   └── splash/      # Pantalla de inicio
│   └── theme/           # Colores, tipografía, tema
└── util/
    └── Constants.kt     # Constantes (API Key, URLs)
```

## 📄 Licencia

Proyecto académico - Trabajo Final de Dispositivos Móviles.
