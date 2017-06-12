package info.tritusk.insanepatcher;

import net.minecraftforge.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;

import javax.annotation.Nullable;

public final class FoodUtil {

    private static final boolean APPLECORE_EXIST = Loader.isModLoaded("AppleCore");

    public static boolean isFood(ItemStack stack) {
        if (APPLECORE_EXIST) {
            return stack.getItem() instanceof IEdible || stack.getItem() instanceof ItemFood;
        } else {
            return stack.getItem() instanceof ItemFood;
        }
    }

    public static FoodValues getFoodValues(ItemStack stack) {
        return getFoodValues(stack, null);
    }

    public static FoodValues getFoodValues(ItemStack stack, @Nullable EntityPlayer player) {
        if (APPLECORE_EXIST) {
            FoodValues originValues;
            if (stack.getItem() instanceof IEdible) {
                originValues = ((IEdible)stack.getItem()).getFoodValues(stack);
            } else if (stack.getItem() instanceof ItemFood) {
                originValues = new FoodValues(
                        ((ItemFood)stack.getItem()).getHealAmount(stack),
                        ((ItemFood)stack.getItem()).getSaturationModifier(stack));
            } else {
                // Only vanilla ItemFood and AppleCore are supported, vanilla cake exclusive
                // otherwise return a dummy one for default implementation
                originValues = new FoodValues(0, 0F);
                return originValues;
            }
            if (player == null) {
                FoodEvent.GetFoodValues event = new FoodEvent.GetFoodValues(stack, originValues);
                MinecraftForge.EVENT_BUS.post(event);
                return event.foodValues;
            } else {
                FoodEvent.GetPlayerFoodValues event = new FoodEvent.GetPlayerFoodValues(player, stack, originValues);
                MinecraftForge.EVENT_BUS.post(event);
                return event.foodValues;
            }
        } else {
            throw new IllegalStateException("AppleCore is not installed.");
        }
    }

    public static int getHungerValueRegen(ItemStack stack) {
        return getHungerValueRegen(stack, null);
    }

    public static int getHungerValueRegen(ItemStack stack, @Nullable EntityPlayer player) {
        if (APPLECORE_EXIST) {
            if (player == null) {
                return getFoodValues(stack).hunger;
            } else {
                return getFoodValues(stack, player).hunger;
            }
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood)stack.getItem()).getHealAmount(stack);
        } else {
            return 0; // Cover all cases
        }
    }

    public static float getSaturationModifier(ItemStack stack) {
        return getSaturationModifier(stack, null);
    }

    public static float getSaturationModifier(ItemStack stack, @Nullable EntityPlayer player) {
        if (APPLECORE_EXIST) {
            if (player == null) {
                return getFoodValues(stack).saturationModifier;
            } else {
                return getFoodValues(stack, player).saturationModifier;
            }
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood)stack.getItem()).getSaturationModifier(stack);
        } else {
            return 0; // Cover all cases
        }
    }
}
