package com.chaeum.api.domain.cat.strategy;

import java.math.BigInteger;

public class PowerLevelUpStrategy implements LevelUpStrategy {

    private final int baseExp;
    private final double exponent;

    public PowerLevelUpStrategy(int baseExp, double exponent) {
        this.baseExp = baseExp;
        this.exponent = exponent;
    }

    @Override
    public BigInteger getRequiredExp(int level) {
        double result = baseExp * Math.pow(level, exponent);
        return BigInteger.valueOf((long) result);
    }
}
