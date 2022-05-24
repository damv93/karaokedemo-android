# Karaoke demo - Android

Android app showcasing a social media feed where users can see videos of other users singing.



https://user-images.githubusercontent.com/14865130/169726917-4978997b-f949-49fa-9ef0-7afbff308bee.mp4



### VideoPlayerRecyclerView
RecyclerView used in the feed to autoplay videos as the user scrolls. It uses **ExpoPlayer**.
The source code was taken from [this repo](https://github.com/mitchtabian/Video-Player-RecyclerView), migrating it to kotlin.

### The application uses the following:
- MVVM Architecture
- ViewModel + Data Binding for reactive programming
- Coroutines for async programming
- Repository pattern to handle data sources
- Retrofit for remote API calls
- Room Database for local storage
- Koin for Dependency Injection
