package com.lyricchan.arknights.penguinstatsclient.entity;

public class Matrix implements java.io.Serializable {

    /*
    {
      "stageId":"main_01-07",
      "itemId":"30012",
      "quantity":164303,
      "times":131916,
      "start":1556676000000,
      "end":1589529600000
    }
    */

    private String stageId;

    private String itemId;

    /**
     * the number of dropped items during the interval
     */
    private int quantity;

    /**
     * the number of times this stage was played during the interval
     */
    private int times;

    /**
     * the left end (inclusive) of the interval
     */
    private long start;

    /**
     * the right end (exclusive) of the interval
     */
    private long end;

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
