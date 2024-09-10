package org.msa.hub.global.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@Log4j2
public class DistanceMatrixUtil {

    @Value("${google.api.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;

    public DistanceMatrixUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDurationFromGoogleMaps(String startAddress, String destAddress) throws UnsupportedEncodingException {

        log.info(startAddress);
        log.info(destAddress);

        String url = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?" + "units=metric&mode=transit&" + "origins=%s&destinations=%s&key=%s",
                startAddress,
                destAddress,
                googleApiKey
        );

        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        // 'rows' 배열 가져오기
        JSONArray rows = jsonResponse.getJSONArray("rows");

        log.info(rows);

        // 첫 번째 'elements' 배열에서 소요 시간 값 추출
        JSONObject elements = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
        String duration = elements.getJSONObject("duration").getString("text");

        return duration;
    }


}
