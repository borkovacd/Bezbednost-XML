package com.ftn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ftn.webservice.files.RegisterAccomodationResponse;
import com.ftn.webservice.files.City;
import com.ftn.soapclient.SOAPConnector;
import com.ftn.webservice.files.Accomodation;
import com.ftn.webservice.files.RegisterAccomodationRequest;
 
@SpringBootApplication
public class SpringBootSoapClientApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSoapClientApplication.class, args);
    }
     
    /*@Bean
    CommandLineRunner lookup(SOAPConnector soapConnector) {
        return args -> {
            String name = "Sajal";//Default Name
            if(args.length>0){
                name = args[0];
            }
            RegisterAccomodationRequest request = new RegisterAccomodationRequest();
            Accomodation a = new Accomodation();
            a.setName("Hotel Borkovac");
            City city = new City();
            city.setName("Ruma");
            a.setCity(city);
            a.setAddress("Adresa");
            request.setAccomondation(a);
            
             
            RegisterAccomodationResponse response =(RegisterAccomodationResponse) soapConnector.callWebService("https://localhost:8443/ws/accomondation", request);
            System.out.println("Got Response As below ========= : ");
            System.out.println("Response : "+response.getResponse());
           
        };
    }*/
}