
package io.github.kuohsuanlo.orereplacer;

import java.io.File;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class OreReplacerPlugin extends JavaPlugin {
    private final OreReplacerListener ORListener = new OreReplacerListener();
    private static final Logger log = Logger.getLogger("Minecraft");

    public static int MAX_DIAMOND = 6;
    public static int MAX_GOLD = 6;
    public static int MAX_IRON = 10;
    public static int MAX_COAL = 10;
    public static int MAX_LAPIS = 4;
    public static int MAX_REDSTONE = 10;
    public static int MAX_EMERALD= 4;
    
    public static int MIN_DIAMOND = 2;
    public static int MIN_GOLD = 3;
    public static int MIN_IRON = 4;
    public static int MIN_COAL = 3;
    public static int MIN_LAPIS = 2;
    public static int MIN_REDSTONE = 4;
    public static int MIN_EMERALD= 2;
    
    public static double PROBABILITY_DIAMOND = 0.0015;
    public static double PROBABILITY_GOLD = 0.003;
    public static double PROBABILITY_IRON = 0.008;
    public static double PROBABILITY_COAL = 0.0015;
    public static double PROBABILITY_LAPIS = 0.0012;
    public static double PROBABILITY_REDSTONE = 0.010;
    public static double PROBABILITY_EMERALD= 0.0008;
    
    public static double PROBABILITY_INCREASING_CONSTANT= 1;


    public static boolean REPLACING_DIAMOND = true;
    public static boolean REPLACING_GOLD = true;
    public static boolean REPLACING_IRON = true;
    public static boolean REPLACING_COAL = true;
    public static boolean REPLACING_LAPIS = true;
    public static boolean REPLACING_REDSTONE = true;
    public static boolean REPLACINGY_EMERALD = true;
    
    public static boolean REPLACING=true;

    public static boolean ORE_PROTECTION_MODE=true;
    public static String ORE_PROTECTION_TEXT= "Your placing behavior would make the ore disappear due to server anti-xray mechanism. Please remove the ore to place the block.";
    
    //TODO:
    public boolean TEAM_WORK=true;
    public double TEAM_WORK_RADIUS= 100;
    public double TEAM_WORK_INCREASING_PER_PLAYER= 0.07;
    public double TEAM_WORK_INCREASING_MAX= 1;


    private FileConfiguration config;
    private OreReplacerCommand CommandExecutor ;

    public static ArrayList<Location> eventLocationListMining;
    public static final int EventLocationListMaxMining = 100;
    public static ArrayList<String> enabledWorld  = new  ArrayList<String> ();
    
    
    @Override
    public void onDisable() {
        // TODO: Place any custom disable code here
        // NOTE: All registered events are automatically unregistered when a plugin is disabled
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
    public void onReload(){
    	PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(ORListener, this);

    	
    	
        CommandExecutor = new OreReplacerCommand(this);
        getCommand("orereplacer").setExecutor(CommandExecutor);

         
        reloadConfig();
     	config = getConfig();
     	
     	loadConfig();
     	
     	
     	
    }
    @Override
    public void onEnable() {
    	
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(ORListener, this);
        CommandExecutor = new OreReplacerCommand(this);
        getCommand("orereplacer").setExecutor(CommandExecutor);
        
        OreReplacerPlugin.eventLocationListMining = new ArrayList<Location>();
        
    	config = getConfig();
    	config.addDefault("version","1.0.0");
    	

    	config.addDefault("ENABLED_WORLD","world,world_nether,world_the_end");
    	
    	config.addDefault("PROBABILITY_DIAMOND",PROBABILITY_DIAMOND);
    	config.addDefault("PROBABILITY_GOLD",PROBABILITY_GOLD);
    	config.addDefault("PROBABILITY_IRON",PROBABILITY_IRON);
    	config.addDefault("PROBABILITY_COAL",PROBABILITY_COAL);
    	config.addDefault("PROBABILITY_LAPIS",PROBABILITY_LAPIS);
    	config.addDefault("PROBABILITY_REDSTONE",PROBABILITY_REDSTONE);
    	config.addDefault("PROBABILITY_EMERALD",PROBABILITY_EMERALD);
    	

    	config.addDefault("MAX_DIAMOND",MAX_DIAMOND);
    	config.addDefault("MAX_GOLD",MAX_GOLD);
    	config.addDefault("MAX_IRON",MAX_IRON);
    	config.addDefault("MAX_COAL",MAX_COAL);
    	config.addDefault("MAX_LAPIS",MAX_LAPIS);
    	config.addDefault("MAX_REDSTONE",MAX_REDSTONE);
    	config.addDefault("MAX_EMERALD",MAX_EMERALD);


    	config.addDefault("MIN_DIAMOND",MIN_DIAMOND);
    	config.addDefault("MIN_GOLD",MIN_GOLD);
    	config.addDefault("MIN_IRON",MIN_IRON);
    	config.addDefault("MIN_COAL",MIN_COAL);
    	config.addDefault("MIN_LAPIS",MIN_LAPIS);
    	config.addDefault("MIN_REDSTONE",MIN_REDSTONE);
    	config.addDefault("MIN_EMERALD",MIN_EMERALD);

    	config.addDefault("PROBABILITY_INCREASING_CONSTANT",1.0);
    	
    	config.addDefault("REPLACING_DIAMOND",true);
    	config.addDefault("REPLACINGY_EMERALD",true);
    	config.addDefault("REPLACING_GOLD",true);
    	config.addDefault("REPLACING_IRON",true);
    	config.addDefault("REPLACING_COAL",true);
    	config.addDefault("REPLACING_LAPIS",true);
    	config.addDefault("REPLACING_REDSTONE",true);
    	

    	config.addDefault("ORE_PROTECTION_MODE",ORE_PROTECTION_MODE);
    	config.addDefault("ORE_PROTECTION_TEXT",ORE_PROTECTION_TEXT);
    	
    	config.options().copyDefaults(true);
    	saveConfig();
    	
    	
    	loadConfig();
    	
    	
    	
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    private void loadConfig(){
    	 
    	enabledWorld = new  ArrayList<String> ();
    	String[] world_names = config.getString("ENABLED_WORLD").split(",");
    	for(int i=0;i<world_names.length;i++){
    		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"[OreReplacer] : world name - "+world_names[i]+" added!");
    		enabledWorld.add(world_names[i]);
    	}
    	
    	PROBABILITY_DIAMOND = config.getDouble("PROBABILITY_DIAMOND");
     	PROBABILITY_GOLD = config.getDouble("PROBABILITY_GOLD");
     	PROBABILITY_IRON = config.getDouble("PROBABILITY_IRON");
     	PROBABILITY_COAL = config.getDouble("PROBABILITY_COAL");
     	PROBABILITY_LAPIS = config.getDouble("PROBABILITY_LAPIS");
     	PROBABILITY_REDSTONE = config.getDouble("PROBABILITY_REDSTONE");
     	PROBABILITY_EMERALD = config.getDouble("PROBABILITY_EMERALD");

     	MIN_DIAMOND = config.getInt("MIN_DIAMOND");
     	MIN_GOLD = config.getInt("MIN_GOLD");
     	MIN_IRON = config.getInt("MIN_IRON");
     	MIN_COAL = config.getInt("MIN_COAL");
     	MIN_LAPIS = config.getInt("MIN_LAPIS");
     	MIN_REDSTONE = config.getInt("MIN_REDSTONE");
     	MIN_EMERALD = config.getInt("MIN_EMERALD");
     	
     	MAX_DIAMOND = config.getInt("MAX_DIAMOND");
     	MAX_GOLD = config.getInt("MAX_GOLD");
     	MAX_IRON = config.getInt("MAX_IRON");
     	MAX_COAL = config.getInt("MAX_COAL");
     	MAX_LAPIS = config.getInt("MAX_LAPIS");
     	MAX_REDSTONE = config.getInt("MAX_REDSTONE");
     	MAX_EMERALD = config.getInt("MAX_EMERALD");
     	
     	PROBABILITY_INCREASING_CONSTANT = config.getDouble("PROBABILITY_INCREASING_CONSTANT");
     	
     	REPLACING_DIAMOND = config.getBoolean("REPLACING_DIAMOND");
     	REPLACING_GOLD = config.getBoolean("REPLACING_GOLD");
     	REPLACING_IRON = config.getBoolean("REPLACING_IRON");
     	REPLACING_COAL = config.getBoolean("REPLACING_COAL");
     	REPLACING_LAPIS = config.getBoolean("REPLACING_LAPIS");
     	REPLACING_REDSTONE = config.getBoolean("REPLACING_REDSTONE");
     	REPLACINGY_EMERALD = config.getBoolean("REPLACINGY_EMERALD");
     	    

     	ORE_PROTECTION_MODE = config.getBoolean("ORE_PROTECTION_MODE");
     	ORE_PROTECTION_TEXT = config.getString("ORE_PROTECTION_TEXT");
     	
     	PROBABILITY_DIAMOND = PROBABILITY_DIAMOND*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_DIAMOND+MIN_DIAMOND));
     	PROBABILITY_GOLD = PROBABILITY_GOLD*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_GOLD+MIN_GOLD));
     	PROBABILITY_IRON = PROBABILITY_IRON*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_IRON+MIN_IRON));
     	PROBABILITY_COAL = PROBABILITY_COAL*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_COAL+MIN_COAL));
     	PROBABILITY_LAPIS = PROBABILITY_LAPIS*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_LAPIS+MIN_LAPIS));
     	PROBABILITY_REDSTONE = PROBABILITY_REDSTONE*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_REDSTONE+MIN_REDSTONE));
     	PROBABILITY_EMERALD = PROBABILITY_EMERALD*PROBABILITY_INCREASING_CONSTANT*(2f/(MAX_EMERALD+MIN_REDSTONE));
     	

    }

}
