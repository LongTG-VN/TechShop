package model;

public class PaymentMethod {

    private int method_id;
    private String method_name;
    private boolean is_active;

    public PaymentMethod(int method_id, String method_name, boolean is_active) {
        this.method_id = method_id;
        this.method_name = method_name;
        this.is_active = is_active;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    

    @Override
    public String toString() {
        return "ID: " + method_id + " | Name: " + method_name + " | Active: " + is_active;
    }
}
