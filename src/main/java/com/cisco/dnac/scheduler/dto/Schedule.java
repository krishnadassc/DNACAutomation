
package com.cisco.dnac.scheduler.dto;

public class Schedule
{
    public static final int SCHEDULE_HOURLY                 = 1;
    public static final int SCHEDULE_DAILY                  = 2;
    public static final int SCHEDULE_WEEKLY                 = 3;
    public static final int SCHEDULE_MONTHLY                = 4;
    public static final int SCHEDULE_MINUTES                = 5;
    public static final int RECURRENCE_NO_END               = 1;
    public static final int RECURRENCE_NUMBER_OF_OCCURENCES = 2;
    public static final int RECURRENCE_END_BY_DATE          = 3;
    public static final int RECURRENCE_ONLY_ONCE            = 4;
    
    private int             scheduleId;
    
    private long            startTime                       = System.currentTimeMillis();
    
    private int             frequency                       = SCHEDULE_HOURLY;
    
    private int             frequencyInterval               = 1;
    
    private int             recurrenceType                  = RECURRENCE_NO_END;
    
    private int             repeatCount                     = 1;
    
    private long            endTime                         = System.currentTimeMillis();
    
    private long            nextExecutionTime               = 0;
    

    public Schedule()
    {
    }

    public int getFrequency()
    {
        return frequency;
    }

    public void setFrequency(int frequency)
    {
        this.frequency = frequency;
    }

    public int getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount)
    {
        this.repeatCount = repeatCount;
    }

    public long getEndTime()
    {
        return endTime;
    }

    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public int getScheduleId()
    {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId)
    {
        this.scheduleId = scheduleId;
    }

    public long getFrequencyInMilliseconds()
    {
        long freq = -1;

        switch (frequency)
        {
            case SCHEDULE_MINUTES:
                freq = 1 * 60 * 1000L;

                break;

            case SCHEDULE_HOURLY:
                freq = 1 * 60 * 60 * 1000L;

                break;

            case SCHEDULE_DAILY:
                freq = 24 * 60 * 60 * 1000L;

                break;

            case SCHEDULE_WEEKLY:
                freq = 7 * 24 * 60 * 60 * 1000L;

                break;

            case SCHEDULE_MONTHLY:
                freq = 30 * 24 * 60 * 60 * 1000L;

                break;
        }

        return freq;
    }

    public void setFrequencyInterval(int frequencyInterval)
    {
        this.frequencyInterval = frequencyInterval;
    }

    public int getFrequencyInterval()
    {
        return frequencyInterval;
    }

    public void setRecurrenceType(int recurrenceType)
    {
        this.recurrenceType = recurrenceType;
    }

    public int getRecurrenceType()
    {
        return recurrenceType;
    }

	public long getNextExecutionTime() {
		return nextExecutionTime;
	}

	public void setNextExecutionTime(long nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}
    
    
}
