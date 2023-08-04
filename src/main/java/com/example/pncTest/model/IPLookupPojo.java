package com.example.pncTest.model;

public class IPLookupPojo {
    public String status;
    public String country;
    public String countryCode;
    public String region;
    public String regionName;
    public String city;
    public String zip;
    public String lat;
    public String lon;
    public String timezone;
    public String isp;

    public String org;
    public String as;
    public String query;

    public IPLookupPojo(String status, String country, String countryCode, String region, String regionName, String city, String zip, String lat, String lon, String timezone, String isp, String org, String as, String query) {
        this.status = status;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.city = city;
        this.zip = zip;
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.isp = isp;
        this.org = org;
        this.as = as;
        this.query = query;
    }

    public String getCountry(){
        return country;
    }
    public String getCity(){
        return city;
    }
}
