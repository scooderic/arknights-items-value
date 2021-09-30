package com.lyricchan.arknights.calculator.service;

import com.lyricchan.arknights.calculator.entity.Price;

public interface CalculatorService {

    /**
     * 获得所有的价值表
     */
    Price getAllPrices() throws Exception;
}
