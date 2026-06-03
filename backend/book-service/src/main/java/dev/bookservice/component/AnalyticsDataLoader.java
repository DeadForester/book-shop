package dev.bookservice.component;

import dev.bookservice.analytics.service.order.OrderAnalyticsSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalyticsDataLoader {

    private final OrderAnalyticsSyncService syncService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("=== Приложение полностью запущено. Начало синхронизации аналитики ===");
        try {
            syncService.syncAllOrders();
            log.info("=== Синхронизация аналитики завершена успешно ===");
        } catch (Exception e) {
            log.error("Ошибка при синхронизации аналитики", e);
        }
    }
}
