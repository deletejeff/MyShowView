package com.waakka.myshowview.been;

public class Params {
    private String id;
    private String sign;

    public Params(String id, String sign) {
        this.id = id;
        this.sign = sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Params{" +
                "id='" + id + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
