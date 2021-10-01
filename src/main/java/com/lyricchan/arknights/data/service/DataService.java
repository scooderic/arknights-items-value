package com.lyricchan.arknights.data.service;

import cn.hutool.json.JSONObject;

import java.util.Map;

/**
 * 加载明日方舟数据
 * @author Lyric
 * @since 2021-10-01
 */
public interface DataService {

    /**
     * 获取所有掉落物信息，返回JSONObject
     */
    JSONObject getAllItemsAsJSONObject() throws Exception;

    /**
     * 根据物品ID查询物品
     */
    Map<String, Object> getItemByItemId(String itemId) throws Exception;

    /**
     * 根据关卡ID查询关卡
     */
    JSONObject getStageByStageId(String stageId) throws Exception;
}
