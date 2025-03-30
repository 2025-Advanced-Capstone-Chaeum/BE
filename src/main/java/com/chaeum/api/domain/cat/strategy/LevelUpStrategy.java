package com.chaeum.api.domain.cat.strategy;

import java.math.BigInteger;

public interface LevelUpStrategy {

    BigInteger getRequiredExp(int level);
}
