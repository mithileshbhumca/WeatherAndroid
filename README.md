# WeatherAndroid

WeatherAndroid is a simple Android application that allows users to get current weather conditions and a 5-day weather forecast based on city names. It uses the OpenWeatherMap API to fetch weather data and displays the following information:

- Current temperature, humidity, wind speed, and weather description.
- 5-day forecast with daily high and low temperatures.
  
## Features

- **Search for weather by city name**: Users can search for weather conditions by entering the name of a city in the search bar.
- **Current weather conditions**: The app displays real-time temperature, humidity, wind speed, and a brief weather description.
- **5-day forecast**: The app provides a forecast of high and low temperatures along with weather conditions for the next 5 days.
- **City list auto-complete**: As users type in the search bar, the app fetches matching city names and shows a list of suggestions.

## Technologies Used

- **Kotlin**: The app is built using Kotlin for native Android development.
- **Coroutines**: Asynchronous network calls are managed using Kotlin coroutines.
- **OpenWeatherMap API**: The app fetches weather data from the OpenWeatherMap API.
- **View Binding**: The app uses view binding for easier interaction with views.
- **Retrofit**: Network requests are handled using Retrofit, a type-safe HTTP client.
  
## API Key Setup

To use the app, you need an API key from OpenWeatherMap. You can sign up for a free API key [here](https://openweathermap.org/).

Once you have the API key, replace `YOUR_API_KEY` in the code with your actual API key in `BuildGradle` or wherever it's being used in the app.

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/mithileshbhumca/WeatherAndroid.git
   
## Screenshot
   ![image2](https://github.com/user-attachments/assets/d024b120-6db9-45f0-ade7-4d381719f729)
![image1](https://github.com/user-attachments/assets/b39c789c-0868-4e3d-8681-4890e82b9d66)

