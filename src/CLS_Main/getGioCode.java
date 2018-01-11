/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

/**
 *
 * @author Isuru
 */
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isuru
 */
public class getGioCode {

    GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAhr55gEo-FlzZxO03U_rNMph3p5TSzhIY");
    GeocodingResult[] results;
    public static AddressComponent[] address;

    public static double[] getGioCode(String location) throws Exception {
        double northeastLat = 0, northeastLng = 0, southwestLat = 0, southwestLng = 0;
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAhr55gEo-FlzZxO03U_rNMph3p5TSzhIY");
        GeocodingResult[] results = GeocodingApi.geocode(context, location).await();
        for (GeocodingResult result : results) {
            northeastLat = result.geometry.bounds.northeast.lat;
            southwestLat = result.geometry.bounds.southwest.lat;
            northeastLng = result.geometry.bounds.northeast.lng;
            southwestLng = result.geometry.bounds.southwest.lng;
            System.out.println(northeastLat + "\n" + northeastLng + "\n" + southwestLat + "\n" + southwestLng);
            address = result.addressComponents;//Matara Matara Southern Province Sri Lanka
        }

        return new double[]{northeastLat, northeastLng, southwestLat, southwestLng};
    }

    public static void main(String[] args) throws Exception {
        getGioCode gioCode = new getGioCode();
        gioCode.getGioCode("sri lanka");
    }
}
