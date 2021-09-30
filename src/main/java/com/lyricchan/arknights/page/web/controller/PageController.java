package com.lyricchan.arknights.page.web.controller;

import com.lyricchan.arknights.page.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳页的
 * @author Lyric
 * @since 2021-09-30
 */
@RequestMapping("/page")
public class PageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PageService pageService;

    @GetMapping(value = "/{name}")
    public ModelAndView toPage(@PathVariable("name") String name) {
        try {
            boolean valid = pageService.checkName(name);
            if (valid) {
                return new ModelAndView(name);
            } else {
                return new ModelAndView("400");
            }
        } catch (Exception e) {
            logger.error("将军金甲夜不脱", e);
            return new ModelAndView("500");
        }
    }
}
