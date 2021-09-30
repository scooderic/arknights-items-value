package com.lyricchan.arknights.calculator.service.impl;

import com.lyricchan.arknights.calculator.entity.Price;
import com.lyricchan.arknights.calculator.service.CalculatorService;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixRequest;
import com.lyricchan.arknights.penguinstatsclient.entity.MatrixResponse;
import com.lyricchan.arknights.penguinstatsclient.service.PenguinStatsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    private PenguinStatsClientService penguinStatsClientService;

    @Autowired
    public CalculatorServiceImpl(PenguinStatsClientService penguinStatsClientService) {
        this.penguinStatsClientService = penguinStatsClientService;
    }

    @Override
    public Price getAllPrices() throws Exception {
        MatrixResponse matrix = penguinStatsClientService.getMatrix(new MatrixRequest());

        Price price = new Price();
        price.setBzrrjt3(25F);
        price.setChjt3(25F);
        price.setGyyzt3(25F);
        price.setHhqxyt3(25F);
        price.setJszzt3(25F);
        price.setJtyjt3(25.23984F);
        price.setNjt3(25F);
        price.setNzct3(25F);
        price.setQmkt3(25F);
        price.setQxzzt3(25F);
        price.setRma7012t3(25.2839F);
        price.setTnjzt3(25F);
        price.setTzt3(25F);
        price.setYmst3(25F);
        price.setYtzt3(25F);
        return price;
    }
}
