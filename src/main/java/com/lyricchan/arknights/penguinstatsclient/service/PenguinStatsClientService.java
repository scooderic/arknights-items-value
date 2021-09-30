package com.lyricchan.arknights.penguinstatsclient.service;

import com.lyricchan.arknights.penguinstatsclient.entity.MatrixRequest;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixResponse;

public interface PenguinStatsClientService {

    /**
     * <p>从企鹅物流获取掉落数据</p>
     */
    MatrixResponse getMatrix(MatrixRequest matrixRequest) throws Exception;
}
