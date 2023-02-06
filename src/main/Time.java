package main;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Time {
    GamePanel gp;
    // TIME
    public int hours = 7;
    public int minutes = 30;

    NumberFormat formatter = new DecimalFormat("00");
    public String timeString = formatter.format(hours)+":"+formatter.format(minutes);

    public Time(GamePanel gp) {
        this.gp = gp;
    }

    public void addMinutes(int minutesToAdd){

        minutes += minutesToAdd;
        if(minutes >= 60){
            minutes -= 60;
            hours++;
        }
        timeString = formatter.format(hours)+":"+formatter.format(minutes);

    }

}
