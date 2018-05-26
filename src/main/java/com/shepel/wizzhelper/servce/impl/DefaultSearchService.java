package com.shepel.wizzhelper.servce.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shepel.wizzhelper.config.TimetableConfig;
import com.shepel.wizzhelper.domain.wizz.Flight;
import com.shepel.wizzhelper.domain.wizz.FlightRequest;
import com.shepel.wizzhelper.domain.wizz.TimetableRequest;
import com.shepel.wizzhelper.domain.wizz.TimetableResponse;
import com.shepel.wizzhelper.servce.SearchService;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class DefaultSearchService implements SearchService {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSearchService.class);

    public DefaultSearchService(CloseableHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<Date, Flight> search(final String dep, final String arr) {

        LOGGER.info("Search: " + dep + "-" + arr);
        Map<Date, Flight> results = new HashMap<>();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 2;
        boolean isEmpty = false;
        do {
            TimetableRequest request = createRequest(month, dep, arr);
            TimetableResponse timetableResponse = makeRequest(request);
            if (timetableResponse != null && timetableResponse.getOutboundFlights() != null && timetableResponse.getOutboundFlights().size() > 0) {
                List<Flight> outboundFlights = timetableResponse.getOutboundFlights();
                for (Flight flight : outboundFlights) {
                    results.put(flight.getDepartureDate(), flight);
                }
            } else {
                isEmpty = true;
            }
            month++;
        } while (!isEmpty);

        return results;
    }

    private TimetableRequest createRequest(Integer month, String dep, String arr) {
        TimetableRequest request = new TimetableRequest();

        request.setInfantCount(0);
        request.setAdultCount(2);
        request.setChildCount(0);
        request.setPriceType("regular");

        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setFrom(calculateFrom(month));
        flightRequest.setTo(calculateTo(month));
        flightRequest.setDepartureStation(dep);
        flightRequest.setArrivalStation(arr);

        List<FlightRequest> flightList = new ArrayList<>();
        flightList.add(flightRequest);

        request.setFlightList(flightList);
        
        return request;
    }

    private TimetableResponse makeRequest(TimetableRequest timetableRequest) {
        try {
            HttpPost post = new HttpPost(TimetableConfig.URL);
            String data = objectMapper.writeValueAsString(timetableRequest);
            StringEntity entity = new StringEntity(data, Charset.defaultCharset());
            post.setEntity(entity);
            post.setHeader(HttpHeaders.ACCEPT, "application/json");
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                LOGGER.info(post.getURI().getPath());
                LOGGER.info(response.getStatusLine().toString());
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity());
                    System.out.println(result);
                    return objectMapper.readValue(result, TimetableResponse.class);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String calculateFrom(int month){
        if (month <= 12) {
            return "2018-" + String.format("%02d", month) + "-01";
        } else {
            return "2019-" + String.format("%02d", month - 12) + "-01";
        }
    }

    private String calculateTo(int month){
        if (month <= 11) {
            return "2018-" + String.format("%02d", month + 1) + "-01";
        } else {
            return "2019-" + String.format("%02d", month - 11) + "-01";
        }
    }
}
