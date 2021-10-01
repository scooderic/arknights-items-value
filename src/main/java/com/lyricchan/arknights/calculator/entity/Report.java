package com.lyricchan.arknights.calculator.entity;

public class Report implements java.io.Serializable {

    /** 关卡ID */
    private String stageId;

    /** 关卡名称 */
    private String stageName;

    /** 关卡主掉落物ID */
    private String mainItemId;

    /** 关卡主掉落物名称 */
    private String mainItemName;

    /** 关卡总价值 */
    private float totalValue;

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getMainItemId() {
        return mainItemId;
    }

    public void setMainItemId(String mainItemId) {
        this.mainItemId = mainItemId;
    }

    public String getMainItemName() {
        return mainItemName;
    }

    public void setMainItemName(String mainItemName) {
        this.mainItemName = mainItemName;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }
}
