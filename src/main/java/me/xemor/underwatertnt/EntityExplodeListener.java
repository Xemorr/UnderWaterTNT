package me.xemor.underwatertnt;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof TNTPrimed) && !(entity instanceof Fireball) && !(entity instanceof Creeper)) {
			return;
		}

		if (entity.isOnGround()) {
			Block occupiedBlock = event.getLocation().getBlock();
			Material type = event.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
			if (UnderWaterTNT.getInstance().getConfiguration().getCannonBlocks().contains(type) && occupiedBlock.getType() == Material.WATER) {
				event.setCancelled(true);
				return;
			}
		}

		if (UnderWaterTNT.getInstance().getConfiguration().getRadius() <= 0) {
			return;
		}

		if (entity.hasMetadata("ignore-UnderwaterTNT")) {
			return;
		}
		World world = entity.getWorld();
		Block centerBlock = entity.getLocation().getBlock();
		Location centerLocation = entity.getLocation();
		centerLocation = centerLocation.add(0.5, 0.5, 0.5);
		for (Location loc : generateSphere(centerLocation, UnderWaterTNT.getInstance().getConfiguration().getRadius())) {
			if (loc.getBlock().isLiquid()) {
				loc.getBlock().setType(Material.AIR);
			}
		}
		/*
		for (BlockFace face : BlockFace.values()) {
			Block block = centerBlock.getRelative(face);
			if (block.getType() == Material.WATER) {
				BlockData blockData = block.getBlockData();
				block.setType(Material.AIR);
				new BukkitRunnable() {
					@Override
					public void run() {
						if (block.getType() == Material.AIR) {
							block.setBlockData(blockData);
						}
					}
				}.runTaskLater(UnderWaterTNT.getInstance(), 1L);
			}
		}
		 */
		entity.setMetadata("ignore-UnderwaterTNT", new FixedMetadataValue(UnderWaterTNT.getInstance(), "w"));
		if (entity instanceof TNTPrimed) {
			world.createExplosion(centerBlock.getLocation(), 4f, false, true, entity);
		}
		else if (entity instanceof Fireball) {
			world.createExplosion(centerBlock.getLocation(), 1f, false, true, entity);
		}
		else {
			world.createExplosion(centerBlock.getLocation(), 3f, false, true, entity);
		}

		event.setCancelled(true);

	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager().hasMetadata("ignore-UnderwaterTNT")) {
			e.setCancelled(true);
		}
	}

	private List<Location> generateSphere(Location centerBlock, int radius) {
		if (centerBlock == null) {
			return new ArrayList<>();
		}

		List<Location> circleBlocks = new ArrayList<Location>();

		int bx = centerBlock.getBlockX();
		int by = centerBlock.getBlockY();
		int bz = centerBlock.getBlockZ();

		for (int x = bx - radius; x <= bx + radius; x++) {
			for (int y = by - radius; y <= by + radius; y++) {
				for (int z = bz - radius; z <= bz + radius; z++) {

					double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));

					if (distance < radius * radius) {
						Location location = new Location(centerBlock.getWorld(), x, y, z);
						circleBlocks.add(location);
					}

				}
			}
		}

		return circleBlocks;
	}
}
