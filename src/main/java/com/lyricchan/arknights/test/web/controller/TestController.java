package com.lyricchan.arknights.test.web.controller;

import com.lyricchan.arknights.data.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DataService dataService;

    @Autowired
    public TestController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(@RequestParam("itemId") String itemId) {
        try {
            long start = System.currentTimeMillis();
            Map<String, Object> map = dataService.getItemByItemId(itemId);
            logger.info("耗时：{} ms", (System.currentTimeMillis() - start));
            return ResponseEntity.ok(map.get("name"));
        } catch (Exception e) {
            logger.error("TestController.test", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
