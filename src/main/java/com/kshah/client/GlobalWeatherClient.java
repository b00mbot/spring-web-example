package com.kshah.client;

import com.kshah.model.globalweather.wsdl.GetCitiesByCountry;
import com.kshah.model.globalweather.wsdl.GetCitiesByCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class GlobalWeatherClient {

    private WebServiceTemplate webServiceTemplate;

    private static final Logger log = LoggerFactory.getLogger(GlobalWeatherClient.class);

    public GlobalWeatherClient(WebServiceTemplate webServiceTemplate) {

        // Null check on WebServiceTemplate
        if(webServiceTemplate == null) {
            throw new IllegalStateException("GlobalWeatherClient: WebServiceTemplate dependency is null");
        }

        // Check if default URI is set on WebServiceTemplate
        if(webServiceTemplate.getDefaultUri() == null || webServiceTemplate.getDefaultUri().isEmpty()) {
            throw new IllegalStateException("GlobalWeatherClient: WebServiceTemplate dependency has a null or empty defaultUri");
        }

        // Check if marshaller and unmarshaller are set on WebServiceTemplate
        if(webServiceTemplate.getMarshaller() == null || webServiceTemplate.getUnmarshaller() == null) {
            throw new IllegalStateException("GlobalWeatherClient: WebServiceTemplate dependency has a null marshaller and/or unmarshaller");
        }

        // Check if message sender is set on WebServiceTemplate
        if(webServiceTemplate.getMessageSenders() == null || webServiceTemplate.getMessageSenders().length == 0) {
            throw new IllegalStateException("GlobalWeatherClient: WebServiceTemplate dependency has no message senders set");
        }

        this.webServiceTemplate = webServiceTemplate;
    }


    public GetCitiesByCountryResponse getCitiesByCountry(String country) {

        GetCitiesByCountry request = new GetCitiesByCountry();
        request.setCountryName(country);

        log.info("Sending GetCitiesByCountry request...");

        GetCitiesByCountryResponse response = (GetCitiesByCountryResponse) webServiceTemplate.marshalSendAndReceive(request, new SoapActionCallback("GetCitiesByCountry"));

        return response;
    }



}
