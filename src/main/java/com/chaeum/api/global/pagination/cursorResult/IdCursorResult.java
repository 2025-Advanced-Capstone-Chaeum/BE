package com.chaeum.api.global.pagination.cursorResult;

import com.chaeum.api.global.pagination.provider.IdProvider;
import java.util.List;
import lombok.Getter;

@Getter
public class IdCursorResult<T extends IdProvider> {

    private final List<T> values;
    private final Boolean hasPrevious;
    private final Boolean hasNext;

    public IdCursorResult(List<T> values, Boolean hasPrevious, Boolean hasNext) {
        this.values = values;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
    }

    public static <T extends IdProvider> IdCursorResult<T> of(List<T> values, Long cursor, int limit) {
        boolean hasPrevious = checkFirstPageById(cursor, values);
        boolean hasNext = checkLastPageById(limit, values);
        return new IdCursorResult<>(values, hasPrevious, hasNext);
    }

    private static <T extends IdProvider> boolean checkFirstPageById(Long cursor, List<T> values) {
        return cursor != null
            && cursor > 0
            && !values.isEmpty()
            && values.getFirst().getId() > cursor;
    }

    private static <T extends IdProvider> boolean checkLastPageById(int limit, List<T> values) {
        if (values.size() > limit) {
            values.subList(limit, values.size()).clear();
            return true;
        }
        return false;
    }
}
