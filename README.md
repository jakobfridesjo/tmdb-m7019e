# TMDB-M7019E

TMDB-M7019E is an App developed for the advanced Android programming course at LTU. 
The app utilizes the [TMDB API](https://developer.themoviedb.org/docs) to fetch movies and their related videos and reviews.

##  User guide

Create a `SECRETS.kt` file and populate <TMDB API Key (v3 auth) with your personal TMDB API key:

---

package com.ltu.m7019e.v23.themoviedb.utils

object SECRETS {
    const val API_KEY = "<TMDB API Key (v3 auth)>"
}

---

### Features

The app has the following features:

- Browse most popular and top voted movies
- Mark movies as favorites and persist the movies locally
- Read reviews and watch videos related to the movies

### Implementation details

- Extensively uses the MVVM pattern
- Room database for persisting data and caching
- Repository pattern for fetching data cohesively
- NetworkCallback for listening to network changes

### Contributors

- [Sandeep Patil](https://github.com/sandeepspatil/)
- [Jakob Fridesjö](https://github.com/jakobfridesjo/)

