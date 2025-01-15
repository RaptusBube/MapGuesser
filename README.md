MapGuesser is a Minecraft plugin that adds a fun and interactive guessing game to your server! Players start in a lobby, choose one of eight maps by pressing a button, and then guess their location on the selected map. The plugin is fully customizable, allowing you to configure spawn points, teleport buttons, and gameplay settings directly through the configuration file.

📥 Installation

    Download the latest version of the MapGuesser plugin from the Releases page.
    Place the .jar file into your server's plugins directory.
    Restart or reload your server to generate the default configuration files.
    Configure the plugin to your liking (see the Configuration section).
    Start your server, and enjoy!

🎮 How to Play

    Start in the Lobby
    The player spawn in the central lobby. Here, they will see eight buttons, each corresponding to one of the maps.
    Currently only Singleplayermode is supported

    Choose a Map
    Press one of the buttons to teleport to the respective map.

    Guess the Location
    Explore the map (if movement is enabled) and guess the location. Each map can have specific settings to allow or restrict movement.

    Return to the Lobby
    After a predefined amount of time, players are automatically teleported back to the lobby to choose a new map or start another round.

⚙️ Configuration

All aspects of the plugin can be customized via the configuration file (config.yml). Here’s an overview of what you can configure:
General Settings

    Lobby spawn location: Define where players spawn in the lobby.
    Map teleport button locations: Set the position of the buttons that teleport players to maps.

Map-Specific Settings

    Spawn locations: Define where players spawn when teleported to a map.
    Movement: Enable or disable player movement for specific maps. Useful for well-known or smaller locations.
    Timer: Set the duration players stay on a map before being teleported back to the lobby.

General Example config.yml:

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