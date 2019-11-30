package com.waakka.myshowview.util;

import android.text.TextUtils;
import android.util.Log;

import com.waakka.myshowview.been.InvoiceBeen;
import com.waakka.myshowview.been.ItemBeen;
import com.waakka.myshowview.been.JsonBeen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static JsonBeen getBeen(String json){
        JsonBeen been = new JsonBeen();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (!code.equals("0")){
                Log.e("TAG","请求失败==>"+message);
                return null;
            }
            JSONObject obj = jsonObject.getJSONObject("data");
            been.setCounterNum(obj.getString("counterNum"));
            been.setFromTime(obj.getString("fromTime"));
            been.setToTime(obj.getString("toTime"));
            been.setMeterCode(obj.getString("meterCode"));
            been.setOrgid(obj.getString("orgid"));
            been.setSign(obj.getString("sign"));
            been.setUserid(obj.getString("userid"));
            been.setUsername(obj.getString("username"));
            been.setQrcodeUrl(obj.getString("qrcodeUrl"));

            List<ItemBeen> data = new ArrayList<>();

            double tNum = 0.0;
            double tM = 0.0;
            JSONArray array = obj.getJSONArray("data");
            if (array.length() > 0 ){
                for (int i = 0 ; i < array.length() ; i++){
                    ItemBeen b = new ItemBeen();
                    JSONObject dataObj = array.getJSONObject(i);
                    b.setChargeDate(dataObj.getString("chargeDate"));

                    String priceStr = dataObj.getString("price");
                    String chargeNumStr = dataObj.getString("chargeNum");
                    String chargeMoneyStr = dataObj.getString("chargeMoney");

                    double price = TextUtils.isEmpty(priceStr)? 0.0 : Double.parseDouble(priceStr);
                    double chargeNum = TextUtils.isEmpty(chargeNumStr)? 0.0 : Double.parseDouble(chargeNumStr);
                    double chargeMoney = TextUtils.isEmpty(chargeMoneyStr)? 0.0 : Double.parseDouble(chargeMoneyStr);
                    price /= 100;
                    chargeMoney /= 100;
                    b.setPrice(price);
                    b.setChargeNum(chargeNum);
                    b.setChargeMoney(chargeMoney);
                    tNum += chargeNum;
                    tM += chargeMoney;
                    data.add(b);
                }
            }
            been.setTotalChargeMoney(tM);
            been.setTotalChargeNum(tNum);
            been.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return been;
    }


    public static InvoiceBeen getInvoiceBeen(String json){
        InvoiceBeen been = new InvoiceBeen();
        try {
            JSONObject obj = new JSONObject(json);
            been.setSign(obj.getString("sign"));
            been.setUserid(obj.getString("userid"));
            been.setUsername(obj.getString("username"));
            been.setOrgid(obj.getString("orgid"));
            been.setMeterCode(obj.getString("meterCode"));
            been.setCounterNum(obj.getString("counterNum"));
            been.setPayTime(obj.getString("payTime"));
            been.setUnitPrice(obj.getString("unitPrice"));
            been.setPayMoney(obj.getString("payMoney"));
            double t1 = Double.parseDouble(obj.getString("totalAccountReceivable"));
            double t2 = Double.parseDouble(obj.getString("totalAccountPayable"));
            t1 /= 100;
            t2 /= 100;
            been.setTotalAccountReceivable(t1);
            been.setTotalAccountPayable(t2);
            been.setOpName(obj.getString("opName"));
            been.setInvoiceExtractionCode(obj.getString("invoiceExtractionCode"));
            been.setQrcodeUrl(obj.getString("qrcodeUrl"));
            been.setInvoiceUr(obj.getString("invoiceUrl"));
            been.setPayNum(obj.getString("payNum"));
            been.setInvoiceDateExpire(obj.getString("invoiceDateExpire"));



            List<InvoiceBeen.DataBean> data = new ArrayList<>();
            if (!TextUtils.isEmpty(been.getUnitPrice())){
                InvoiceBeen.DataBean b1 = new InvoiceBeen.DataBean();
                b1.setPayName("天然气");
                double price1 = Double.parseDouble(been.getUnitPrice());
                double chargeNum1 = Double.parseDouble(been.getPayNum());
                double chargeMoney1 = Double.parseDouble(been.getPayMoney());
                price1 /= 100;
                chargeMoney1 /= 100;
                b1.setNum(chargeNum1);
                b1.setChargeDate(been.getPayTime());
                b1.setChargeMoney(chargeMoney1);
                b1.setPrice(price1);
                data.add(b1);
            }

            JSONArray array = obj.getJSONArray("data");
            if (array.length() > 0 ){
                for (int i = 0 ; i < array.length() ; i++){
                    InvoiceBeen.DataBean b = new InvoiceBeen.DataBean();
                    JSONObject dataObj = array.getJSONObject(i);
                    b.setPayName(dataObj.getString("payName"));
                    b.setChargeDate(dataObj.getString("chargeDate"));
                    double price = 0.0;
                    double chargeNum = 0.0;
                    double chargeMoney = Double.parseDouble(dataObj.getString("chargeMoney"));
                    chargeMoney /= 100;
                    b.setPrice(price);
                    b.setNum(chargeNum);
                    b.setChargeMoney(chargeMoney);
                    data.add(b);
                }
            }
            been.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return been;
    }
}
