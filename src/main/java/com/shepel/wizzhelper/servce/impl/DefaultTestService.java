package com.shepel.wizzhelper.servce.impl;

import com.shepel.wizzhelper.domain.SearchParametr;
import com.shepel.wizzhelper.domain.SearchResult;
import com.shepel.wizzhelper.domain.wizz.Flight;
import com.shepel.wizzhelper.domain.wizz.Price;
import com.shepel.wizzhelper.servce.SearchService;
import com.shepel.wizzhelper.servce.TestService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service
public class DefaultTestService implements TestService {

    private SearchService searchService;

    public DefaultTestService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<SearchResult> search(List<SearchParametr> searchParametrs) {

        Map<Integer, Map<Date, Flight>> flightListMap = new LinkedHashMap<>();
        for (SearchParametr sParametr : searchParametrs) {
            Map<Date, Flight> flightMap = searchService.search(sParametr.getFrom(), sParametr.getTo());
            flightListMap.put(sParametr.getDay(), flightMap);
        }

        List<SearchResult> result = new ArrayList<>();
        Map<Date, Flight> firstFlightMap = flightListMap.remove(1);
        for (Entry<Date, Flight> firstFlight : firstFlightMap.entrySet()) {
            List<Flight> resultFlightList = new ArrayList<>();
            Date firstDay = firstFlight.getKey();
            resultFlightList.add(firstFlight.getValue());
            for (Entry<Integer, Map<Date, Flight>> otherDaysMap : flightListMap.entrySet()) {
                Integer nextDayValue = otherDaysMap.getKey();
                Date nextDay = addDays(firstDay, nextDayValue);
                if (otherDaysMap.getValue().containsKey(nextDay)) {
                    Flight flight = otherDaysMap.getValue().get(nextDay);
                    resultFlightList.add(flight);
                }
            }
            if (resultFlightList.size() == searchParametrs.size()) {
                System.out.print("Before - ");
                int price = resultFlightList.stream()
                    .map(Flight::getPrice)
                    .mapToInt(this::convertPrice)
                    .sum();
                System.out.println(" Sum: " + price);
                SearchResult searchResult = new SearchResult();
                searchResult.setDate(firstDay.toInstant().toString());
                searchResult.setPrice(price);
                searchResult.setFlights(resultFlightList);
                result.add(searchResult);
            }

        }

        // List<Flight> lvivGdanksList = searchService.search(LVIV, GDANSK);
        // List<Flight> gdanskVieneList = searchService.search(GDANSK, VIENE);
        // List<Flight> budapeshtKyivList = searchService.search(BUDAPEST, KYIV);

        // Map<Integer, List<Flight>> result = new HashMap<>();

        // for (Flight lvivGdansk : lvivGdanksList) {
        //     Date lvivDate = lvivGdansk.getDepartureDate();
        //     Date gdanskDate = addDays(lvivDate, 2);
        //     Date budapestDay = addDays(gdanskDate, 4);
        //     for (Flight gdanskViene : gdanskVieneList) {
        //         for (Flight budapeshtKyiv : budapeshtKyivList) {
        //             if (gdanskViene.getDepartureDate().equals(gdanskDate)
        //                     && budapeshtKyiv.getDepartureDate().equals(budapestDay)) {
        //                 int sum = lvivGdansk.getPrice().getAmount() + convertPLN(gdanskViene.getPrice().getAmount())
        //                         + convertHUF(budapeshtKyiv.getPrice().getAmount());
        //                 List<Flight> relevantList = new ArrayList<>();
        //                 relevantList.add(lvivGdansk);
        //                 relevantList.add(gdanskViene);
        //                 relevantList.add(budapeshtKyiv);
        //                 result.put(sum, relevantList);
        //             }
        //         }
        //     }
        // }

        return result;
    }

    private Date addDays(Date date, int days) {
        days--;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private int convertPrice(Price price) {
        switch (price.getCurrencyCode()) {
            case "UAH":
                System.out.print(" UAN: " + price.getAmount());
                return price.getAmount();
            case "HUF":
                System.out.print(" HUF: " + price.getAmount());
                return convertHUF(price.getAmount());
            case "PLN":
                System.out.print(" PLN: " + price.getAmount());
                return convertPLN(price.getAmount());
        }
        System.out.print("!not-worked - " + price.getCurrencyCode() + ":" + price.getAmount() + "!");
        return 0;
    }

    private int convertHUF(int huf) {
        return Math.round(huf * 0.0966f);
    }

    private int convertPLN(int pln) {
        return Math.round(pln * 7.14666f);
    }
}
