# Get Weather_DS

This is a maven project to get weather information by using API from [NATIONAL WEATHER SERVICE](https://www.weather.gov/documentation/services-web-api)

##Use case:

Given a list of three geographic US lat / long values in a text file, in this format (you should manually put these three lines into the text file with a text editor):


38.2527° N, 85.7585° W

42.3601° N, 71.0589° W

39.1031° N, 84.5120° W


Write a program that:

Uses this free weather service to get weather data about each location:

https://www.weather.gov/documentation/services-web-api

Extract the "temperature" value for "Wednesday Night" for each location

Writes out the results to a local text file, in this format:

72, 68, 70

## Demo
### Input file:
![Input Image](https://github.com/xu9449/getWeather_DataSociety/blob/master/GetWeatherDemo/img/input.png).   
### Console: 
![Console log](https://github.com/xu9449/getWeather_DataSociety/blob/master/GetWeatherDemo/img/console.png).    
### Output file:
![Output Image](https://github.com/xu9449/getWeather_DataSociety/blob/master/GetWeatherDemo/img/output.png)
