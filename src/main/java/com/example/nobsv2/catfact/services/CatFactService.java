package com.example.nobsv2.catfact.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.catfact.model.CatFactDTO;
import com.example.nobsv2.catfact.model.CatFactResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CatFactService implements Query<Integer, CatFactDTO> {

    private final RestTemplate restTemplate;

    private final String url = "https://catfact.ninja/fact";
    private final String MAX_LENGTH = "max_length";

    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Integer input) {

        //sets up our URL with query parameters
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .queryParam(MAX_LENGTH, input)
                .build()
                .toUri();

        //headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accepts", "application/JSON"); //can abstract these string at the top of the class as well

        HttpEntity<String> entity = new HttpEntity<>(headers);

        //handle cat fact error response
        try {
            ResponseEntity<CatFactResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CatFactResponse.class);
            CatFactDTO catFactDTO = new CatFactDTO(response.getBody().getFact());
            return ResponseEntity.ok(catFactDTO);
        } catch (Exception exception) {
            throw new RuntimeException("Cat Fact API is down");
        }
    }
}
