package com.yuzhi.fine.pay.aliPay;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sky.
 */
public class BizContent {
//    参数 类型 是否必填 最大长度 描述 示例值
//    body String 否 128 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。 Iphone6 16G
//    subject String 是 256 商品的标题/交易标题/订单标题/订单关键字等。 大乐透
//    out_trade_no String 是 64 商户网站唯一订单号 70501111111S001111119
//    timeout_express String 否 6 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。 90m
//    total_amount String 是 9 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 9.00
//    seller_id String 否 16 收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID 2088102147948060
//    product_code String 是 64 销售产品码，商家和支付宝签约的产品码 QUICK_MSECURITY_PAY
    //---------------------------------
    //---------------------------------
    //该笔订单允许的最晚付款时间，逾期将关闭交易。
    //取值范围：1m～15d m-分钟，h-小时，d-天，1c-当天(1c-当天的情况下，无论交易何时创建，都在0点关闭)
    //该参数数值不接受小数点， 如1.5h，可转换为90m。
    private String timeoutExpress = "30m";//30m
    private String productCode = "QUICK_MSECURITY_PAY";//QUICK_MSECURITY_PAY
    private String totalAmount;//0.01
    private String subject;
    private String body;
    private String outTradeNO;//业务服务器单号

    public String toJsonString() {
        JSONObject json = new JSONObject();
        try {
            json.put("timeout_express", timeoutExpress);
            json.put("product_code", productCode);
            json.put("total_amount", totalAmount);
            json.put("subject", subject);
            json.put("body", body);
            json.put("out_trade_no", outTradeNO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNO() {
        return outTradeNO;
    }

    public void setOutTradeNO(String outTradeNO) {
        this.outTradeNO = outTradeNO;
    }
}
