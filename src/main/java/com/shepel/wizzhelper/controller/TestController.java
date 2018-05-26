package com.shepel.wizzhelper.controller;


import com.shepel.wizzhelper.domain.SearchParametr;
import com.shepel.wizzhelper.domain.SearchResult;
import com.shepel.wizzhelper.domain.wizz.Flight;
import com.shepel.wizzhelper.servce.TestService;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public List<SearchResult> post(@RequestBody List<SearchParametr> searchParameters) {
        List<SearchResult> data = testService.search(searchParameters);
        return data.stream()
                .sorted(Comparator.comparing(SearchResult::getPrice))
                .collect(Collectors.toList());
    }

}
