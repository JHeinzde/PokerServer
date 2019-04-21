package org.bonn.pokerserver.poker.game;

import java.math.BigDecimal;

public enum StakeLevel {

    TWO(BigDecimal.valueOf(2)),
    FIVE(BigDecimal.valueOf(5)),
    TEN(BigDecimal.valueOf(10)),
    TWENTY(BigDecimal.valueOf(20)),
    FIFTY(BigDecimal.valueOf(50)),
    ONE_HUNDRET(BigDecimal.valueOf(100)),
    ONE_HUNDRET_FIFTY(BigDecimal.valueOf(150)),
    THREE_HUNDRED(BigDecimal.valueOf(300));

    private final BigDecimal numericLevel;

    StakeLevel(BigDecimal numericLevel) {
        this.numericLevel = numericLevel;
    }

    public BigDecimal getNumericLevel() {
        return numericLevel;
    }
}
