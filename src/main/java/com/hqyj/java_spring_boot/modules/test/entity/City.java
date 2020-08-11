package com.hqyj.java_spring_boot.modules.test.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class City {
    private Integer cityId;

    private String cityName;

    private String localCityName;

    private Integer countryId;

    private String district;

    private Integer population;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateModified;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateCreated;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocalCityName() {
        return localCityName;
    }

    public void setLocalCityName(String localCityName) {
        this.localCityName = localCityName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "City [cityId=" + cityId + ", cityName=" + cityName + ", localCityName=" + localCityName + ", countryId="
                + countryId + ", district=" + district + ", population=" + population + ", dateModified=" + dateModified
                + ", dateCreated=" + dateCreated + "]";
    }


}