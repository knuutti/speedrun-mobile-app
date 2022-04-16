package com.example.harkkatyo;

import java.util.regex.Pattern;

public class Run {

    private String runId;
    private String time;
    private String date;
    private String placement;

    private Player player;

    public Run(String placement, String runId, String time, String date, Player player) {
        this.placement = placement;
        this.runId = runId;
        this.date = date;
        this.player = player;
        this.time = getTimeFormat(time);
    }

    // METHODS

    public String getTimeFormat(String timeString) {
        String time = null;
        try {
            String[] times = timeString.split(Pattern.quote("."));

            int timeInt = Integer.parseInt(times[0]);
            int h = timeInt/3600;
            int m = (timeInt - (3600*h)) / 60;
            int s = timeInt - 3600*h - 60*m;

            String minutes = "" + m;
            String seconds = "" + s;
            String hours = "" + h;
            String ms = "";

            if (times.length == 2) {
                ms = times[1];
            }

            if (m < 10 && hours.compareTo("0") != 0) {
                minutes = "0" + minutes;
            }
            if (s < 10) {
                seconds = "0" + seconds;
            }

            if (ms.compareTo("") != 0) {
                if (ms.length() == 1) {
                    ms = ms + "0";
                }
                ms = ms + "ms";
            }

            if (hours.compareTo("0") != 0) {
                time = hours + "h " + minutes + "m " + seconds + "s " + ms;
            }
            else {
                time = minutes + "m " + seconds + "s " + ms;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return time;
    }

    // GETTERS AND SETTERS

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
