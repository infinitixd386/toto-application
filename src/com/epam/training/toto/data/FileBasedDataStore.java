package com.epam.training.toto.data;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileBasedDataStore implements DataStore{

    private List<Round> rounds;

    @Override
    public List<Round> getRounds() {
    return rounds;
    }

    public void init(String path){

        List<Round> rounds = new ArrayList<>();
        int indexr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] round = line.split(";");
                int indexh = 0,hit = 14;
                int year = Integer.parseInt(round[0]), week = Integer.parseInt(round[1]), rOW = Integer.parseInt(round[2].replaceAll("-","1"));
                String dateLine = round[3];

                List<Outcome> outcomes = new ArrayList<>();
                List<Hit> hits = new ArrayList<>();

                for (int i = 14; i<round.length; i++){
                    if(round[i].equals("1")) outcomes.add(Outcome._1);
                    else if(round[i].equals("2")) outcomes.add(Outcome._2);
                    else outcomes.add(Outcome.X);
                }
                for (int i = 4; i<14; i+=2){
                    hits.add(indexh++, new Hit(hit--, Integer.parseInt(round[i]), Integer.parseInt(round[i + 1].replaceAll("[ |Ft]", ""))));
                }
                rounds.add(indexr++,new Round(year,week,rOW, dateParse(dateLine,year,week,rOW),outcomes,hits));
            }
            this.rounds = rounds;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalDate dateParse(String dateLine, int year, int week, int rOW){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        LocalDate date;
        Locale locale = Locale.ENGLISH;
        if (dateLine.isEmpty()) {
                    date = LocalDate
                    .of(year,1,1)
                    .with(WeekFields.of(locale).weekOfWeekBasedYear(), week)
                    .with(WeekFields.of(locale).dayOfWeek(),rOW);
                    return date;
        }
        return LocalDate.parse(dateLine,dateFormatter);
    }

}
