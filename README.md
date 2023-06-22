# MemoryGame-Android
||
|--|
|<img src="https://github.com/hall9zeha/MemoryGame-Android/blob/main/InKotlin/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" align="left"  hspace="10" vspace="10">|
|Classic memory game for Android with a theme inspired by the video game Braid.|

## Download demo 📂 [click here](https://github.com/hall9zeha/MemoryGame-Android/raw/main/demo/braid_memory_game.apk)
## It was used 🔧:
* [Arquitectura MVVM](https://developer.android.com/jetpack/guide)
* [Lyfecycle view model](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [Glide](https://developer.android.com/training/dependency-injection/hilt-android) 
* [Material components and Material Design](https://material.io/components)
* [Braid Game resources](http://davidhellman.net/braidbrief.htm)

## Requirements ⚙️

Technical challenge: Create a memory game:

The dynamics of the game consists of presenting a board with a series of cards face down and revealing all the pairs before time runs out. The user will touch each card to turn it over, and if two of them match when revealed, they will be turned face up. Otherwise, they will be hidden again.
* The game is won if all pairs are discovered within the time limit.


The game will have an initial screen to select one of the three difficulties and one minute to solve each board:
* Easy: 4x4 board
* Medium: 4x6 board
* Hard: 5x6 board

The application will have a main game screen that contains the following elements:
* Game board. The board where the user will play and see the cards
* Move counter (one every time 2 cards are turned, hit or miss)
* Timer
* Remaining pairs counter
* Action to return to the level selection screen

## Screenshots 🖼️
|||
|--|--|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/MemoryGame-Android/blob/main/screenshots/MemGame.gif"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/MemoryGame-Android/blob/main/screenshots/screen1.jpg" alt="drawing" width="70%" height="70%"/></p>|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/MemoryGame-Android/blob/main/screenshots/screen2.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/MemoryGame-Android/blob/main/screenshots/screen3.jpg"  alt="drawing" width="70%" height="70%"/></p>|
