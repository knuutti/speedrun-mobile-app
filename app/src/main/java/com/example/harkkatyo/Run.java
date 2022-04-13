package com.example.harkkatyo;

public class Run {

    private String runId;
    private String realTimeString;
    private String inGameTimeString;
    private String date;
    private String placement;

    private int realTimeInteger;
    private int inGameTimeInteger;

    private Player player;
    private Game game;

    public Run(String placement, String runId, String gameId, String playerId, String playerName, String date, String realTime, String inGameTime) {
        this.placement = placement;
        this.runId = runId;
        this.game = new Game(gameId);
        this.player = new Player(playerId, playerName);
        this.realTimeString = realTime;
        this.inGameTimeString = inGameTime;
    }

    // GETTERS AND SETTERS

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

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

    public String getRealTimeString() {
        return realTimeString;
    }

    public void setRealTimeString(String realTimeString) {
        this.realTimeString = realTimeString;
    }

    public String getInGameTimeString() {
        return inGameTimeString;
    }

    public void setInGameTimeString(String inGameTimeString) {
        this.inGameTimeString = inGameTimeString;
    }

    public int getRealTimeInteger() {
        return realTimeInteger;
    }

    public void setRealTimeInteger(int realTimeInteger) {
        this.realTimeInteger = realTimeInteger;
    }

    public int getInGameTimeInteger() {
        return inGameTimeInteger;
    }

    public void setInGameTimeInteger(int inGameTimeInteger) {
        this.inGameTimeInteger = inGameTimeInteger;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }
}
