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