package com.chaeum.api.global.pagination.cursorResult;

import com.chaeum.api.global.pagination.provider.CreatedAtProvider;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CreatedAtCursorResult<T extends CreatedAtProvider> {

    private final List<T> values;
    private final Boolean hasPrevious;
    private final Boolean hasNext;

    public CreatedAtCursorResult(List<T> values, Boolean hasPrevious, Boolean hasNext) {
        this.values = values;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
    }

    public static <T extends CreatedAtProvider> CreatedAtCursorResult<T> of(
        List<T> values, LocalDateTime cursor, int limit
    ) {
        boolean hasPrevious = checkFirstPageByCreatedAt(cursor, values);
        boolean hasNext = checkLastPageByCreatedAt(limit, values);
        return new CreatedAtCursorResult<>(values, hasPrevious, hasNext);
    }

    private static <T extends CreatedAtProvider> boolean checkFirstPageByCreatedAt(
        LocalDateTime cursor, List<T> values
    ) {
        if ((cursor != null) && !values.isEmpty() && (values.getFirst().getCreatedAt().isAfter(cursor))) {
            return true;
        }
        return false;
    }

    private static <T extends CreatedAtProvider> boolean checkLastPageByCreatedAt(int limit, List<T> values) {
        if (values.size() > limit) {
            values.subList(limit, values.size()).clear();
            return true;
        }
        return false;
    }
}
