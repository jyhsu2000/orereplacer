package io.github.kuohsuanlo.orereplacer;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class OreReplacerUtil {
	public static Random rand = new Random();
	public static int[] res = new int[6];
	public static BlockFace[] checkedFaces = {BlockFace.DOWN,BlockFace.UP,BlockFace.EAST,BlockFace.WEST,BlockFace.NORTH,BlockFace.SOUTH};
	public static Material[] UndergroudBlocks = {
		Material.STONE,
		Material.GRANITE,
		Material.DIORITE,
		Material.ANDESITE,
		Material.GRAVEL,
		Material.DIRT,
		Material.CLAY,
		Material.LAVA,
		Material.BEDROCK,
		Material.COAL_ORE,
		Material.IRON_ORE,
		Material.GOLD_ORE,
		Material.DIAMOND_ORE,
		Material.EMERALD_ORE,
		Material.REDSTONE_ORE,
		Material.LAPIS_ORE
	};
	public static int currentIdxDamage=0;
	public static int currentIdx=0;
	
	public static int[] generateRandomPermutation() {
	    for (int i = 0; i < 6; i++) {
	        int d = rand.nextInt(i+1);
	        res[i] = res[d];
	        res[d] = i;
	    }
	    return res;
	}


	public static boolean isPlacableLocation(Block block){

		if(isUndergroundBlock(block)){
			for(BlockFace face : checkedFaces){
				Block candidate = block.getRelative(face);
				
				BlockFace originalFace = face.getOppositeFace();
				
				if(candidate!=null &&  isOre(candidate)){
					int coveredBlockNumber = 0;
					for(BlockFace facesOfAdj : checkedFaces){
						if(facesOfAdj!=originalFace){
							Block adjBlock = candidate.getRelative(facesOfAdj);
							
							if(adjBlock.getType().equals(Material.AIR)){
								break;
							}
							
							for(Material undergroud : UndergroudBlocks){
								//System.out.println(adjBlock.getType().name()+"/"+facesOfAdj+"/"+adjBlock.getLocation().toString()+"/"+undergroud.name()+"/"+coveredBlockNumber);
								if(adjBlock.getType().equals(undergroud)){
									coveredBlockNumber++;
									break;
								}
								
							}
							if(coveredBlockNumber==5){
								return false;
							}
							
						}
					}
				}
			}
			return true;
		}
		else{
			return true;
		}
		
		
	}
	

	public static boolean isBearOre(Block block){
		if(isOre(block)  &&  isValidLocation(block.getLocation())){
			
			boolean isCurrentBearOre = false;
			for(BlockFace face : checkedFaces){
				Block candidate = block.getRelative(face);
				
				if(candidate!=null &&  candidate.getType().equals(Material.AIR)){
					isCurrentBearOre = true;
				}
				
			}
			if(isCurrentBearOre){
				return true;
			}
		}
		return false;
		
    }
	
	public static void handleBearOre(Block block,Material oreType,int remainedLength){
	
		if(remainedLength<=0) return;
		
		for(BlockFace face : checkedFaces){
			Block candidate = block.getRelative(face);
			
			
			if(isValidLocation(block.getLocation())  &&  candidate!=null &&  candidate.getType().equals(oreType)){
				//System.out.println(oreType.name()+" : remainedLength : "+remainedLength);
				attemptAddingValidLocation(block.getLocation());
				handleBearOre(candidate,oreType,remainedLength-1);
			}
		}
			
	}
	
	public static boolean isValidLocation(Location location){
		if(location.getBlockY()>70) return false;
    	for(int i=0;i<OreReplacerPlugin.eventLocationListMining.size();i++){
    		if(OreReplacerPlugin.eventLocationListMining.get(i).getWorld().getName().equals(location.getWorld().getName())  &&
    				OreReplacerPlugin.eventLocationListMining.get(i).distance(location)<0.01){
    			return false;
    		}
    	}
    	
    	
		return true;
    }
	public static boolean attemptAddingValidLocation(Location location){
		if(location.getBlockY()>70) return false;
    	for(int i=0;i<OreReplacerPlugin.eventLocationListMining.size();i++){
    		if(OreReplacerPlugin.eventLocationListMining.get(i).getWorld().getName().equals(location.getWorld().getName())  &&
    				OreReplacerPlugin.eventLocationListMining.get(i).distance(location)<0.01){
    			return false;
    		}
    	}
    	
    	
    	if(OreReplacerPlugin.eventLocationListMining.size()<OreReplacerPlugin.EventLocationListMaxMining){
			OreReplacerPlugin.eventLocationListMining.add(location);
			return true;
		}
		else{
			OreReplacerPlugin.eventLocationListMining.set(currentIdx, location);
			currentIdx++;
			currentIdx%=OreReplacerPlugin.EventLocationListMaxMining;
			return true;
		}
    }
    public static boolean isUndergroundBlock(Block block){
    	for(Material undergroud : UndergroudBlocks){
			if(block.getType().equals(undergroud)){
				return true;
			}
    	}
    	return false;
    }
    public static boolean isUndergroundNonOreBlock(Block block){
    	if(OreReplacerPlugin.REPLACING == true){
        	if( block.getType().equals(Material.STONE)  ||  
    			block.getType().equals(Material.GRANITE)  ||  
				block.getType().equals(Material.DIORITE)  ||  
				block.getType().equals(Material.ANDESITE)  ||  
    			block.getType().equals(Material.DIRT) ||
        		block.getType().equals(Material.GRAVEL) ||
        		block.getType().equals(Material.CLAY)||
        		block.getType().equals(Material.LAVA) ){
        		return true;
        	}
    	}
    	return false;
    }
    public static boolean isOre(Block block){
    	if(OreReplacerPlugin.REPLACING == true){
        	if( block.getType().equals(Material.DIAMOND_ORE) && OreReplacerPlugin.REPLACING_DIAMOND ||   
        			block.getType().equals(Material.EMERALD_ORE) && OreReplacerPlugin.REPLACINGY_EMERALD  ||   
        			block.getType().equals(Material.LAPIS_ORE) && OreReplacerPlugin.REPLACING_LAPIS  ||    
        			block.getType().equals(Material.REDSTONE_ORE) && OreReplacerPlugin.REPLACING_REDSTONE  ||    
        			block.getType().equals(Material.GOLD_ORE) && OreReplacerPlugin.REPLACING_GOLD  ||   
        			block.getType().equals(Material.IRON_ORE) && OreReplacerPlugin.REPLACING_IRON  ||   
        			block.getType().equals(Material.COAL_ORE) && OreReplacerPlugin.REPLACING_COAL 
            			){
            		return true;
            	}
    	}

    	return false;
    }
    public static void replaceOreToUndergroudBlock(Block block){
    	if(OreReplacerPlugin.REPLACING == true){
        	if( block.getType().equals(Material.DIAMOND_ORE) && OreReplacerPlugin.REPLACING_DIAMOND ||   
        			block.getType().equals(Material.EMERALD_ORE) && OreReplacerPlugin.REPLACINGY_EMERALD  ||   
        			block.getType().equals(Material.LAPIS_ORE) && OreReplacerPlugin.REPLACING_LAPIS  ||    
        			block.getType().equals(Material.REDSTONE_ORE) && OreReplacerPlugin.REPLACING_REDSTONE  ||    
        			block.getType().equals(Material.GOLD_ORE) && OreReplacerPlugin.REPLACING_GOLD  ||   
        			block.getType().equals(Material.IRON_ORE) && OreReplacerPlugin.REPLACING_IRON  ||   
        			block.getType().equals(Material.COAL_ORE) && OreReplacerPlugin.REPLACING_COAL 
            			){
        		
        		block.setType(Material.STONE);
            }
    	}

    }
    public static boolean isValidWorld(World world){
    	for(int i=0;i<OreReplacerPlugin.enabledWorld.size();i++){
    		if(world.getName().equals(OreReplacerPlugin.enabledWorld.get(i))){
    			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"[OreReplacer] : world name : "+world.getName()+" enabled!");
    			return true;
    		}
    		else{
    			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"[OreReplacer] : world name : "+world.getName()+"/"+OreReplacerPlugin.enabledWorld.get(i));		
    		}
    	}
    	return false;
    }
    
	public static int getOreNumber(Material m){
		int maxNumber =1;
		int minNumber = 1;
		if(m.equals(Material.DIAMOND_ORE)){
			maxNumber= OreReplacerPlugin.MAX_DIAMOND;
			minNumber= OreReplacerPlugin.MIN_DIAMOND;
		}
		if(m.equals(Material.EMERALD_ORE)){
			maxNumber= OreReplacerPlugin.MAX_EMERALD;
			minNumber= OreReplacerPlugin.MIN_EMERALD;
		}
		if(m.equals(Material.LAPIS_ORE)){
			maxNumber=OreReplacerPlugin.MAX_LAPIS;
			minNumber=OreReplacerPlugin.MIN_LAPIS;
		}
		if(m.equals(Material.REDSTONE_ORE)){
			maxNumber= OreReplacerPlugin.MAX_REDSTONE;
			minNumber= OreReplacerPlugin.MIN_REDSTONE;
		}
		if(m.equals(Material.GOLD_ORE)){
			maxNumber= OreReplacerPlugin.MAX_GOLD;
			minNumber= OreReplacerPlugin.MIN_GOLD;
		}
		if(m.equals(Material.IRON_ORE)){
			maxNumber= OreReplacerPlugin.MAX_IRON;
			minNumber= OreReplacerPlugin.MIN_IRON;
		}
		if(m.equals(Material.COAL_ORE)){
			maxNumber= OreReplacerPlugin.MAX_COAL;
			minNumber= OreReplacerPlugin.MIN_COAL;
		}
		
		int oreNumber = (int) Math.round(Math.random()*(maxNumber-minNumber))+minNumber;
		if(oreNumber==0) oreNumber=1; 
		
		return oreNumber;
	}
	public static void replaceRemainedOre(Block oriBlock){
		int oreNumber = getOreNumber(oriBlock.getType())-1;
		findOreCandidates(oriBlock,oreNumber);
	}
	private static void findOreCandidates(Block oriBlock, int remainedOre){
		if(remainedOre==0){
			return;
		}
		else{
			double x = oriBlock.getLocation().getBlockX();
	    	double y = oriBlock.getLocation().getBlockY();
	    	double z = oriBlock.getLocation().getBlockZ();
	    	ArrayList<Block> blockList = new ArrayList<Block>();
	    	blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x+1,y,z)));
			blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x-1,y,z)));
			blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x,y+1,z)));
			blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x,y-1,z)));
			blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x,y,z+1)));
			blockList.add(oriBlock.getWorld().getBlockAt(new Location(oriBlock.getWorld(),x,y,z-1)));
			int[] randIdx = OreReplacerUtil.generateRandomPermutation();
			for(int j=0;j<blockList.size();j++){
	    		Block blockAdj = blockList.get(randIdx[j]);
	    		if(isOre(blockAdj)  ||  isUndergroundNonOreBlock(blockAdj)  &&  isCoverByUndergoundBlock(blockAdj) ){  
	    				blockAdj.setType(oriBlock.getType());
		    			attemptAddingValidLocation(blockAdj.getLocation().add(1,0,0));
		    			attemptAddingValidLocation(blockAdj.getLocation().add(-1,0,0));
		    			attemptAddingValidLocation(blockAdj.getLocation().add(0,1,0));
		    			attemptAddingValidLocation(blockAdj.getLocation().add(0,-1,0));
		    			attemptAddingValidLocation(blockAdj.getLocation().add(0,0,1));
		    			attemptAddingValidLocation(blockAdj.getLocation().add(0,0,-1));
	    				findOreCandidates(blockAdj,remainedOre-1);
		    			break;
    			}
    		}
	    	
		}
			
		
	}
	
	
	
	public static void hideOre(Block block,int radius){
    	double x = block.getLocation().getBlockX();
    	double y = block.getLocation().getBlockY();
    	double z = block.getLocation().getBlockZ();
    	ArrayList<Block> blockList = new ArrayList<Block>();

    	for(int dx=-1;dx<=1;dx++){
    		for(int dy=-1;dy<=1;dy++){
    			for(int dz=-1;dz<=1;dz++){
    				blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x+dx,y+dy,z+dz)));
    	    	}
    		}
    	}
    	for(int dx=-radius;dx<=radius;dx++){
    		blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x+dx,y,z)));
    	}
		for(int dy=-radius;dy<=radius;dy++){
			blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y+dy,z)));
		}
		for(int dz=-radius;dz<=radius;dz++){
			blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y,z+dz)));
    	}
		
		
    	for(int i=0;i<blockList.size();i++){
    		if(isOre(blockList.get(i))  ||  isUndergroundNonOreBlock(blockList.get(i))){
    			if( isCoverByUndergoundBlock(blockList.get(i)) ){  
    				
    				if(isValidLocation(blockList.get(i).getLocation())){
    					blockList.get(i).setType(Material.STONE);
    				}
    			}
    		}
    	}
    }

	public static void replaceAllOreToStone(Block block){
		if(block.getType().equals(Material.COAL_ORE)) block.setType(Material.STONE);
		if(block.getType().equals(Material.DIAMOND_ORE)) block.setType(Material.STONE);
		if(block.getType().equals(Material.EMERALD_ORE)) block.setType(Material.STONE);
		if(block.getType().equals(Material.IRON_ORE)) block.setType(Material.STONE);
		if(block.getType().equals(Material.LAPIS_ORE)) block.setType(Material.STONE);
		if(block.getType().equals(Material.REDSTONE_ORE)) block.setType(Material.STONE);
	}
	public static void replaceFirstOre(Block block){
    	double x = block.getLocation().getBlockX();
    	double y = block.getLocation().getBlockY();
    	double z = block.getLocation().getBlockZ();
    	ArrayList<Block> blockList = new ArrayList<Block>();
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x+1,y,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x-1,y,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y+1,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y-1,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y,z+1)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y,z-1)));
    	
    	/*
    	 * All six nearby blocks should be marked as dirty. Otherwise the remaining ores would be removed,
    	 * as soon as the first block is break.  (only if it's an ore block)
    	 */
    	for(int i=0;i<blockList.size();i++){
    		if(isOre(blockList.get(i))  ||  isUndergroundNonOreBlock(blockList.get(i))){
    			if( isCoverByUndergoundBlock(blockList.get(i)) ){  
	    		
	    			boolean isReplacedByOre = false;
					if(isDiamond(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isEmerald(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isLapis(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isGold(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isRedStone(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isIron(blockList.get(i))){
						isReplacedByOre = true;
					}
					else if(isCoal(blockList.get(i))){
						isReplacedByOre = true;
					}
					
					if(isReplacedByOre){
						if(attemptAddingValidLocation(blockList.get(i).getLocation()))
							replaceRemainedOre(blockList.get(i));
					}
    			}
    		}
    	}
    }

    public static boolean isCoverByUndergoundBlock(Block block){
    	double x = block.getLocation().getBlockX();
    	double y = block.getLocation().getBlockY();
    	double z = block.getLocation().getBlockZ();
    	ArrayList<Block> blockList = new ArrayList<Block>();
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x+1,y,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x-1,y,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y+1,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y-1,z)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y,z+1)));
    	blockList.add(block.getWorld().getBlockAt(new Location(block.getWorld(),x,y,z-1)));
    	
    	
    	for(int i=0;i<blockList.size();i++){
    		if(!isUndergroundBlock(blockList.get(i))){
    			return false;
    		}
    	}
    	
    	return true;
    }
    public static boolean isDiamond(Block stone){
    	double max_y = 15;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_DIAMOND) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_DIAMOND){
    			stone.setType(Material.DIAMOND_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isEmerald(Block stone){
    	double max_y = 15;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACINGY_EMERALD) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_EMERALD){
    			stone.setType(Material.EMERALD_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isLapis(Block stone){
    	double max_y = 30;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_LAPIS) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_LAPIS){
    			stone.setType(Material.LAPIS_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isRedStone(Block stone){
    	double max_y = 15;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_REDSTONE) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_REDSTONE){
    			stone.setType(Material.REDSTONE_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isGold(Block stone){
    	double max_y = 30;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_GOLD) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_GOLD){
    			stone.setType(Material.GOLD_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isIron(Block stone){
    	double max_y = 60;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_IRON) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_IRON){
    			stone.setType(Material.IRON_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
    public static boolean isCoal(Block stone){
    	double max_y = 70;
    	double min_y = 0;
    	if(!OreReplacerPlugin.REPLACING_COAL) return false;
    	if(stone.getLocation().getBlockY()>min_y &&  stone.getLocation().getBlockY()<max_y){
    		if(Math.random()<=OreReplacerPlugin.PROBABILITY_COAL){
    			stone.setType(Material.COAL_ORE);
    			return true;
    		}
    		else if(isOre(stone)){
    			replaceOreToUndergroudBlock(stone);
    			return false;
    		}
    	}
    	return false;
    }
}
