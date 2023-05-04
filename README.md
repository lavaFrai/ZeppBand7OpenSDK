# Zepp Band7 OpenSDK 
***Zepp Band7 OpenSDK*** is open source SDK ro developing mini applications for Xiaomi Band 7.
Actually ***Zepp Band7 OpenSDK*** is just a wrapper over [Zmake](https://melianmiko.ru/en/zmake/guide/) and [zepp_player](https://melianmiko.ru/en/zepp_player) but downloading them binaries automatically and making comfortable integration between them.

## Usage
1. Download the [latest binaries](https://github.com/lavafrai/zeppBand7openSdk/releases/latest/)
2. Run application `java -jar zb7o-sdk.jar [Target]`

#### Possible targets:
1. i [directory?] - INIT generate new project template inside this directory.
2. b [directory?] - BUILD build your project. Result file will appear in dist directory.
3. r [directory?] - RUN build your project and run built application in emulator.
4. d [.bin file] - DECOMPILE unpack .bin of application into current directory.

If `directory` parameter unspecified or specified incorrect, using `current working directory`. Be careful.
## Realisation progress
 - [x] Basic application
 - [ ] Auto download binaries
 - [x] Basic integration with ZMake
 - [x] Project initiation
 - [ ] Project compilation
 - [ ] Project preprocessing with ESBuild (with zmake)
 - [ ] Basic integration with zepp-player 
 - [ ] Project running in emulator

<a href="https://www.donationalerts.com/r/lavafrai">
<p align="center">
<br>
<img src="https://github.com/appcraftstudio/buymeacoffee/raw/master/Images/snapshot-bmc-button.png" width="300">
</p>
</a>