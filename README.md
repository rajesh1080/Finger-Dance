# Finger-Dance
This android app is created using Android studio.

How to install or run
  Download the repository
  import the repository on Android Studio.
  Run it from android studio.


Approach
  Getiing the maximux touch pointer:
    from package manager ask if FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND exhist than 5 or more simultaneous touch is possible
    from package manager ask if FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT exhist than 3 or more simultaneous touch is possible
    if FEATURE_TOUCHSCREEN_MULTITOUCH is not exhist than the phone Does not have multitouch so this app won't work on that device.
  Get the board size(n) from user
  create board of tiles. Each tile is a textview whose height and width are equal and also epual to (screenwidth / n).
  choose a random tile and set selected true.
  let the user tap on it.
    if the tapped point cordinate lies in the selected text view area user tapped correctly. now highlight another tile and process goes on
    else tapped outside so game over.
  if user lift ony of the finger game over (determinde by ACTION_UP or ACTION_POINTER_UP)
    on game over show a popup to reset game or quit.


