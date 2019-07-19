package com.cisco.dnac.scheduler;

import java.util.concurrent.Future;

public interface SystemExecutorServiceIf {
	Future<?> sheduleTask(String taskName, Runnable runnable, long scheduledTimeInMilliSec);

}
