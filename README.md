<h1 align="center">
  <img width="248" height="248" src="https://raw.githubusercontent.com/Sorax5/ProfilsManagerCore/master/logo.png">
  <br>ProfilesManager</br>
</h1>

<div align="center">
  <img>
  
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![](https://img.shields.io/bstats/servers/15930?label=bStats)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)
[![](https://img.shields.io/github/stars/Sorax5/ProfilesManager.svg?label=Stars&logo=github)](https://github.com/Sorax5/ProfilesManager/stargazers)
[![CodeFactor](https://www.codefactor.io/repository/github/sorax5/profilesmanager/badge)](https://www.codefactor.io/repository/github/sorax5/profilesmanager)
![](https://img.shields.io/badge/environment-server-orangered?style=flat-square)
</div>

ProfilesManager is a Bukkit/Spigot/Paper minecraft plugin allowing players to have multiple game profiles on the same server, goodbye second account and welcome to Profiles! Only this plugin is useless it's an API so it requires addons to be useful.

### BStats
[![](https://bstats.org/signatures/bukkit/ProfilsManagerCore.svg)](https://bstats.org/plugin/bukkit/ProfilsManagerCore/15930)

## Official Addon
### ProfilesPlayerStatistics
https://modrinth.com/plugin/profilsplayerstatistics

### ProfilesManagerCommands
https://modrinth.com/plugin/profilesmanagercommands

## API
### Maven Artifact Package

```xml
<repository>
 <id>jitpack.io</id>
 <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
 <groupId>com.github.Sorax5</groupId>
 <artifactId>ProfilesManager</artifactId>
 <version>Tag</version>
</dependency>
```

### Gradle Artifact Package

```kotlin
repositories {
	maven { url 'https://jitpack.io' }
}
```

```kotlin
dependencies {
	implementation 'com.github.Sorax5:ProfilesManager:Tag'
}
```

### Exemples
#### Create an Addon

The addons allow you to save information according to the profiles, you have two functions that allow you to save and load the information of the addon if needed.

[PlayerStats class](./statistics/src/main/java/fr/soraxdubbing/profilesplayerstatistics/PlayerStats.java)

#### Register your Addon

You must specify to the API the class that implements the abstract class AddonData, otherwise the information of your addon will not be saved because the API does not recognize your addon.

```java
@Override
public void onLoad(){
    // PlayerStats.class is your AddonData
    UsersManager.getInstance().registerClass(PlayerStats.class);
}
```

### JavaDocs

Work in progress

## Used By

<a href="https://github.com/Studio-Leblanc-RoadToNincraft"><img src="https://avatars.githubusercontent.com/Studio-Leblanc-RoadToNincraft" title="RoadToNincraft" width="80" height="80"></a>

## Authors

<a href="https://github.com/sorax5"><img src="https://avatars.githubusercontent.com/sorax5" title="sorax5" width="80" height="80"></a>

## Artwork
the logo was made by <a href="game-icons.net">game-icons.net</a> under the <a href="https://creativecommons.org/licenses/by/3.0/">CC BY 3.0 license</a>.


