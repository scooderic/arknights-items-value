package com.lyricchan.arknights.calculator.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.lyricchan.arknights.calculator.entity.Item;
import com.lyricchan.arknights.calculator.entity.Price;
import com.lyricchan.arknights.calculator.entity.Report;
import com.lyricchan.arknights.calculator.service.CalculatorService;
import com.lyricchan.arknights.data.service.DataService;
import com.lyricchan.arknights.penguinstatsclient.entity.Matrix;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixRequest;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixResponse;
import com.lyricchan.arknights.penguinstatsclient.service.PenguinStatsClientService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PenguinStatsClientService penguinStatsClientService;
    private DataService dataService;

    @Autowired
    public CalculatorServiceImpl(PenguinStatsClientService penguinStatsClientService, DataService dataService) {
        this.penguinStatsClientService = penguinStatsClientService;
        this.dataService = dataService;
    }

    @Override
    public Price getAllPrices() throws Exception {
        Price price = new Price();
        // 默认按绿票加载价值
        price.setBzrrjt3(40f);
        price.setChjt3(40f);
        price.setGyyzt3(25f);
        price.setHhqxyt3(40f);
        price.setJszzt3(30f);
        price.setJtyjt3(30f);
        price.setNjt3(40f);
        price.setNzct3(30f);
        price.setQmkt3(35f);
        price.setQxzzt3(45f);
        price.setRma7012t3(45f);
        price.setTnjzt3(35f);
        price.setTzt3(30f);
        price.setYmst3(40f);
        price.setYtzt3(35f);
        return price;
    }

    @Override
    public List<Report> getFullReport(Map<String, String> paramMap) throws Exception {
        MatrixResponse matrixResponse = penguinStatsClientService.getMatrix(new MatrixRequest());
        if (matrixResponse == null) {
            return Collections.emptyList();
        }
        List<Matrix> matrixList = matrixResponse.getMatrix();
        Map<String, Float> priceMap = convertParamMapToPriceMap(paramMap);
        Map<String, List<Item>> stageItemListMap = convertMatrixListToStageItemListMap(matrixList, priceMap);
        List<Report> reportList = convertStageItemListMapToReportList(stageItemListMap);
        reportList.sort((l, r) -> Float.compare(r.getTotalValue(), l.getTotalValue()));
        return reportList;
    }

    /**
     * 将web入参转为物品价值表，key：itemId，value：物品价值系数
     */
    private Map<String, Float> convertParamMapToPriceMap(Map<String, String> paramMap) {
        Map<String, Float> priceMap = new HashMap<>(32);
        Set<Map.Entry<String, String>> entrySet = paramMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String entryValue = entry.getValue();
            priceMap.put(entry.getKey(), StringUtils.isEmpty(entryValue) ? 0.0f : Float.parseFloat(entryValue));
        }
        return priceMap;
    }

    /**
     * 将matrixList转化为以关卡为分组的掉落物列表
     */
    private Map<String, List<Item>> convertMatrixListToStageItemListMap(List<Matrix> matrixList, Map<String, Float> priceMap) throws Exception {
        Map<String, List<Item>> stageItemListMap = new HashMap<>(32);
        for (Matrix matrix : matrixList) {
            int times = matrix.getTimes();
            // 如果刷的次数不足200，那么不计入（样本数太少）
            if (times < 200) {
                continue;
            }
            String stageId = matrix.getStageId();
            if (!isValidStageId(stageId)) {
                continue;
            }
            JSONObject stage = dataService.getStageByStageId(stageId);
            // 体力消耗
            float cost = MapUtils.getFloatValue(stage, "apCost");
            if (cost <= 0.0f) {
                logger.info("无法确定关卡体力消耗值：" + stageId);
                continue;
            }
            String itemId = matrix.getItemId();
            int quantity = matrix.getQuantity();
            // 掉率
            float probability = 1.0f * quantity / times;
            Float itemPriceObj = priceMap.get(itemId);
            float itemPrice = (itemPriceObj == null) ? 0.0f : itemPriceObj;
            // 关卡该物品价值 = 100 * 掉率 * 物品价值 / 体力消耗
            float value = 100.0f * probability * itemPrice / cost;
            List<Item> itemList = stageItemListMap.get(stageId);
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            Map<String, Object> itemMap = dataService.getItemByItemId(itemId);
            Item item = new Item();
            item.setItemId(itemId);
            item.setItemName(MapUtils.getString(itemMap, "name"));
            item.setProbability(probability);
            item.setValue(value);
            itemList.add(item);
            stageItemListMap.put(stageId, itemList);
        }
        return stageItemListMap;
    }

    /**
     * 过滤关卡，只保留主线和活动
     */
    private boolean isValidStageId(String stageId) {
        if (StringUtils.isEmpty(stageId)) {
            return false;
        }
        if (stageId.length() < 5) {
            return false;
        }
        char c = stageId.charAt(0);
        if (c == 'a') {
            return stageId.startsWith("act");
        }
        if (c == 'm') {
            return stageId.startsWith("main");
        }
        if (c == 's') {
            return stageId.startsWith("sub");
        }
        return false;
    }

    /**
     * 将以关卡分组的掉落物列表，转化为结果（关卡总价值）报表
     */
    private List<Report> convertStageItemListMapToReportList(Map<String, List<Item>> stageItemListMap) throws Exception {
        List<Report> reportList = new ArrayList<>(50);
        Set<Map.Entry<String, List<Item>>> entrySet = stageItemListMap.entrySet();
        for (Map.Entry<String, List<Item>> entry : entrySet) {
            String stageId = entry.getKey();
            List<Item> itemList = entry.getValue();
            Report report = new Report();
            report.setStageId(stageId);
            float totalValue = 0.0f;
            for (Item item : itemList) {
                totalValue += item.getValue();
            }
            JSONObject stageDetail = dataService.getStageByStageId(stageId);
            if (stageDetail != null) {
                JSONArray dropInfos = stageDetail.getJSONArray("dropInfos");
                if (CollectionUtils.isEmpty(dropInfos)) {
                    report.setMainItemName("");
                } else {
                    final List<String> itemIds = new ArrayList<>(4);
                    final List<String> itemNames = new ArrayList<>(4);
                    Iterable<JSONObject> jsonIter = dropInfos.jsonIter();
                    jsonIter.forEach(jsonObject -> {
                        String dropType = jsonObject.getStr("dropType");
                        if ("NORMAL_DROP".equals(dropType)) {
                            try {
                                String itemId = jsonObject.getStr("itemId");
                                if (StringUtils.isNotEmpty(itemId)) {
                                    Map<String, Object> itemDetail = dataService.getItemByItemId(itemId);
                                    String itemName = MapUtils.getString(itemDetail, "name", itemId);
                                    itemIds.add(itemId);
                                    itemNames.add(itemName);
                                }
                            } catch (Exception e) {
                                logger.error("获取物品信息失败", e);
                            }
                        }
                    });
                    report.setMainItemId(StringUtils.join(itemIds, ','));
                    report.setMainItemName(StringUtils.join(itemNames, '+'));
                }
                String stageName = MapUtils.getString(stageDetail, "code", stageId);
                if (isPermStage(stageId)) {
                    stageName += " (常驻)";
                }
                report.setStageName(stageName);
            }
            report.setTotalValue(totalValue);
            reportList.add(report);
        }
        return reportList;
    }

    /**
     * 判断是否为常驻活动关卡（插曲/别传）
     */
    private boolean isPermStage(String stageId) {
        return StringUtils.endsWith(stageId, "perm");
    }
}
