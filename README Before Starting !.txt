Author: Joshua Hollenbeck Woodbury University 

Description : the ArdaGlass App aims to allow developers of arduino to view serial data sent over bluetooth from their arduino to google glass handset so you can monitor your arduino without hooking up a screen or using the pesky serial monitor you can openly modify both the arduino and android codes to make it more flexible to your needs my app is currently designed for one piece of serial data
 
Make sure you have all things needed to make this project work

Physical Items needed

1 FAVI bluetooth Keyboard - http://www.amazon.com/Favi-FE02BT-BL-Bluetooth-Keyboard-Android/dp/B00904PU7K/ref=sr_1_1?ie=UTF8&qid=1398587174&sr=8-1&keywords=favi+bluetooth+keyboard

2 Bluetooth Mate silver/gold for arduino - https://www.sparkfun.com/products/12576

3 Arduino UNO / any arduino compatable board

4 Google glass Handset 

Software Installation steps

1 sideload the SETTINGS.APK file included in the zip 

2 sideload Bluetooth Manager.APK file included in the zip

3 Upload the example sketch or use your own NOTE: if you use your own make sure you keep the nescesary declarations and librarys included in your sketch 

4 NOTE: if this is not your first time sideloading apps to google glass skip to step 5 other wise you will need to manually install the drivers for google glass on to your computer( NOTE: CURRENTLY ONLY WORKS FOR WINDOWS 7) refer to this link for instructions: --> (http://stackoverflow.com/questions/16928983/google-glass-adb-devices-doesnt-find-omap4430-driver-not-installed-cant-find)

5 you will now need to pair the FAVI keyboard to the google glass you can do this by launching the glass launcher and running the setting app go to the bluetooth settings and pair follow the on screen instructions untill its paired

6 once the keyboard is paired you need to click on the keyboard int he bluetooth settings to tell google glass to connect to it other wise you will not be able to type 

7 once the keyboard works you now need to pair the arduino you can do the same steps above but use the keyboard to enter the security code for the bluetooth mate NOTE: the default code is 1234 then hit enter and you are done pairing 

8 once both devices have been paired you can now launch the ArdaGlass Application and view your arduino's serial data

9 You can modify the android and arduino sketches any way you want if you would like to support more data feeds or multiple pieces of serial data. 

10 Enjoy =)