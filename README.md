# **Smart Pantry Manager**

## Description
    This Smart Pantry Manager app is an Android application that was developed in Java to help users manage pantry ingredients, reduce food waste, and find recipes they can cook/prepare using the ingredients they already have.
    This application allows users to add, view, edit, and delete pantry ingredients. It also provides a collection of preloaded recipes that suggests recipes that appear only when all the required ingredients are available.

## Features
    •	Add ingredients with a name, quantity, unit, and optional expiry date.
    •	View and manage ingredients in the pantry.
    •	Edit existing ingredients and delete unwanted entries.
    •	Browse suggested recipes based on the current pantry/ what items are in your pantry currently.
    •	Applies strict recipe matching so recipes with missing ingredients or insufficient quantities are not showing.
    •	View recipe details and preparation instructions.
    •	Use the Settings screen to clear the entire pantry.

## Technologies used
    The language I used for this application was Java developed within the Android Studio environment. My choice for the database was SQLite over the other options. The interface was created using Android XML layouts, intents, ListView and activities. The overall version control is done via GitHub and Git

## Database choice
    SQLite was selected because it provides local, persistent storage directly on the Android device. It does not require a separate server or an internet connection to manage pantry data. The database stores ingredients, recipes, and the ingredients required for each recipe.

## Requirements
    •	Android Studio
    •	Android SDK compatible with the project's configured SDK settings
    •	An Android emulator or a compatible Android device

## Setup and run instructions
    1.	Clone or download this repository.
    2.	Open Android Studio.
    3.	Select Open and choose the downloaded Smart Pantry project folder.
    4.	Allow Android Studio to sync the Gradle files and download any required dependencies.
    5.	Select an Android emulator device or connect a compatible Android device with USB debugging enabled.
    6.	Click Run to build and launch the application.
    7.	Add ingredients to the pantry and open Suggested Recipes to test strict recipe matching.
        Testing strict recipe matching

## To test the main recipe matching feature:
    1.	Add all the ingredients required by a recipe in the necessary quantities.
    2.	Open Suggested Recipes and confirm that the recipe appears.
    3.	Remove one required ingredient or reduce its quantity below the required amount.
    4.	Return to Suggested Recipes and confirm that the recipe is no longer suggested.
    5.	Restore the required ingredients and quantities to confirm that the recipe becomes available again.

## Project purpose
    The purpose of this project is to demonstrate Android application development, persistent database storage, CRUD operations, screen navigation, input validation, and recipe matching logic while helping users make better use of the food they already have.

## Repository link
    GitHub: https://github.com/olerato-m/Smart-Pantry

