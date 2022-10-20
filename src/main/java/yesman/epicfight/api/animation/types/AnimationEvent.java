package yesman.epicfight.api.animation.types;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AnimationEvent {
	final AnimationEvent.Side executionSide;
	final BiConsumer<LivingEntityPatch<?>, Object[]> event;
	Object[] params;
	
	private AnimationEvent(AnimationEvent.Side executionSide, BiConsumer<LivingEntityPatch<?>, Object[]> event) {
		this.executionSide = executionSide;
		this.event = event;
	}
	
	public void executeIfRightSide(LivingEntityPatch<?> entitypatch) {
		if (this.executionSide.predicate.test(entitypatch.isLogicalClient())) {
			this.event.accept(entitypatch, this.params);
		}
	}
	
	public AnimationEvent withParams(Object... params) {
		AnimationEvent event = new AnimationEvent(this.executionSide, this.event);
		event.params = params;
		
		return event;
	}
	
	public static AnimationEvent create(BiConsumer<LivingEntityPatch<?>, Object[]> event, AnimationEvent.Side isRemote) {
		return new AnimationEvent(isRemote, event);
	}
	
	public static class TimeStampedEvent extends AnimationEvent implements Comparable<TimeStampedEvent> {
		final float time;
		
		private TimeStampedEvent(float time, AnimationEvent.Side executionSide, BiConsumer<LivingEntityPatch<?>, Object[]> event) {
			super(executionSide, event);
			this.time = time;
		}
		
		public void executeIfRightSide(LivingEntityPatch<?> entitypatch, float prevElapsed, float elapsed) {
			if (this.time >= prevElapsed && this.time < elapsed) {
				this.executeIfRightSide(entitypatch);
			}
		}
		
		public static TimeStampedEvent create(float time, BiConsumer<LivingEntityPatch<?>, Object[]> event, AnimationEvent.Side isRemote) {
			return new TimeStampedEvent(time, isRemote, event);
		}
		
		public TimeStampedEvent withParams(Object... params) {
			TimeStampedEvent event = new TimeStampedEvent(this.time, this.executionSide, this.event);
			event.params = params;
			
			return event;
		}
		
		@Override
		public int compareTo(TimeStampedEvent arg0) {
			if(this.time == arg0.time) {
				return 0;
			} else {
				return this.time > arg0.time ? 1 : -1;
			}
		}
	}
	
	public static enum Side {
		CLIENT((isLogicalClient) -> isLogicalClient), SERVER((isLogicalClient) -> !isLogicalClient), BOTH((isLogicalClient) -> true);
		
		Predicate<Boolean> predicate;
		
		Side(Predicate<Boolean> predicate) {
			this.predicate = predicate;
		}
	}
}