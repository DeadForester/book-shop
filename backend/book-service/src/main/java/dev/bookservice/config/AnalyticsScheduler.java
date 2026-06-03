package dev.bookservice.config;

import dev.bookservice.analytics.service.order.OrderAnalyticsSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AnalyticsScheduler {

    private final OrderAnalyticsSyncService syncService;

    /**
     * Запускает инкрементальную синхронизацию каждые 15 минут.
     */
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES, zone = "Europe/Moscow")
    public void syncIncremental() {
        try {
            log.debug("Запуск плановой синхронизации аналитики заказов");
            syncService.syncOrdersSince(java.time.LocalDateTime.now(ZoneId.of("Europe/Moscow")));
        } catch (Exception e) {
            log.error("Ошибка при синхронизации аналитики", e);
        }
    }

    /**
     * Запускает полную синхронизацию ежедневно в 03:00.
     */
    @Scheduled(cron = "0 0 3 * * *", zone = "Europe/Moscow")
    public void syncFull() {
        try {
            log.info("Запуск полной синхронизации аналитики");
            syncService.syncAllOrders();
        } catch (Exception e) {
            log.error("Ошибка при полной синхронизации", e);
        }
    }
}
