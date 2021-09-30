package com.lyricchan.arknights.calculator.web.controller;

import com.lyricchan.arknights.calculator.entity.Price;
import com.lyricchan.arknights.calculator.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lyric
 * @since 2021-09-30
 */
@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * <p>获取物品初始价值表</p>
     * <p>所谓初始价值，指的是通过某种算法得出的物品贵贱的量化，例如：轻锰矿-3.12、固源岩组-2.33</p>
     */
    @GetMapping(value = "/getAllPrices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Price> getAllPrices() {
        try {
            Price allPrices = calculatorService.getAllPrices();
            return ResponseEntity.ok(allPrices);
        } catch (Exception e) {
            logger.error("CalculatorController.getAllPrices", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
