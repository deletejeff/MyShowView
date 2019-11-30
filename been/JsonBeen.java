package com.waakka.myshowview.been;

import java.util.List;

public class JsonBeen {

    /**
     * counterNum : 001
     * data : [{"chargeNum":"1","price":"1.00","chargeMoney":"100.00","chargeDate":"2019-07-03"},{"chargeNum":"2","price":"1.00","chargeMoney":"200.00","chargeDate":"2019-07-04"}]
     * fromTime : 2019-06-29 19:07:00
     * meterCode : 11300
     * orgid : JK
     * sign : 472dccd37b23596556f9b80d001f5546
     * toTime : 2019-07-20 23:59:59
     * userid : machao
     * username : 马超
     */

    private String counterNum;
    private List<ItemBeen> data;
    private String fromTime;
    private String meterCode;
    private String orgid;
    private String sign;
    private String toTime;
    private String qrcodeUrl;
    private String userid;
    private String username;
    private double totalChargeNum;
    private double totalChargeMoney;

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public List<ItemBeen> getData() {
        return data;
    }

    public void setData(List<ItemBeen> data) {
        this.data = data;
    }

    public double getTotalChargeNum() {
        return totalChargeNum;
    }

    public void setTotalChargeNum(double totalChargeNum) {
        this.totalChargeNum = totalChargeNum;
    }

    public double getTotalChargeMoney() {
        return totalChargeMoney;
    }

    public void setTotalChargeMoney(double totalChargeMoney) {
        this.totalChargeMoney = totalChargeMoney;
    }

    public String getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(String counterNum) {
        this.counterNum = counterNum;
    }


    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "JsonBeen{" +
                "counterNum='" + counterNum + '\'' +
                ", data=" + data +
                ", fromTime='" + fromTime + '\'' +
                ", meterCode='" + meterCode + '\'' +
                ", orgid='" + orgid + '\'' +
                ", sign='" + sign + '\'' +
                ", toTime='" + toTime + '\'' +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", totalChargeNum=" + totalChargeNum +
                ", totalChargeMoney=" + totalChargeMoney +
                '}';
    }
}
