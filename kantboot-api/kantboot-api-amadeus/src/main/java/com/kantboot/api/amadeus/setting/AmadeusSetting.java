package com.kantboot.api.amadeus.setting;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Location;
import com.amadeus.referenceData.Locations;
import org.springframework.stereotype.Component;


// TODO 测试API阶段
// https://developers.amadeus.com/self-service/category/flights/api-doc/flight-offers-search
@Component
public class AmadeusSetting {
    private static final String AUTH_URL = "https://test.api.amadeus.com/v1/security/oauth2/token";
    private static final String API_KEY = "9xAPjyAiJ4WyuwjYGgbPcFZANPmMjPA0";
    private static final String API_SECRET = "tqJH2DvCBkenMCuA";

    public Amadeus getAmadeus() {
        return Amadeus
                .builder(API_KEY, API_SECRET)
                .build();
    }


}
