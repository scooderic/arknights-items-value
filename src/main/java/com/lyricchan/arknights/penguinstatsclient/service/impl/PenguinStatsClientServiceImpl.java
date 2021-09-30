package com.lyricchan.arknights.penguinstatsclient.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixRequest;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixResponse;
import com.lyricchan.arknights.penguinstatsclient.service.PenguinStatsClientService;
import org.springframework.stereotype.Service;

@Service("penguinStatsClientService")
public class PenguinStatsClientServiceImpl implements PenguinStatsClientService {

    private static final String PENGUIN_STATS_MATRIX_API_URL = "https://penguin-stats.io/PenguinStats/api/v2/result/matrix";

    @Override
    public MatrixResponse getMatrix(MatrixRequest matrixRequest) throws Exception {
        String responseStr = HttpUtil.get(PENGUIN_STATS_MATRIX_API_URL, BeanUtil.beanToMap(matrixRequest, false, true));
        return JSONUtil.toBean(responseStr, MatrixResponse.class);
    }
}
