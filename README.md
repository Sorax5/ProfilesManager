<h1 align="center">
  <img width="248" height="248" src="https://raw.githubusercontent.com/Sorax5/ProfilsManagerCore/master/logo.png">
  <br>ProfilsManagerCore</br>
</h1>

<div align="center">
  <img>
  
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![](https://img.shields.io/bstats/servers/15930?label=bStats)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)
[![](https://img.shields.io/github/stars/Sorax5/ProfilsManagerCore.svg?label=Stars&logo=github)](https://github.com/Sorax5/ProfilsManagerCore/stargazers)
[![](https://img.shields.io/github/workflow/status/Sorax5/ProfilsManagerCore/Java%20CI%20with%20Maven)](https://img.shields.io/github/workflow/status/Sorax5/ProfilsManagerCore/Java%20CI%20with%20Maven)
[![CodeFactor](https://www.codefactor.io/repository/github/sorax5/profilsmanagercore/badge)](https://www.codefactor.io/repository/github/sorax5/profilsmanagercore)
</div>

ProfilsManagerCore is a Bukkit/Spigot/Paper minecraft plugin allowing players to have multiple game profiles on the same server, goodbye second account and welcome to Profiles! Only this plugin is useless it's an API so it requires addons to be useful.

When I will finish and pauffine the api I will publish some addon examples.
I plan later to port the plugins to a newer version of minecraft (1.12.2 --> 1.16.5 --> 1.19.2).

### BStats
[![](https://bstats.org/signatures/bukkit/ProfilsManagerCore.svg)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)

## Official Addon
The recent refactoring of the project has led to the incompatibility of the addons with version 2.0.
I plan to update them soon, it will be done when this message is removed from the README.
### ProfilsPlayerStatistics (WORK WITH V2.0)
https://github.com/Sorax5/ProfilsPlayerStatistics
### ProfilsVaultIntegration (DO NOT WORK YET)
https://github.com/Sorax5/ProfilsVaultIntegration

## API
Work in progress
### Maven Artifact Package

```xml
<dependency>
  <groupId>fr.soraxdubbing</groupId>
  <artifactId>profilsmanagercore</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

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
        super("PlayerStat"); // Addon name
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
        sb.append("§2gamemode:§r ").append(this.gamemode).append("\n");
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

You must specify to the API the class that implements the abstract class AddonData, otherwise the information of your addon will not be saved because the API does not recognize your addon.

```java
@Override
public void onLoad(){
    // PlayerStats.class is your AddonData
    UsersManager.getInstance().registerClass(PlayerStats.class);
}
```

#### ItemManager

This class allows you to transform items into String for serialization, the methods are static and I used them originally to save the inventories of the players, so I let you these methods at your disposal.(work with modded item)

```java
public static String ItemStackToStringByte(ItemStack itemStack)

public static ItemStack StringByteToItemStack(String encodedObject)

public static List<String> ItemStackToStringList(ItemStack[] itemStack)

public static ItemStack[] StringListToItemStack(List<String> encodedObject)
```

### JavaDocs

Work in progress

## Used By

<a href="https://github.com/Studio-Leblanc-RoadToNincraft"><img src="https://avatars.githubusercontent.com/Studio-Leblanc-RoadToNincraft" title="RoadToNincraft" width="80" height="80"></a>

## Authors

<a href="https://github.com/sorax5"><img src="https://avatars.githubusercontent.com/sorax5" title="sorax5" width="80" height="80"></a>

## Artwork
the logo was made by <a href="game-icons.net">game-icons.net</a> under the <a href="https://creativecommons.org/licenses/by/3.0/">CC BY 3.0 license</a>.


