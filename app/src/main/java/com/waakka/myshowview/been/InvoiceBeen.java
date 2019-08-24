package com.waakka.myshowview.been;

import java.util.List;

public class InvoiceBeen {


    /**
     * sign : 979c17b38c8b9ad37610a3f8bb1fe6df
     * userid : machao
     * username : 马超
     * orgid : JK
     * meterCode : 11300
     * counterNum : 001
     * payTime : 2019-06-29 19:07:00
     * payNum : 20
     * unitPrice : 4500
     * payMoney : 90000
     * totalAccountReceivable : 90000
     * totalAccountPayable : 90000
     * opName : 柜员1
     * invoiceExtractionCode : cfj5nwk
     * qrcodeUrl : http://www.baidu.com
     * data : [{"payName":"充值","chargeMoney":"20000","chargeDate":"2019-07-03 19:07:00"},{"payName":"补卡","chargeMoney":"5000","chargeDate":"2019-07-03 19:07:00"}]
     */

    private String sign;
    private String userid;
    private String username;
    private String orgid;
    private String meterCode;
    private String counterNum;
    private String payTime;
    private String payNum;
    private String unitPrice;
    private String payMoney;
    private double totalAccountReceivable;
    private double totalAccountPayable;
    private String opName;
    private String invoiceExtractionCode;
    private String qrcodeUrl;
    private String invoiceUr;
    private String invoiceDateExpire;
    private List<DataBean> data;

    public String getInvoiceUr() {
        return invoiceUr;
    }

    public void setInvoiceUr(String invoiceUr) {
        this.invoiceUr = invoiceUr;
    }

    public String getInvoiceDateExpire() {
        return invoiceDateExpire;
    }

    public void setInvoiceDateExpire(String invoiceDateExpire) {
        this.invoiceDateExpire = invoiceDateExpire;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getCounterNum() {
        return counterNum;
    }

    public void setCounterNum(String counterNum) {
        this.counterNum = counterNum;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public double getTotalAccountReceivable() {
        return totalAccountReceivable;
    }

    public void setTotalAccountReceivable(double totalAccountReceivable) {
        this.totalAccountReceivable = totalAccountReceivable;
    }

    public double getTotalAccountPayable() {
        return totalAccountPayable;
    }

    public void setTotalAccountPayable(double totalAccountPayable) {
        this.totalAccountPayable = totalAccountPayable;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getInvoiceExtractionCode() {
        return invoiceExtractionCode;
    }

    public void setInvoiceExtractionCode(String invoiceExtractionCode) {
        this.invoiceExtractionCode = invoiceExtractionCode;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * payName : 充值
         * chargeMoney : 20000
         * chargeDate : 2019-07-03 19:07:00
         */

        private String payName;
        private String chargeDate;
        private double num;
        private double price;
        private double chargeMoney;

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
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
            return "DataBean{" +
                    "payName='" + payName + '\'' +
                    ", chargeMoney='" + chargeMoney + '\'' +
                    ", chargeDate='" + chargeDate + '\'' +
                    ", num=" + num +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InvoiceBeen{" +
                "sign='" + sign + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", orgid='" + orgid + '\'' +
                ", meterCode='" + meterCode + '\'' +
                ", counterNum='" + counterNum + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payNum='" + payNum + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", totalAccountReceivable='" + totalAccountReceivable + '\'' +
                ", totalAccountPayable='" + totalAccountPayable + '\'' +
                ", opName='" + opName + '\'' +
                ", invoiceExtractionCode='" + invoiceExtractionCode + '\'' +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", invoiceUr='" + invoiceUr + '\'' +
                ", invoiceDateExpire='" + invoiceDateExpire + '\'' +
                ", data=" + data +
                '}';
    }
}
