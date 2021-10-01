package com.lyricchan.arknights.penguinstatsclient.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixRequest;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixResponse;
import com.lyricchan.arknights.penguinstatsclient.service.PenguinStatsClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("penguinStatsClientService")
public class PenguinStatsClientServiceImpl implements PenguinStatsClientService {

    private static final String PENGUIN_STATS_MATRIX_API_URL = "https://penguin-stats.io/PenguinStats/api/v2/result/matrix";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public MatrixResponse getMatrix(MatrixRequest matrixRequest) throws Exception {
        long start = System.currentTimeMillis();
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "matrix" + File.separator + "matrix-" + dateFormat.format(new Date()) + ".txt";
        boolean exist = FileUtil.exist(filePath);
        String responseStr;
        if (exist) {
            logger.info("将从文件读取Matrix");
            responseStr = FileUtil.readString(filePath, StandardCharsets.UTF_8);
        } else {
            logger.info("将从远程（企鹅物流）读取Matrix");
            responseStr = HttpUtil.get(PENGUIN_STATS_MATRIX_API_URL, BeanUtil.beanToMap(matrixRequest, false, true));
            File tempFile = FileUtil.touch(new File(filePath));
            FileUtil.writeString(responseStr, tempFile, "UTF-8");
        }
        MatrixResponse response = JSONUtil.toBean(responseStr, MatrixResponse.class);
        logger.info("读取Matrix耗时{} ms", (System.currentTimeMillis() - start));
        return response;
    }
}
