# Zepp Band7 OpenSDK 
***Zepp Band7 OpenSDK*** is open source SDK ro developing mini applications for Xiaomi Band 7.
Actually ***Zepp Band7 OpenSDK*** is just a wrapper over [Zmake](https://melianmiko.ru/en/zmake/guide/) and [zepp_player](https://melianmiko.ru/en/zepp_player) but downloading them binaries automatically and making comfortable integration between them.

## Usage
1. Download the [latest binaries](https://github.com/lavafrai/zeppBand7openSdk/releases/latest/)
2. Run application `java -jar zb7o-sdk.jar [Target]`

#### Possible targets:
1. i [directory?] - *INIT* generate new project template inside this directory.
2. b [directory?] - *BUILD* build your project. Result file will appear in dist directory.
3. r [directory?] - *RUN* build your project and run built application in emulator.
4. s - *STOP* Stopping already started emulation.
5. [.bin file] - *DECOMPILE* unpack .bin of application into current directory.

If `directory` parameter unspecified or specified incorrect, using `current working directory`. Be careful.

## User guide:
1. Install java 1.8+ 
2. Download the [latest binaries](https://github.com/lavafrai/zeppBand7openSdk/releases/latest/) jar file
3. Init project `java -jar ZeppBand7OpenSDK.jar i <path_to_empty_directory>`
4. ~~Do some magic~~
5. Write code of your app
6. Run app in emulator `java -jar ZeppBand7OpenSDK.jar r <path_to_project_directory>`
7. Debug app in browser
8. Stop emulator `java -jar ZeppBand7OpenSDK.jar s`
9. Build .bin file of app `java -jar ZeppBand7OpenSDK.jar b <path_to_project_directory>`
10. Install .bin file on your Mi Band 7

Also, you always can display help `java -jar ZeppBand7OpenSDK.jar --help`

## Realisation progress
 - [x] Basic application
 - [x] Auto download binaries
 - [x] Basic integration with ZMake
 - [x] Project initiation
 - [x] Project compilation
 - [ ] Project preprocessing with ESBuild (with zmake)
 - [x] Basic integration with zepp-player 
 - [x] Project running in emulator
 - [ ] Bin files decompilation

<a href="https://www.donationalerts.com/r/lavafrai">
<p align="center">
<br>
<img src="https://github.com/appcraftstudio/buymeacoffee/raw/master/Images/snapshot-bmc-button.png" width="300" alt="Buy me a coffee">
</p>
</a>

## Acknowledgements:
1. [Melianmiko](https://github.com/melianmiko) for his programs [Zmake](https://github.com/melianmiko/zmake) and [zepp_player](https://github.com/melianmiko/ZeppPlayer) 
2. [mr. Unknown](https://discordapp.com/users/619212304403398675) for moral support 