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
- **Hilt**: Used for dependency injection to simplify the process of providing dependencies in Android components like Activities, Fragments, and ViewModels.
- **Navigation Component**:Handles in-app navigation, making fragment transitions, argument passing, and deep linking easier with a visual navigation graph.
  
- **Architecture: MVVM (Model-View-ViewModel)**:
This project follows the **MVVM** (Model-View-ViewModel) architecture pattern to separate concerns and create a scalable and maintainable application structure. Below is an overview of the architecture and how it’s applied in this project.

## Used Open Source Library
- **Retrofit**: For Networking work
- **Glide**: To load cloud images.

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

- After the build finishes, run it on a real Android device or install Android Studio emulator.

## Project Structure
The project follows MVVM clean architecture to separate UI and data 

![project_flow](https://github.com/user-attachments/assets/a2b44996-18fc-43d6-89e7-f4282a5ecaf9)

## How to Generate Test Report in Android Studio

You can generate test reports in Android Studio for both **Unit Tests**. Here's a step-by-step guide to generate these reports:

 **Running Unit Tests**

Unit tests are run on your local machine and are usually located in the `src/test/java` folder. Follow the steps below to run them and generate reports:

1. **Run Tests**:
   - In Android Studio, right-click on the `test` folder or any specific test class inside the folder.
   - Click on `Run 'Tests in <filename>'` from the context menu to execute the tests.

2. **View Test Results**:
   - Test results will be shown in the `Run` window at the bottom of Android Studio.
   - To view detailed reports, navigate to the `build/reports/tests/testDebugUnitTest/index.html` file in the `project` directory.
 
## App Screenshot
  ##Screen1 When we open app show simple screen to search city name for weather details, its showing all available city matched with typed text
  
   ![screen1](https://github.com/user-attachments/assets/833121f5-2d48-408d-b1d9-c3f87415425d)

    ##Screen2 Once click on any city from screen1, then navigate to screen2 for city weather details, showing current temp, and five days forecast data

   ![screen2](https://github.com/user-attachments/assets/e10f6143-9d46-4658-9c1b-1c0b6d7f7222)







