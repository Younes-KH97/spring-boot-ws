package com.example.demo.endpoint_ws;

import com.example.demo.gs_producing_web_service.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();

    private final String path = "/data.json";



    @PostConstruct
    public void initData() throws IOException {

        TypeReference<List<CountryData>> typeReference = new TypeReference<List<CountryData>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(path);
        List<CountryData> countryDataList = new ObjectMapper().readValue(inputStream, typeReference);
        if(countryDataList != null && !countryDataList.isEmpty()){
            countryDataList.forEach(country -> {
                Country c = new Country();
                c.setCountryName(country.getCountry_name());
                c.setBpd(country.getBpd());
                c.setRefMonth(country.getRefMonth());
                countries.put(country.getCountry_name(),c);
            });
        }


    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}

class CountryData{
    private String country_name;
    private  Integer bpd;
    private XMLGregorianCalendar refMonth;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public Integer getBpd() {
        return bpd;
    }

    public void setBpd(Integer bpd) {
        this.bpd = bpd;
    }

    public XMLGregorianCalendar getRefMonth() {
        return refMonth;
    }

    public void setRefMonth(XMLGregorianCalendar refMonth) {
        this.refMonth = refMonth;
    }
}