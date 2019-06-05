package com.rowlands.searchapi.service;

import com.rowlands.searchapi.api.SearchApiResponse;

import java.io.IOException;

public interface SearchEngine {
    SearchApiResponse search(String queryString) throws IOException;
}
