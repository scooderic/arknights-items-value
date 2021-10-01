package com.lyricchan.arknights.calculator.service;

import com.lyricchan.arknights.calculator.entity.Price;
import com.lyricchan.arknights.calculator.entity.Report;

import java.util.List;
import java.util.Map;

/**
 * @author Lyric
 * @since 2021-09-30
 */
public interface CalculatorService {

    /**
     * 获得所有的物品价值表
     */
    Price getAllPrices() throws Exception;

    /**
     * 获得所有的关卡价值报表
     */
    List<Report> getFullReport(Map<String, String> paramMap) throws Exception;
}
