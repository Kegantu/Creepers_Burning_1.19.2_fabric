package me.kegantu.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

	protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "initGoals")
	protected void initGoals(CallbackInfo info) {
		this.goalSelector.add(2, new AvoidSunlightGoal(this));
		this.goalSelector.add(3, new EscapeSunlightGoal(this, 1d));
	}

	@Override
	public void tickMovement() {
		if (this.isAlive()){
			boolean AffectedBySunLight = this.isAffectedByDaylight();
			if (AffectedBySunLight){
				this.setOnFireFor(6);
			}
		}
		super.tickMovement();
	}
}