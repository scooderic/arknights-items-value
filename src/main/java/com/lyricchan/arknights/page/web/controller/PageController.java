package com.lyricchan.arknights.page.web.controller;

import com.lyricchan.arknights.page.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳页的
 * @author Lyric
 * @since 2021-09-30
 */
@Controller
@RequestMapping("/page")
public class PageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * <p>通用的跳你妈功能</p>
     * <ul>
     *     <li>/-/xxx ~ 跳到templates/目录下的页面</li>
     *     <li>/yyy/xxx ~ 跳到templates/yyy/目录下的页面</li>
     * </ul>
     * @param folder 路径
     * @param name 文件名
     * @return mav
     */
    @GetMapping(value = "/{folder}/{name}")
    public ModelAndView toPage(@PathVariable("folder") String folder, @PathVariable("name") String name) {
        try {
            boolean valid = pageService.checkName(name);
            if (valid) {
                String path = folder + "/";
                if ("-".equals(folder)) {
                    path = "";
                }
                return new ModelAndView(path + name);
            } else {
                return new ModelAndView("400");
            }
        } catch (Exception e) {
            logger.error("呸呸呸的长处车船使用税", e);
            return new ModelAndView("500");
        }
    }
}
