/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Specification {
    private int specificationId;
    private product product;
    private String screen;
    private String cpu;
    private String ram;
    private String camera;
    private String battery;
    private String os;

    public Specification() {
    }

    public Specification(int specificationId, product product, String screen, String cpu, String ram, String camera, String battery, String os) {
        this.specificationId = specificationId;
        this.product = product;
        this.screen = screen;
        this.cpu = cpu;
        this.ram = ram;
        this.camera = camera;
        this.battery = battery;
        this.os = os;
    }

    public int getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(int specificationId) {
        this.specificationId = specificationId;
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return "Specification{" + "specificationId=" + specificationId + ", product=" + product + ", screen=" + screen + ", cpu=" + cpu + ", ram=" + ram + ", camera=" + camera + ", battery=" + battery + ", os=" + os + '}';
    }
    
    
}
