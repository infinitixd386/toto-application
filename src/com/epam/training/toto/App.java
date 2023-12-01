package com.epam.training.toto;

import com.epam.training.toto.data.FileBasedDataStore;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Result;
import com.epam.training.toto.domain.Statistics;
import com.epam.training.toto.service.TotoService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    public static List<Outcome> outcomeConvert(String outcome){
        List<Outcome> outcomeList = new ArrayList<>();

        for (int i = 0; i < outcome.length(); i++) {
            if (outcome.charAt(i) == '1') outcomeList.add(Outcome._1);
            else if (outcome.charAt(i) == '2') outcomeList.add(Outcome._2);
            else outcomeList.add(Outcome.X);
        }

        return outcomeList;
    }

    public static void main(String[] args) throws Exception {

        List<Outcome> outcomes = new ArrayList<>();
        Scanner keyboard = new Scanner(System.in);
        String outcome = "";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        String pattern1 = "###,###";
        String pattern2 = "###.##";

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat priceFormat = new DecimalFormat(pattern1,symbols);
        DecimalFormat statsFormat = new DecimalFormat(pattern2);

        FileBasedDataStore file = new FileBasedDataStore();
        file.init("toto.csv");
        TotoService player = new TotoService(file);

        System.out.println("The largest prize ever recorded: "+ priceFormat.format(player.getLargestPrize())+" Ft");

        Statistics statistics = player.calculateStatistics();
        System.out.println("Statistics: team #1 won: "+statsFormat.format(statistics.getFirstTeamWinPercentage())+" %, team #2 won: "+statsFormat.format(statistics.getSecondTeamWinPercentage())+" %, draw: "+statsFormat.format(statistics.getDrawPercentage())+" %");

        System.out.print("Enter date: ");
        String dates = keyboard.nextLine();
        LocalDate date = LocalDate.parse(dates,dateFormatter);

        System.out.print("Enter outcomes: ");
        do {
            outcome = keyboard.nextLine();
            if(!(outcome.length()==14))
            System.out.print("Enter 14 outcomes: ");
        }while (outcome.length()==14);

        try {
            Result result = player.getResult(date, outcomeConvert(outcome));
            System.out.println("Result: hits: " + result.getHitCount() + ", amount: " + priceFormat.format(result.getPrize()) + " Ft");
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }

    }
}
