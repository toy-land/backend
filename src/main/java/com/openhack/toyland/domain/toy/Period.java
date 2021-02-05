package com.openhack.toyland.domain.toy;

import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

public enum Period {
    LESS_THAN_A_DAY(0L),
    LESS_THAN_THREE_DAYS(1L),
    LESS_THAN_A_WEEK(2L),
    LESS_THAN_TWO_WEEKS(3L),
    LESS_THAN_A_MONTH(4L),
    LESS_THAN_THREE_MONTHS(5L),
    LESS_THAN_SIX_MONTHS(6L),
    LESS_THAN_A_YEAR(7L),
    MORE_THAN_A_YEAR(8L);

    private final Long index;

    Period(Long index) {
        this.index = index;
    }

    public static Period of(Long index) {
        return Arrays.stream(values())
            .filter(it -> it.index.equals(index))
            .findFirst()
            .orElseThrow(EntityNotFoundException::new);
    }
}
