package com.example.deardoctor.Weeks;

public class WeekArray {

   private int slopCount,color;
   private String slotNumber,end;


    public WeekArray(int slopCount, String slotNumber, int color, String end){
        this.slopCount = slopCount;
        this.slotNumber = slotNumber;
        this.color =color;
        this.end = end;
    }

     WeekArray(WeekArray targetUser) {
        this.slopCount = targetUser. slopCount;
        this.slotNumber = targetUser.slotNumber;
        this.color =targetUser.color;
    }


    public String getEnd() {
        return end;
    }

    public int getColor() {
        return color;
    }

    public int getSlopCount() {
        return slopCount;
    }

    String getSlotNumber() {
        return slotNumber;
    }
}
