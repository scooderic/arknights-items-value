package com.lyricchan.arknights.calculator.web.controller;

import com.lyricchan.arknights.calculator.entity.Price;
import com.lyricchan.arknights.calculator.entity.Report;
import com.lyricchan.arknights.calculator.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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

    /**
     * <p>获取关卡价值报表</p>
     * <p>价值报表是每一关根据所提供的物品初始价值得出的价值结论</p>
     */
    @PostMapping(value = "/getFullReport", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Report>> getFullReport(@RequestParam Map<String, String> paramMap) {
        try {
            List<Report> reportList = calculatorService.getFullReport(paramMap);
            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            logger.error("CalculatorController.getFullReport", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
