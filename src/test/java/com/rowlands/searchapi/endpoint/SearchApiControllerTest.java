package com.rowlands.searchapi.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rowlands.searchapi.api.SearchApiRequest;
import com.rowlands.searchapi.api.SearchApiResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchApiController.class)
public class SearchApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchApiController searchApiController;


    @Test
    public void testGetResultsForTest() throws Exception {
        SearchApiRequest request = new SearchApiRequest("test");
        SearchApiResponse response = new SearchApiResponse("test", "www.test.com");

        given(searchApiController.searchForSecondResult(any(SearchApiRequest.class))).willReturn(response);

        mvc.perform(post("/search-for-second-result")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(response.getTitle())))
                .andExpect(jsonPath("url", is(response.getUrl())));
    }

    @Test
    public void testBlankSearchQueryRequest() throws Exception {
        SearchApiRequest request = new SearchApiRequest("");

        mvc.perform(post("/search-for-second-result")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNullSearchQueryRequest() throws Exception {
        SearchApiRequest request = new SearchApiRequest(null);

        mvc.perform(post("/search-for-second-result")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testIncorrectEndpointReturns404() throws Exception {
        SearchApiRequest request = new SearchApiRequest(null);

        mvc.perform(post("/not-an-endpoint")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }


}