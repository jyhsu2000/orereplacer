使用時請勿做為贊助回饋，以任何方式複製或下載碼源您即同意此規章。

做為營利手段時，願意負擔違反智慧財產連帶之法律責任。

# OreReplacer--- Powered by [LogoCat](https://mcuuid.net/?q=logocat) 
[![mcfallout](https://i.imgur.com/o6S7V07.png)](https://mcfallout.net)
Jar Download link: [Spigot Page](https://www.spigotmc.org/resources/orereplacer.22158/)

###### A zero burden plugin that ends Xray-mining forever. This plugin is a minecraft server-side spigot plugin in order to enhance the experience of:
  - Prevent any method of X-Ray mining 100%.
  - Consume theoratically least network traffic and computational effort.
  - Do not affect any normal player
###### This plugin will dynamically generate the ore and stone block that is not connect to the air or water when they get broken. ![](https://www.csie.ntu.edu.tw/~b98902055/items/1-0.png) ---> ![](https://www.csie.ntu.edu.tw/~b98902055/items/16-0.png) ![](https://www.csie.ntu.edu.tw/~b98902055/items/15-0.png) ![](https://www.csie.ntu.edu.tw/~b98902055/items/14-0.png) ![](https://www.csie.ntu.edu.tw/~b98902055/items/56-0.png)  ![](https://www.csie.ntu.edu.tw/~b98902055/items/129-0.png)  ![](https://www.csie.ntu.edu.tw/~b98902055/items/73-0.png)  ![](https://www.csie.ntu.edu.tw/~b98902055/items/21-0.png)  ![](https://www.csie.ntu.edu.tw/~b98902055/items/153-0.png) 
###### So X-Ray players would think they are getting close to the block. But after they break the last stone. They would find the ores disappear.  ![](https://www.csie.ntu.edu.tw/~b98902055/items/56-0.png)  ---> ![](https://www.csie.ntu.edu.tw/~b98902055/items/1-0.png) 
---------

  ### Features : 
  Enhanced mining mechanism : 
    1. Experience : The probability and ore distribution is based on minecraft vanilla settings. Normal player won't notice the transformation.
    2. Efficiency : Blocks are generated only when players reveal them. No excessive fake ores transimitted. 
    3. Configurable : All of the ores probability and distribution could be set and revised immediately.
    4. Extendable : Tested on 70 players server, less than 0.1% TPS occupation.

Media Demo:
  - [Youtube link](https://www.youtube.com/watch?v=9WqhJXAGvYQ) : Ore got replaced as X-ray player mining.

----
### Environment 
This build is compiled and tested on these environments.
* [java] - JVM 1.8
* [spigot] - 1.12.x

### Hard-Dependency
This plugin needs to run with the following plugins with the latest version to work properly:
* none.
----
### Installation
1. Drop the plugin jar file in your server folder /plugins/ and run once.
2. After the plugin folder and default config.yml is generated, stop the server.
3. Start to set your own config withing config.yml.

### Configuration setting
```sh
ENABLED_WORLD: world,world_nether,world_the_end
PROBABILITY_DIAMOND: 0.001
# 0.001 means that when a player mine 1000 blocks, the player would "probably" get 1 diamond. (Expected value.)
MAX_DIAMOND: 4
#What is the maximum number blocks of the ore veins.
PROBABILITY_INCREASING_CONSTANT: 1.0
#You could change this value and hold a mining bonus time!.
#This number will multiply the probability of ore generation.
REPLACING_DIAMOND: true
# true equals replacing such ores.
# false equals leaving such ores as their original position.
#Other ore blocks have default setting. Just follow the logic above.
```
If you don't have any idea how to set the configuration.
Just use the config setting right [here](https://pastebin.com/5xqJGhXn). It is well tuned to match the gaming experience of vanilla minecraft. 
----
### Commands
| command |description| required permission |
| ------ | ------ |---|
| /orereplacer reload | player with his node could reload the plugin's config. | op |

----
### Development

Want to contribute? Great!
This project is open to everyone as long as it follows the [license]. You could follow these steps to build up the developing environment : 
1. Click [here](https://stackoverflow.com/questions/2061094/importing-maven-project-into-eclipse) for instrctions of importing a maven project.
2. Add the dependent plugin .jar file mentioned above. 
3. Run the project as 'maven install'
4. The built version would be in /$project_name/target/


----
### License
Term of Service
PERSONAL AND NON-COMMERCIAL USE LIMITATION

Unless otherwise agreed in a separate written document signed by an authorized representative of mcfallout.net, the plugin and its usage are for your personal and non-commercial use. You may not modify, copy, distribute, transmit, display, perform, reproduce, publish, license, create derivative works for providing a pay-to-win mechanism or providing special powers / perks as a return for donation or payment.

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [item]: <https://www.csie.ntu.edu.tw/~b98902055/items/>

   [vault]: <https://www.spigotmc.org/resources/vault.41918/>
   [multiverse-core]: <https://www.spigotmc.org/resources/multiverse-core.390/>
   [faction]: <https://www.spigotmc.org/resources/factions.1900/>
   [griefprevention]: <https://www.spigotmc.org/resources/griefprevention.1884/>
   [worldedit]: <https://dev.bukkit.org/projects/worldedit/files/2460562>
   [placeholderapi]: <https://www.spigotmc.org/resources/placeholderapi.6245/>
   [titlemanager]: <https://www.spigotmc.org/resources/titlemanager.1049/>
   [spigot]: <https://spigotmc.org>
   [java]: <https://java.com/zh_TW/>
   [license]: <https://opensource.org/licenses/MIT>

