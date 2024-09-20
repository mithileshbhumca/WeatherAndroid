# Weather App - Android

Weather App is a simple Android application that allows users to get current weather conditions and a 5-day weather forecast based on city names. It uses the OpenWeatherMap API to fetch weather data and displays the following information:

- Current temperature and weather description.
- 5-day forecast with daily high and low temperatures.
  
## Features

- **Search for weather by city name**: Users can search for weather conditions by entering the name of a city in the search bar.
- **City list auto-complete**: As users type in the search bar, the app fetches matching city names and shows a list of suggestions.
- **Current weather conditions**: The app displays real-time temperature and a brief weather description.
- **5-day forecast**: The app provides a forecast of high and low temperatures along with weather conditions for the next 5 days.

## Technologies Used

- **Kotlin**: The app is built using Kotlin for native Android development.
- **Coroutines**: Asynchronous network calls are managed using Kotlin coroutines.
- **OpenWeatherMap API**: The app fetches weather data from the OpenWeatherMap API.
- **View Binding**: The app uses view binding for easier interaction with views.
- **View Model**: The app uses View model for easier communication with views and data sources.
- **Retrofit**: Network requests are handled using Retrofit, a type-safe HTTP client.
- ## Architecture: MVVM (Model-View-ViewModel)
This project follows the **MVVM** (Model-View-ViewModel) architecture pattern to separate concerns and create a scalable and maintainable application structure. Below is an overview of the architecture and how it’s applied in this project:

  
## API Key Setup

To use the app, you need an API key from OpenWeatherMap. You can sign up for a free API key [here](https://openweathermap.org/).

Once you have the API key, replace `YOUR_API_KEY` in the code with your actual API key in buildConfigField under project level of BuildGradle in the app.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/mithileshbhumca/WeatherAndroid.git
   
## How to Run
Make sure you have the following installed:
- [Android Studio](https://developer.android.com/studio).

- A stable internet connection for downloading dependencies.

- After build finish, run it real device or install android studio emulator.

## Screenshot
  ##Screen1 When we open app show simple screen to search city name for weather details, its showing all available city matched with typed text
  
   ![weather1](https://github.com/user-attachments/assets/b71195d5-0c78-49f3-a0e3-f4b1af5a1ab1)

    ##Screen2 Once click on any city from screen1, then navigate to screen2 for city weather details, showing current temp, and five days forecast data

   ![weather2](https://github.com/user-attachments/assets/df0c879d-0eec-4f8f-80b2-3b94dc7b50c2)





