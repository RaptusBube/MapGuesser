MapGuesser Minecraft Plugin

MapGuesser is a Minecraft plugin that adds a fun and interactive guessing game to your server! Players start in a lobby, choose one of eight maps, and
then guess their location on the selected map. The plugin now includes support for multiple teams, spectators, simultaneous games on a single map, and
various gameplay enhancements.

### 📥 Installation

    Download the latest version of the MapGuesser plugin from the Releases page.
    Place the .jar file into your server's plugins directory.
    Restart or reload your server to generate the default configuration files.
    Configure the plugin to your liking (see the Configuration section).
    Start your server, and enjoy!

### 🎮 How to Play

    Start in the Lobby
    Players spawn in the central lobby. Here, they will see eight buttons, each corresponding to one of the maps. Players can also manage their teams and start games directly through the lobby.

    Join a Team or Spectate
        Use /mg join <ID> to join a specific team.
            The <ID> is optional; without it, you'll join the default team.
        Use /mg sjoin to join as a spectator. Spectators will be in Spectator mode during the game.
        Use /mg leave to leave the game at any time.

    Choose a Map
    Press one of the buttons in the lobby or use the inventory-based map selector to teleport to the respective map.

    Guess the Location
    Explore the map (if movement is enabled) and guess the location. Multiple games can run simultaneously on a single map, so teamwork and communication are key!

    End or Return Early
    If you guess the location faster than the time limit, use the hotbar item to return to the lobby immediately. Otherwise, you’ll be teleported back at the end of the timer.

    Game Start and End Countdown
    The game features start and end countdowns, complete with sounds to enhance the experience.

### ⚙️ Configuration

All aspects of the plugin can be customized via the configuration file (config.yml). Here’s an overview of what you can configure:
General Settings

    Lobby spawn location: Define where players spawn in the lobby.
    Map teleport button locations: Set the position of the buttons that teleport players to maps.

#### Map-Specific Settings

    Spawn locations: Define where players spawn when teleported to a map.
    Movement: Enable or disable player movement for specific maps. Useful for well-known or smaller locations.
    Timer: Set the duration players stay on a map before being teleported back to the lobby.

#### Teams and Spectators

    Configure team options, including default team settings and spectator behavior.
    Hide players not in a team: Players who are not in any team will be hidden from others who are in a team. This helps create a more immersive experience where only team members can see each other.

#### Example configuration:

    ### EXAMPLE
    lobby/spawn: WORLDNAME|X|Y|Z|YAW|PITCH
    map1/spawn: WORLDNAME|X|Y|Z|YAW|PITCH
    map1/button: WORLDNAME|X|Y|Z|
    map1/time: INT TIME
    map1/move: BOOL Movement
    ###
    
    lobby/spawn: world|-54.5|110|-26.5|-90|0
    map1/spawn: world|-47.5|110|-33.5|-90|0
    map1/button: world|-50|110|-34
    map1/time: 20
    map1/move: true
    map2/spawn: world|-47.5|110|-31.5|-90|0
    map2/button: world|-50|110|-32
    map2/time: 20
    map2/move: true
    map3/spawn: world|-47.5|110|-29.5|-90|0
    map3/button: world|-50|110|-30
    map3/time: 20
    map3/move: true
    map4/spawn: world|-47.5|110|-27.5|-90|0
    map4/button: world|-50|110|-28
    map4/time: 20
    map4/move: true
    map5/spawn: world|-47.5|110|-25.5|-90|0
    map5/button: world|-50|110|-26
    map5/time: 20
    map5/move: true
    map6/spawn: world|-47.5|110|-23.5|-90|0
    map6/button: world|-50|110|-24
    map6/time: 20
    map6/move: true
    map7/spawn: world|-47.5|110|-21.5|-90|0
    map7/button: world|-50|110|-22
    map7/time: 20
    map7/move: true
    map8/spawn: world|-47.5|110|-19.5|-90|0
    map8/button: world|-50|110|-20
    map8/time: 20
    map8/move: false

### 📋 Commands and Permissions

Commands:

    /mg join [ID] - Join a team (default or specified by <ID>).
    /mg sjoin - Join the game as a spectator.
    /mg leave - Leave the game.
    /mg reload - Reloads the plugin configuration without restarting the server.

### 🛠️ Features

    Customizable Lobby and Maps: Fully configurable spawn points and button locations.
    Team and Spectator Support: Join teams or spectate games with commands or inventory.
    Simultaneous Games: Run multiple games on the same map without interference.
    Movement Restrictions: Enable or disable player movement on specific maps.
    Automatic Teleportation: Players are automatically returned to the lobby after a defined period or with a hotbar item.
    Sound Effects and Countdown Timers: Enhanced gameplay experience with start and end countdowns.

### 🚀 Future Features

Planned updates may include:

    Score tracking and leaderboards.
    Multiplayer competition modes.
    More detailed customization options.

### 📧 Support

For issues, feature requests, or general support, feel free to open an issue on this repository or contact the plugin author.

### 🏷️ License

This plugin is distributed under the MIT License.

Enjoy hosting MapGuesser on your server! 🎮