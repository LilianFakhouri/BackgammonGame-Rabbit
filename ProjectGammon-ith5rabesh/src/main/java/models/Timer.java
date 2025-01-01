package models;

public class Timer {
	private int hour;
    private int minute;
    private int second;

    // Constructor with hour, minute, and second
    public Timer(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Constructor with a string representing the time in "HH:mm:ss" format
    public Timer(String currentTime) {
        String[] time = currentTime.split(":");
        this.hour = Integer.parseInt(time[0]);
        this.minute = Integer.parseInt(time[1]);
        this.second = Integer.parseInt(time[2]);
    }

    // Method to get the current time as a string
    public String getCurrentTime() {
        return hour + ":" + minute + ":" + second;
    }

    // Method to increment the timer by one second
    public void oneSecondPassed() {
        second++;
        if (second >= 60) {
            second = 0;
            minute++;
        }
        if (minute >= 60) {
            minute = 0;
            hour++;
        }
        if (hour >= 24) {
            hour = 0;
        }
    }
}


