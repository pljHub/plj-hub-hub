package org.msa.hub.global.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class GeocodingUtil {

    private final RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String googleApiKey;

    // RestTemplate 의존성 주입
    public GeocodingUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 주소로 위도 경도 계산
    public LatLong getLatLongFromAddress(String address) {

        log.info("address: {}", address);

        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                address, googleApiKey);

        log.info("url: {}", url);

        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        if (jsonResponse.getString("status").equals("OK")) {
            JSONArray results = jsonResponse.getJSONArray("results");
            JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

            // 위도와 경도를 double 타입으로 받아옴
            double latitude = location.getDouble("lat");
            double longitude = location.getDouble("lng");

            return new LatLong(latitude, longitude); // double 타입의 위도와 경도를 반환
        }

        throw new IllegalArgumentException("주소를 변환할 수 없습니다.");
    }

    @Getter
    public static class LatLong {

        private final double latitude; // 위도
        private final double longitude; // 경도

        public LatLong(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
