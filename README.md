# Country Explorer

**An Android app to browse every country in the world, filter by region and save favorites.**

Data comes live from the [REST Countries API](https://restcountries.com). Favorites are stored on the device so they stay available between sessions.

## Features

- List of all countries with flag, capital, region and population
- Live search by name
- Filter by region: Africa, Americas, Asia, Europe, Oceania
- Detail screen with languages and currencies
- Favorites saved locally with Room

## Tech stack

| Layer | Tools |
|-------|-------|
| Language | Java |
| UI | Android Views, Material Components, ConstraintLayout, RecyclerView |
| Networking | Retrofit + Gson |
| Images | Glide |
| Local storage | Room |
| Build | Gradle (Kotlin DSL) — minSdk 24, targetSdk 35 |

## Project structure

```
app/src/main/java/com/example/countryexplorer4/
├── MainActivity.java      # List, search, region filters, favorites
├── DetailActivity.java    # Country details
├── CountryApi.java        # Retrofit interface (REST Countries v3.1)
├── CountryAdapter.java    # RecyclerView adapter with filtering
├── Country.java           # API model
├── FavoriteCountry.java   # Room entity
├── CountryDao.java        # Room DAO
└── AppDatabase.java       # Room database
```

## Getting started

1. Clone the repository:
   ```bash
   git clone https://github.com/Khadija2005J/CountryExplorer.git
   ```
2. Open the folder in **Android Studio** and let Gradle sync.
3. Run the app on an emulator or a device (Android 7.0+). An internet connection is required.

## Author

Khadija Alaoui Mrani – [GitHub](https://github.com/Khadija2005J)
