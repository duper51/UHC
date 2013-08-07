package net.thepark.uhc.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class Recipes {
	public static ShapedRecipe goldenApple() {
		ShapedRecipe apple = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE, 1));
		apple.shape("GGG", "GAG", "GGG");
		
		apple.setIngredient('G', Material.GOLD_INGOT);
		apple.setIngredient('A', Material.APPLE);
	
		return apple;
	}
	
	public static ShapelessRecipe healthPotion() {
		Potion potion = new Potion(PotionType.INSTANT_HEAL);
		
		ShapelessRecipe health = new ShapelessRecipe(potion.toItemStack(1));
		health.addIngredient(Material.RED_ROSE);
		health.addIngredient(Material.DIAMOND);
		health.addIngredient(Material.GOLD_BLOCK);
		health.addIngredient(Material.GLASS_BOTTLE);
		
		return health;
	}
	
	public static ShapedRecipe compass() {
		ShapedRecipe compass = new ShapedRecipe(new ItemStack(Material.COMPASS, 1));
		compass.shape(" G ", "GRG", " G ");
		
		compass.setIngredient('G', Material.GOLD_INGOT);
		compass.setIngredient('R', Material.REDSTONE);
		
		return compass;
	}

	public static void removeUnwantedRecipies(List<Recipe> recipes) {
		for(Recipe recipe : recipes) {
			ItemStack item = recipe.getResult();
			
			if(item.getType() == Material.GOLDEN_APPLE || item.getType() == Material.COMPASS) {
				recipes.remove(recipe);
			}
		}
	}
}
