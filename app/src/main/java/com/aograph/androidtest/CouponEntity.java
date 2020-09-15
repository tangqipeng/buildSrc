package com.aograph.androidtest;

/**
 * Created by tangqipeng
 * 2020-02-03
 * email: tangqipeng@aograph.com
 */
public class CouponEntity {

    private String coupon_type;
    private String coupon_id;
    private String amount;
    private String promotion;

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}
