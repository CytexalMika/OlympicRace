package com.cytexal.Dragons;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.EntityTNTPrimed;

import org.apache.logging.log4j.core.net.DatagramOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.PlaySoundCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.connorlinfoot.titleapi.TitleAPI;
import com.darkblade12.particleeffect.ParticleEffect;

public class main extends JavaPlugin implements Listener{
	HashMap<Player, ItemStack[]> invo = new HashMap<>();
	HashMap<Player, ItemStack[]> invor = new HashMap<>();
	ArrayList<Player> InGame = new ArrayList<Player>();
	ArrayList<Player> Death = new ArrayList<Player>();
	ArrayList<Player> cooldown = new ArrayList<Player>();
	List<Location> locations = new ArrayList<Location>();
	List<ItemStack> items = new ArrayList<ItemStack>();
	ArrayList<Player> fly = new ArrayList<Player>();
	ArrayList<Player> Team = new ArrayList<Player>();
	Location loc = null;
	Location loc2 = null;
	ArrayList<Player> online = new ArrayList<Player>();
	int zeit;
	Player Sneakp = null;
	boolean game = false;
	int high = 60;
	int Count;
	public Player plt = null;
	public Player pltm = null;
	boolean Countdown = false;
	int shed5;
	Location spawn = new Location(Bukkit.getWorld("DA"), -1160, 57, 167);
	int s = 0;
	double Leben;
	int i = 20;
	int counta = 0;
	int sched2; 
	int sched;
	int Start = InGame.size();
    HashMap<String, Location> Checkpoints = new HashMap<>();
	int sched3;
	LivingEntity Zombie;
	HashMap<Player, Player> accept = new HashMap<Player,Player>();
	List<Player> pl= new ArrayList<Player>(); 
	

