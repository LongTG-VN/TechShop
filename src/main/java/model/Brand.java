/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author CT
 */
public class Brand {

    private int brand_id;
    private String brand_name;
    private String logo_url;

    public Brand() {
    }

    public Brand(int brand_id, String brand_name, String logo_url) {
        this.brand_id = brand_id;
        this.brand_name = brand_name;
        this.logo_url = logo_url;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    @Override
    public String toString() {
        return "Brand: " + "brand_id = " + brand_id + " | brand_name = " + brand_name + " | logo_url = " + logo_url;
    }

}
//test