package com.waakka.myshowview.been;

public class ItemBeen {

    /**
     * chargeNum : 2
     * price : 1.00
     * chargeMoney : 200.00
     * chargeDate : 2019-07-04
     */

    private double chargeNum;
    private double price;
    private double chargeMoney;
    private String chargeDate;

    public double getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(double chargeNum) {
        this.chargeNum = chargeNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    @Override
    public String toString() {
        return "ItemBeen{" +
                "chargeNum=" + chargeNum +
                ", price=" + price +
                ", chargeMoney=" + chargeMoney +
                ", chargeDate='" + chargeDate + '\'' +
                '}';
    }
}
