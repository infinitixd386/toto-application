package com.epam.training.toto.service;

import com.epam.training.toto.data.DataStore;
import com.epam.training.toto.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class TotoService {

    private DataStore dataStore;

    public TotoService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Result getResult(LocalDate date, List<Outcome> playeroutcome){

            List<Round> rounds = dataStore.getRounds();
            Round round = rounds.stream()
                    .filter(round1 -> date.equals(round1.getDate()))
                    .findAny()
                    .orElse(null);
            if(round==null)
                throw new NoSuchElementException("There was no such round");

            List<Outcome> roundoutcome = round.getOutcomes();
            List<Hit> hits = round.getHits();

            int hit = 0, prize = 0;
            for(int i = 0; i < playeroutcome.size(); i++)
                if(playeroutcome.get(i).equals(roundoutcome.get(i))) hit++;

            if(hit>=10) prize = hits.get(14-hit).getPrize();
            Result result = new Result(hit,prize);
            return result;
}

public Statistics calculateStatistics() {
    List<Round> rounds = dataStore.getRounds();
    int index = 0;
    double team1 = 0, team2 = 0, teamX = 0;

    for (Round r : rounds){
        List<Outcome> outcomes = r.getOutcomes();
        for (Outcome o : outcomes) {
            if (o.toString().equals("_1")) team1++;
                else if (o.toString().equals("_2")) team2++;
                    else teamX++;
    }
    }

    double matches = team1 + team2 + teamX;
    team1/=matches/100; team2/=matches/100; teamX/=matches/100;
    Statistics stats = new Statistics(team1,team2,teamX);

    return stats;
}

public int getLargestPrize(){
    List<Round> rounds = dataStore.getRounds();
    int index = 0, largestPrize=0;

    for (Round r : rounds) {
        List<Hit> hits = r.getHits();
        for (Hit h : hits) {
            if (largestPrize < h.getPrize()) largestPrize=h.getPrize();
        }
    }

    return largestPrize;
}
}
