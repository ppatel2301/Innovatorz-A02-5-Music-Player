# Architecture

This application has three layers for Presentation, Logic and Data Persistence. These layers are used to enforce
single responsibility. Each layer has its own package and directory in the repo and are named accordingly.

- presentation
- logic
- data

## 3-tier architecture

The main components in our application are organized as follows.

### Presentation Layer

- **MainActivity**: Main process in the application. Everything runs within it.

- **HomeActivity**: The home page view.

- **UserProfileActivity**: The view for any user profile (listener or artist).

- **LibraryActivity**: The view for the user's music library.

- **SettingsActivity**: The view for the app settings.

- **MusicControlsActivity**: Displays the main music player controls (play, pause, skip, replay, seek).

- **ChatActivity**: The chat feature view.

### Logic Layer

- **MusicPlayer**: Responsible of decoding music tracks from `.mp3` files.

- **BluetoothPeer**: Responsible of sending and receiving Bluetooth messages.

### Data Layer

- **Database**: Interface to a HSQL database.

- **Library**: Interface to the user's device's storage.

### Domain Specific Object

- **MusicTrack**

- **Artist**

- **Album**

- **Playlist**

## Architecture Diagram

``` mermaid

    
```
