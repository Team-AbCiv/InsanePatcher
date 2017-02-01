package info.tritusk.insanepatcher;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;

public class FoodUtil {

    public static boolean isFood(ItemStack stack) {
        if (Constants.APPLECORE_EXIST) {
            return stack.getItem() instanceof IEdible || stack.getItem() instanceof ItemFood;
        } else {
            return stack.getItem() instanceof ItemFood;
        }
    }

    public static int getHungerValueRegen(ItemStack stack) {
        if (Constants.APPLECORE_EXIST) {
            FoodValues foodValues;
            if (stack.getItem() instanceof IEdible) {
                foodValues = ((IEdible)stack.getItem()).getFoodValues(stack);
            } else if (stack.getItem() instanceof ItemFood) {
                foodValues = new FoodValues(
                        ((ItemFood)stack.getItem()).func_150905_g(stack),
                        ((ItemFood)stack.getItem()).func_150906_h(stack));
            } else {
                // Only vanilla ItemFood and AppleCore are supported, vanilla cake exclusive
                // otherwise return 0 for default implementation
                return 0;
            }
            FoodEvent.GetFoodValues event = new FoodEvent.GetFoodValues(stack, foodValues);
            MinecraftForge.EVENT_BUS.post(event);
            return event.foodValues.hunger;
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood)stack.getItem()).func_150905_g(stack);
        } else {
            return 0; // Cover all cases
        }
    }

    public static float getSaturationModifier(ItemStack stack) {
        if (Constants.APPLECORE_EXIST) {
            FoodValues foodValues;
            if (stack.getItem() instanceof IEdible) {
                foodValues = ((IEdible)stack.getItem()).getFoodValues(stack);
            } else if (stack.getItem() instanceof ItemFood) {
                foodValues = new FoodValues(
                        ((ItemFood)stack.getItem()).func_150905_g(stack),
                        ((ItemFood)stack.getItem()).func_150906_h(stack));
            } else {
                //Only vanilla ItemFood and AppleCore are supported, otherwise return 0 for default implementation
                return 0;
            }
            FoodEvent.GetFoodValues event = new FoodEvent.GetFoodValues(stack, foodValues);
            MinecraftForge.EVENT_BUS.post(event);
            return event.foodValues.saturationModifier;
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood)stack.getItem()).func_150905_g(stack);
        } else {
            return 0; // Cover all cases
        }
    }
}