	@Override
	public void onEnable() {
		System.out.println("[Whiterattack] Das Plugin wurde erfolgreich gestartet!");
		getCommand("dragons").setExecutor(this);
		Cooldown();
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void Cooldown() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				cooldown.clear();
				for (Entity ent : Bukkit.getWorld(Bukkit.getWorld("Dragons").getName()).getEntities()) {
		            if (!(ent instanceof Player) && !(ent instanceof EnderDragon) && !(ent instanceof Item)) {
		               ent.remove();
		            }
		        }
				
			}
		}, 0, 20*5);
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		if(p.hasPermission("Cytexal.vip")){
			p.sendMessage("§e§lCytexal§3§l.com§7§l>>>> §7Da du den Rang §6§lPremium §7besitzt kannst du den §a§lInstantStart §7verwenden"); 
		}
				if(game == true){
					p.kickPlayer("§6§lOlympic§7§lRace>>> §7Das Spiel läuft bereits bitte warte bis zur nächsten Runde! Die Runde läuft bereits seit: §c" + zeit + " Minuten!");
					}else{
					p.setPlayerTime(13000, true);
					ItemStack InstaStart = new ItemStack(Material.EMERALD);
					ItemMeta InstaStartMeta = InstaStart.getItemMeta();
					InstaStartMeta.setDisplayName("§7§l>>§a§lInstantStart");
					InstaStartMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					InstaStart.setItemMeta(InstaStartMeta);
					InGame.add(p.getPlayer());
					online.add(p.getPlayer());
					p.setHealth(20);
					p.setFoodLevel(20);
					p.setGameMode(GameMode.ADVENTURE);
					Location lobby = new Location(Bukkit.getWorld("Olympus"), 1689, 34, -15);
					p.teleport(lobby);
					p.sendMessage("§e[]------------------------------------------[] ");
					p.sendMessage("§7§l>>>>§6§lOlympic§7§lRace>>>:");
					p.sendMessage("§7Du bist dem Spiel beigetreten!");
					p.sendMessage("§e[]------------------------------------------[] ");		
					p.getInventory().setItem(4, InstaStart);
					int vS = 2 - online.size();
					if(InGame.size() >= Start){
						if(Start < 2){
						p.sendMessage("§7§l>>>>§6§lOlympic§7§lRace>>> Es werden " + vS +" weitere Spieler benötigt!");	
						}
						if(Countdown == true){
							
						}else{
							Countdown = true;
							Count();	
						}
					}
	}
}
public void Count(){
	sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		
		@Override
		public void run() {
			high --;
			for(Player p : InGame){
				if(high >= 1){
					p.setLevel(high);
					if(high==30||high==20||high==10||high<=3){
						p.sendMessage("§7§lDragon§5§lAttack>>>> §7Das Spiel startet in §c" + high + " Sekunden");
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 100, 1);	
					} 
					}else{
						p.getInventory().clear();
					 	Zeit();
					 	game = true;
						p.sendMessage("§6§lOlympic§7§lRace>>>> §7Lasset die Spiele beginnen");
						p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100, 1);
						Location lobby2 = new Location(Bukkit.getWorld("Olympus"), 1689, 34, -15);
						p.teleport(lobby2);
						ItemStack Bow = new ItemStack(Material.STICK);
						Bow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
						ItemMeta BowMeta = Bow.getItemMeta();
						BowMeta.setDisplayName("§3§lKnüppel");
						Bow.setItemMeta(BowMeta);
						PotionEffect Speed = new PotionEffect(PotionEffectType.SPEED, 999, 2);
						PotionEffect Jump = new PotionEffect(PotionEffectType.JUMP, 999, 2);
						p.addPotionEffect(Jump);
						p.addPotionEffect(Speed);
						
						p.getInventory().setItem(0, Bow);
						Bukkit.getScheduler().cancelTask(sched);
				}
			
		}	
			}
	}, 0, 20);
}
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		Player p = e.getPlayer();
		if(InGame.contains(p.getPlayer())){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void Death(PlayerDeathEvent e){
		int Tode = 2;
		Player p = e.getEntity();
		if(InGame.contains(p)){
			Tode ++;
		    p.sendMessage("§6§lOlympic§7§lRace>>>> §7Du bist §cgestorben!");
	        for(Player pl : InGame){
				pl.getInventory().clear();
				ItemStack Bow = new ItemStack(Material.STICK);
				Bow.addEnchantment(Enchantment.KNOCKBACK, Tode);
				ItemMeta BowMeta = Bow.getItemMeta();
				BowMeta.setDisplayName("§3§lKnüppel");
				
				 pl.getInventory().setItem(1, Bow);
	        	 pl.sendMessage("§6§lOlympic§7§lRace>>>> §7Der Spieler §c" + p.getDisplayName() + "§7 ist gestorben");
	        }
			e.setDeathMessage("");
			e.setKeepInventory(true);
			p.getInventory().clear();
			e.setKeepInventory(false);
		}
	}
	public void onDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(InGame.contains(p.getPlayer())){
			e.setCancelled(true);
		}
	}
	public void Zeit(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				zeit ++;
				for(Player p : Bukkit.getOnlinePlayers()){
					p.getInventory().clear();
					ItemStack Bow = new ItemStack(Material.STICK);
					Bow.addEnchantment(Enchantment.KNOCKBACK, zeit);
					ItemMeta BowMeta = Bow.getItemMeta();
					BowMeta.setDisplayName("§3§lKnüppel");
					
					p.getInventory().setItem(1, Bow);
				}
			}
		}, 0, 20*60);
	}
	public void Win(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : InGame){
					Bukkit.shutdown();
				}
				
			}
		}, 20*10);
	}
	@EventHandler
	public void Leave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		InGame.remove(p.getPlayer());
		online.remove(p.getPlayer());
		if(InGame.size() < 2){
				Bukkit.reload();
				for(Player pl : Bukkit.getOnlinePlayers()){
					pl.kickPlayer("§6§lOlympic§7§lRace>>>> Die Runde wurde beendet!");
				}
				}
		}
	  @EventHandler
	  public void onPlayerInteract4(PlayerInteractEvent e) {
	    Player p = e.getPlayer();
	    if ((((e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0)) != 0) && 
	      (p.getItemInHand().getItemMeta().hasDisplayName()))
	    {
	      if (p.getItemInHand().getItemMeta().getDisplayName().equals("§7§l>>§a§lInstantStart"))
	      {
	    	  high = 10;
	    	  
	      }
	    }
	  }
	  @EventHandler
	  public void onPlayerJoin(PlayerLoginEvent e){
		  if(game == true){
			  e.disallow(null, "§6§lOlympic§7§lRace>>>> §7Das Spiel läuft bereits!");
		  }
	  }
	  @EventHandler
	  public void onMove(PlayerMoveEvent e){
		  Player p = e.getPlayer();
		  InGame.remove(p.getPlayer());
		  if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.DIAMOND_BLOCK)){
			  for(Player pl : InGame){
				  pl.sendMessage("§6§lOlympic§7§lRace>>>> §7Der Spieler §6" + p.getDisplayName() + " §7hat das Spiel §6gewonnen");
			  }
			  Location lobby = new Location(Bukkit.getWorld("Olympus"), 1694, 34, -21);
			  p.teleport(lobby);
			  Bukkit.broadcastMessage("§6§lOlympic§7§lRace>>>> §7Du §7hast das Spiel §6gewonnen");
			  Bukkit.shutdown();
		  }
	  }
	  @EventHandler
	  public void onWay(PlayerMoveEvent e){
		  Player p = e.getPlayer();
		  if(game == true){
		      if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SAND) || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR) || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.EMERALD_BLOCK)){
	  }else{
		PotionEffect Speed = new PotionEffect(PotionEffectType.SPEED, 99999, 2);
		PotionEffect Jump = new PotionEffect(PotionEffectType.JUMP, 99999, 2);
		p.addPotionEffect(Jump);
		p.addPotionEffect(Speed);
		p.setHealth(20);
		p.setFoodLevel(20);
		  Location Checks = Checkpoints.get(p.getName());
		  p.teleport(Checks);
			ItemStack Bow = new ItemStack(Material.STICK);
			Bow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
			ItemMeta BowMeta = Bow.getItemMeta();
			BowMeta.setDisplayName("§3§lKnüppel");
			Bow.setItemMeta(BowMeta);
		p.getInventory().setItem(0, Bow);
		  p.sendMessage("§6§lOlympic§7§lRace>>>>§7Du wurdest zu deinem letzten Checkpoint §ateleportiert");
	  }  
		  }
}
	  @EventHandler
	  public void onCheck(PlayerMoveEvent e){
		  Player p = e.getPlayer();
		  World Welt = Bukkit.getWorld("Olympus");
		      if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.EMERALD_BLOCK)){
		    	  if(game == true){
			    	  Checkpoints.put(p.getName(), p.getLocation());
			    	  p.playSound(p.getLocation(), Sound.NOTE_PLING, 100, 1);
			    	  ParticleEffect.VILLAGER_HAPPY.display(2, 2, 2, 80, 40, p.getLocation(), 3);  
		    	  }
	  }  
	  }
	  @EventHandler
	  public void Damage(EntityDamageEvent e){
		  Player damager = (Player) e.getEntity();
		  Location Checks = Checkpoints.get(damager.getName());
		  double health = damager.getHealth();
		  if(damager instanceof Player){
			  if(health <= 1){
					PotionEffect Speed = new PotionEffect(PotionEffectType.SPEED, 999, 2);
					PotionEffect Jump = new PotionEffect(PotionEffectType.JUMP, 999, 2);
					damager.addPotionEffect(Jump);
					damager.addPotionEffect(Speed);
				  e.setCancelled(true);
				  damager.teleport(Checks);
				  damager.setHealth(20);
				  damager.setFoodLevel(20);
			  }
		  }
		  if(e.getCause().equals(DamageCause.FALL)){
			  e.setDamage(0);
		  }
	  }
	  @EventHandler
	  public void Drop(PlayerDropItemEvent e){
		  e.setCancelled(true);
	  }
}