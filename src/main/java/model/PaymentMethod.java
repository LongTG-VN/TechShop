/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WIN11
 */
public class PaymentMethod {

    private int method_id;
    private String method_name;

    public PaymentMethod(int method_id, String method_name) {
        this.method_id = method_id;
        this.method_name = method_name;
    }

    public int getMethod_id() {
        return method_id;
    }

    public void setMethod_id(int method_id) {
        this.method_id = method_id;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    @Override
    public String toString() {
        return "payment id: " + method_id + " | payment method: " + method_name ;
    }

    
}
