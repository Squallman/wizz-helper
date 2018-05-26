package com.shepel.wizzhelper.servce;

import com.shepel.wizzhelper.domain.wizz.Flight;

import java.util.Date;
import java.util.Map;

public interface SearchService {

    Map<Date, Flight> search(final String dep, final String arr);
}
