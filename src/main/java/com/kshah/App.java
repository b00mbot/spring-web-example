package com.kshah;


import com.kshah.client.GlobalWeatherClient;
import com.kshah.model.globalweather.wsdl.GetCitiesByCountryResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }

    @Bean
    public CommandLineRunner run(GlobalWeatherClient client) {
        return args -> {
            GetCitiesByCountryResponse response = client.getCitiesByCountry("Canada");
        };
    }

}
