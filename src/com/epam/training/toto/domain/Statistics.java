package com.epam.training.toto.domain;

public class Statistics {

    private double firstTeamWinPercentage;
    private double secondTeamWinPercentage;
    private double drawPercentage;

    public Statistics(double firstTeamWinPercentage, double secondTeamWinPercentage, double drawTeamWinPercentage) {
        this.firstTeamWinPercentage = firstTeamWinPercentage;
        this.secondTeamWinPercentage = secondTeamWinPercentage;
        this.drawPercentage = drawTeamWinPercentage;
    }

    public double getFirstTeamWinPercentage() {
        return firstTeamWinPercentage;
    }

    public double getSecondTeamWinPercentage() {
        return secondTeamWinPercentage;
    }

    public double getDrawPercentage() {
        return drawPercentage;
    }
}
