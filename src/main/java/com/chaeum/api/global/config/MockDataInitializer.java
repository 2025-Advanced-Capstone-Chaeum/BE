package com.chaeum.api.global.config;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "test"})
@RequiredArgsConstructor
public class MockDataInitializer {

    private final DataSource dataSource;

    private static final String[] SCRIPT_PATHS = {
        "data/01-member.sql",
        "data/02-item.sql",
        "data/03-inventory.sql",
        "data/04-funding.sql",
        "data/05-donation.sql",
        "data/06-mission.sql",
        "data/07-member-mission.sql",
        "data/08-cat.sql",
        "data/09-attendance.sql",
        "data/10-friendship.sql",
        "data/11-uploaded_file.sql",
        "data/12-review.sql",
        "data/13-title.sql",
        "data/14-notification.sql",
        "data/15-payment-record.sql"
    };

    @PostConstruct
    public void initMockData() {
        try (Connection conn = dataSource.getConnection()) {
            for (String path : SCRIPT_PATHS) {
                Resource resource = new ClassPathResource(path);
                log.info("목업 SQL 스크립트 로딩 중: {}", path);
                ScriptUtils.executeSqlScript(conn, resource);
            }
            log.info("모든 목업 데이터 스크립트가 성공적으로 로딩되었습니다.");
        } catch (Exception e) {
            log.error("목업 데이터 스크립트 로딩에 실패했습니다.", e);
            throw new IllegalStateException("목업 데이터 초기화 실패", e);
        }
    }
}
