package com.example.productsearchapp.pojo;

public class DetailItemShipping {
    private String storeName;
    private String storeUrl;
    private String feedbackScore;
    private String popularity;
    private String feedbackStar;
    private String shippingCost;
    private String globalShipping;
    private String handlingTime;
    private String condition;
    private String policy;
    private String returnsWithin;
    private String refundMode;
    private String shippedBy;

    public DetailItemShipping(){};
    public DetailItemShipping(String storeName, String storeUrl, String feedbackScore, String popularity, String feedbackStar, String shippingCost, String globalShipping, String handlingTime, String condition, String policy, String returnsWithin, String refundMode, String shippedBy) {
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.feedbackScore = feedbackScore;
        this.popularity = popularity;
        this.feedbackStar = feedbackStar;
        this.shippingCost = shippingCost;
        this.globalShipping = globalShipping;
        this.handlingTime = handlingTime;
        this.condition = condition;
        this.policy = policy;
        this.returnsWithin = returnsWithin;
        this.refundMode = refundMode;
        this.shippedBy = shippedBy;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(String feedbackScore) {
        this.feedbackScore = feedbackScore;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getFeedbackStar() {
        return feedbackStar;
    }

    public void setFeedbackStar(String feedbackStar) {
        this.feedbackStar = feedbackStar;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getGlobalShipping() {
        return globalShipping;
    }

    public void setGlobalShipping(String globalShipping) {
        this.globalShipping = globalShipping;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getReturnsWithin() {
        return returnsWithin;
    }

    public void setReturnsWithin(String returnsWithin) {
        this.returnsWithin = returnsWithin;
    }

    public String getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(String refundMode) {
        this.refundMode = refundMode;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }
}
