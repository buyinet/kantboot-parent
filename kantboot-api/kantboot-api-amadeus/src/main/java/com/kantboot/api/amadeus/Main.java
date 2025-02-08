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

        Amadeus amadeus = new AmadeusSetting().getAmadeus();
        Location[] locations = amadeus.referenceData.locations.get(Params
                .with("keyword", "SHA")
                .and("subType", Locations.AIRPORT));
//        System.out.println(JSON.toJSONString(locations));

        FlightOfferSearch[] flightOfferSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", "SHA") // 上海
//                        .and("destinationLocationCode", "BJS") // 北京
                        .and("destinationLocationCode", "MAD")
                        .and("departureDate", "2025-02-08")
                        .and("returnDate", "2025-02-10")
                        .and("adults", 1)
                        .and("max", 30)
                        .and("currencyCode", "USD")
        );
        System.out.println(JSON.toJSONString(flightOfferSearches));
        FlightOfferSearch offer = flightOfferSearches[0];
        FlightPrice post = amadeus.shopping.flightOffersSearch.pricing.post(offer);
        System.out.println(JSON.toJSONString(post));
        System.err.println(JSON.toJSONString(offer));
    }


}
