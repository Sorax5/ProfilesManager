<div align="center">
  <img width="248" height="248" src="https://repository-images.githubusercontent.com/514954448/3d46d9d4-eacd-4af5-96a3-0c9f7bacf0e4">
  
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![](https://img.shields.io/bstats/servers/15930?label=bStats)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)
[![](https://img.shields.io/github/stars/Sorax5/ProfilsManagerCore.svg?label=Stars&logo=github)](https://github.com/Sorax5/ProfilsManagerCore/stargazers)

</div>

# ProfilsManagerCore

ProfilsManagerCore is a Bukkit/Spigot/Paper minecraft plugin allowing players to have multiple game profiles on the same server, goodbye second account and welcome to Profiles! Only this plugin is useless it's an API so it requires addons to be useful.

When I will finish and pauffine the api I will publish some addon examples.
I plan later to port the plugins to a newer version of minecraft.

[![](https://bstats.org/signatures/bukkit/ProfilsManagerCore.svg)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)

## API

Work in progress

### Exemple

#### Create an Addon

The addons allow you to save information according to the profiles, you have two functions that allow you to save and load the information of the addon if needed.

```java
public class PlayerStats extends AddonData {

    // Attribute you want to seperate by Profiles
    private int level;
    private int life;
    private Gamemode gamemode;
    
  // Constructor of the Class
  public PlayerStats(Player player) {
        super("PlayerStat") // Addon name
        this.level = player.getLevel();
        this.life = player.getMaxLife();
        this.gamemode = player.getGamemode();
    }
    
    // use in /profils command to give informations about your addon
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("§a§lPlayerStat:§r").append("\n");
        sb.append("§2level:§r ").append(this.level).append("\n");
        sb.append("§2life:§r ").append(this.life).append("\n");
        sb.append("§gamemode:§r ").append(this.gamemode).append("\n");
        return sb.toString();
    }

    // call when The actual profil is save or juste update
    @Override
    public void updateAddonData(Player player, JavaPlugin javaPlugin) {
        this.level = player.getLevel();
        this.life = player.getMaxLife();
        this.gamemode = player.getGamemode();
    }

   // call when the profils is loaded by the player
    @Override
    public void loadAddonData(Player player, JavaPlugin javaPlugin) {
        player.setMaxLife(this.life);
        player.setLevel(this.level);
        player.setGamemode(this.gamemode);
    }
}

```

#### Register your Addon

To save the addon, the plugin must know the class used, so use this event to save the class.

```java
public class RegisterAddon implements Listener {

    @EventHandler
    public void registerAddon(AddonRegisterEvent event){
        if(!event.getData().containsClass(PlayerStats.class)){
            event.registerAddon(PlayerStats.class);
            System.out.println("Registering addon");
        }
    }
}
```

#### ItemManager

This class allows you to transform items into String for serialization, the methods are static and I used them originally to save the inventories of the players, so I let you these methods at your disposal.

```java
public static String ItemStackToStringByte(ItemStack itemStack)

public static ItemStack StringByteToItemStack(String encodedObject)

public static List<String> ItemStackToStringList(ItemStack[] itemStack)

public static ItemStack[] StringListToItemStack(List<String> encodedObject)
```

### JavaDocs

Work in progress

## Official Addon

Work in progress

## Demo

Insert gif or link to demo

## Used By

<a href="https://github.com/Studio-Leblanc-RoadToNincraft"><img src="https://avatars.githubusercontent.com/Studio-Leblanc-RoadToNincraft" title="RoadToNincraft" width="80" height="80"></a>

## Authors

<a href="https://github.com/sorax5"><img src="https://avatars.githubusercontent.com/sorax5" title="sorax5" width="80" height="80"></a>

