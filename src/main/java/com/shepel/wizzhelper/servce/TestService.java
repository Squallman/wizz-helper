package com.shepel.wizzhelper.servce;

import com.shepel.wizzhelper.domain.SearchParametr;
import com.shepel.wizzhelper.domain.SearchResult;

import java.util.List;

public interface TestService {

    List<SearchResult> search(List<SearchParametr> searchParametrs);
}
