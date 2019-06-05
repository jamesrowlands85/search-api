package com.rowlands.searchapi.endpoint;

import com.rowlands.searchapi.api.SearchApiRequest;
import com.rowlands.searchapi.api.SearchApiResponse;
import com.rowlands.searchapi.service.SearchEngineGoogleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
public class SearchApiController {

    @Autowired
    SearchEngineGoogleImpl searchEngine;

    @RequestMapping(value = "search-for-second-result", method = RequestMethod.POST)
    @ResponseBody
    public SearchApiResponse searchForSecondResult(@Valid @RequestBody SearchApiRequest request) {

        return searchEngine.search(request.getQuery());

    }
}
