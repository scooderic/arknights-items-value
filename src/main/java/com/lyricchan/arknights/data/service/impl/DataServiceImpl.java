package com.lyricchan.arknights.data.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lyricchan.arknights.data.service.DataService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("dataService")
public class DataServiceImpl implements DataService {

    private static JSONObject itemsJSONObject;
    private static JSONArray stagesJSONArray;

    @Override
    public JSONObject getAllItemsAsJSONObject() throws Exception {
        if (itemsJSONObject == null) {
            loadItemsJSONObject();
        }
        return itemsJSONObject;
    }

    @Override
    public Map<String, Object> getItemByItemId(String itemId) throws Exception {
        if (itemsJSONObject == null) {
            loadItemsJSONObject();
        }
        return itemsJSONObject.getJSONObject(itemId);
    }

    @Override
    public JSONObject getStageByStageId(String stageId) throws Exception {
        if (stagesJSONArray == null) {
            loadStagesJSONArray();
        }
        for (Object o : stagesJSONArray) {
            if (o instanceof JSONObject) {
                JSONObject m = (JSONObject) o;
                String id = m.getStr("stageId", "");
                if (id.equals(stageId)) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * 从文件加载物品信息
     */
    private void loadItemsJSONObject() throws Exception {
        File file = ResourceUtils.getFile(ResourceUtils.getURL("classpath:data/item_table.json"));
        JSONObject json = JSONUtil.readJSONObject(file, StandardCharsets.UTF_8);
        itemsJSONObject = json.getJSONObject("items");
    }

    /**
     * 从文件加载关卡信息
     */
    private void loadStagesJSONArray() throws Exception {
        File file = ResourceUtils.getFile(ResourceUtils.getURL("classpath:data/stages_penguinstats.json"));
        stagesJSONArray = JSONUtil.readJSONArray(file, StandardCharsets.UTF_8);
    }
}
