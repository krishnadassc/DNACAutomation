package com.cisco.dnac.scheduler;

public class RandomScheduleDelay {

	private long constantDelayMins;

	private long spreadMinValueMins;

	private long spreadMaxValueMins;

	public RandomScheduleDelay(long constantDelayMins, long spreadMinValueMins, long spreadMaxValueMins) {
		super();
		this.constantDelayMins = constantDelayMins;
		this.spreadMinValueMins = spreadMinValueMins;
		this.spreadMaxValueMins = spreadMaxValueMins;
	}

	public long getConstantDelayMins() {
		return constantDelayMins;
	}

	public void setConstantDelayMins(long constantDelayMins) {
		this.constantDelayMins = constantDelayMins;
	}

	public long getSpreadMinValueMins() {
		return spreadMinValueMins;
	}

	public void setSpreadMinValueMins(long spreadMinValueMins) {
		this.spreadMinValueMins = spreadMinValueMins;
	}

	public long getSpreadMaxValueMins() {
		return spreadMaxValueMins;
	}

	public void setSpreadMaxValueMins(long spreadMaxValueMins) {
		this.spreadMaxValueMins = spreadMaxValueMins;
	}

	public long getDelay() {
		long multFactor = computeMultiplyFactor();

		return constantDelayMins * 60 * 1000L + (long) (Math.random() * multFactor);
	}

	private long computeMultiplyFactor() {
		// TODO Auto-generated method stu
		double nr = (spreadMaxValueMins - spreadMinValueMins) * 60 * 1000;
		return (long) ((nr * 10) / 8); // Random delay = 0.9 - 0.1
	}

	public static void main(String[] args) {
		System.out.println((new RandomScheduleDelay(0, 1, 5).getDelay()));
	}

}
