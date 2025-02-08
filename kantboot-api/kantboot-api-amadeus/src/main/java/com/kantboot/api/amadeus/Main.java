package com.kantboot.api.amadeus;

import com.alibaba.fastjson2.JSON;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Location;
import com.kantboot.api.amadeus.setting.AmadeusSetting;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "7890");

        Amadeus amadeus = new AmadeusSetting().getAmadeus();
        Location[] locations = amadeus.referenceData.locations.get(Params
                .with("keyword", "SHA")
                .and("subType", Locations.AIRPORT));

        FlightOfferSearch[] flightOfferSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", "SHA") // 上海
//                        .and("destinationLocationCode", "BJS") // 北京
                        // 马德里
//                        .and("destinationLocationCode", "MAD")
                        // 马尔丁
                        .and("destinationLocationCode", "MRU")
                        .and("departureDate", "2025-02-08")
                        .and("returnDate", "2025-02-10")
                        .and("adults", 1) // 几个成人
                        .and("max", 30) // 最多返回30个结果
                        .and("currencyCode", "CNY") // 货币单位
        );
        System.out.println(JSON.toJSONString(flightOfferSearches));
        FlightOfferSearch offer = flightOfferSearches[0];
        FlightPrice post = amadeus.shopping.flightOffersSearch.pricing.post(offer);
        System.out.println(JSON.toJSONString(post));
        System.err.println(JSON.toJSONString(offer));
    }


}
