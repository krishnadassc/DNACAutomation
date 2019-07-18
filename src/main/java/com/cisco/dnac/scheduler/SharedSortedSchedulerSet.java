package com.cisco.dnac.scheduler;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.cisco.dnac.scheduler.dto.Schedule;

public class SharedSortedSchedulerSet {

	private static SharedSortedSchedulerSet instance = new SharedSortedSchedulerSet();

	private SharedSortedSchedulerSet() {

	}

	public static SharedSortedSchedulerSet getInstance() {
		if (instance == null) {
			synchronized (SharedSortedSchedulerSet.class) {
				if (instance == null) {
					instance = new SharedSortedSchedulerSet();
				}
			}
		}
		return instance;
	}

	public static ScheduleComparator scheduleComparator = new ScheduleComparator();

	private static Set<Schedule> scheduleSet = new ConcurrentSkipListSet<Schedule>(scheduleComparator);

	public static class ScheduleComparator implements Comparator<Schedule> {

		@Override
		public int compare(Schedule s1, Schedule s2) {
			if (s1.getNextExecutionTime() > s2.getNextExecutionTime()) {
				return 1;
			} else if (s1.getNextExecutionTime() < s2.getNextExecutionTime()) {
				return -1;
			}
			return 0;
		}

	}

	public void addToScheduleSet(Schedule sch) {
		scheduleSet.add(sch);
	}

	public void removeFromScheduleSet(Schedule sch) {
		Iterator<Schedule> scheduleIterator = scheduleSet.iterator();
		while (scheduleIterator.hasNext()) {
			Schedule schedule = scheduleIterator.next();
			if (schedule != null && schedule.getScheduleId() == sch.getScheduleId()) {
				scheduleIterator.remove();
				break;
			}
		}

	}

	public Schedule getFirstScheduleItem() {
		Iterator<Schedule> it = scheduleSet.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	public Schedule getScheduleItem(long schId) {
		Iterator<Schedule> scheduleIterator = scheduleSet.iterator();
		Schedule schedule = null;
		while (scheduleIterator.hasNext()) {
			schedule = scheduleIterator.next();
			if (schedule != null && schedule.getScheduleId() == schId) {
				return schedule;
			}
		}
		return null;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * Schedule sch = new Schedule(); sch.setScheduleId(5);
	 * sch.setNextExecutionTime(5); addToScheduleSet(sch); sch = new Schedule();
	 * sch.setScheduleId(2); sch.setNextExecutionTime(2); addToScheduleSet(sch);
	 * Schedule sch1 = getSchedule();
	 * System.out.println(sch1.getNextExecutionTime()); sch = new Schedule();
	 * sch.setScheduleId(3); sch.setNextExecutionTime(3); addToScheduleSet(sch);
	 * sch1 = getSchedule(); System.out.println(sch1.getNextExecutionTime()); sch =
	 * new Schedule(); sch.setScheduleId(1); sch.setNextExecutionTime(1);
	 * addToScheduleSet(sch); sch1 = getSchedule();
	 * System.out.println(sch1.getNextExecutionTime()); sch = new Schedule();
	 * sch.setScheduleId(1); sch.setNextExecutionTime(1);
	 * removeFromScheduleSet(sch); sch1 = getSchedule();
	 * System.out.println(sch1.getNextExecutionTime());
	 * 
	 * }
	 */
}
