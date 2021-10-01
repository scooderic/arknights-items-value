package com.lyricchan.arknights.calculator.entity;

/**
 * <p>以每关卡掉落该物品为粒度，物品实体，包含字段：</p>
 * <ul>
 *     <li>物品ID</li>
 *     <li>物品名</li>
 *     <li>关卡ID</li>
 *     <li>关卡名</li>
 *     <li>物品掉率（该关）</li>
 *     <li>物品总价值（该关）=该关掉率*价值系数</li>
 * </ul>
 * @author Lyric
 * @since 2021-10-01
 */
public class Item implements java.io.Serializable {

    /** 物品ID */
    private String itemId;

    /** 物品名称 */
    private String itemName;

    /** 物品掉率 */
    private float probability;

    /** 物品该关价值总量 */
    private float value;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
