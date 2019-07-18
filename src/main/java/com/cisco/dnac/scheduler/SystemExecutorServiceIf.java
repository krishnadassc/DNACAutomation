package com.cisco.dnac.scheduler;

import java.util.TimerTask;
import java.util.concurrent.Future;

public interface SystemExecutorServiceIf {
	Future<?> runNow(String name, TimerTask task);

	Future<?> runNow(String name, Runnable theRunnable);
}
