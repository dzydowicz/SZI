package com.company.LogicLayer;

public class Meal {
    private final String name; //nazwa posi�ku
    private int time; // czas przygotowania, prawdopodobnie b�dzie podawany w sekundach

    public Meal(String name, int time){
        this.name = name;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public int getTime(){
        return time;
    }

    public boolean equals(Meal meal){
        if (this.name.equals(meal.getName()) &&
                this.time == meal.getTime() ) return true;
        return false;
    }
}