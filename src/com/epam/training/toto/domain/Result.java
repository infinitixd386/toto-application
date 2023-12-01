package com.epam.training.toto.domain;

public class Result {

    private int hitCount;
    private int prize;

    public Result(int hitCount, int prize) {
        this.hitCount = hitCount;
        this.prize = prize;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getPrize() {
        return prize;
    }
}
