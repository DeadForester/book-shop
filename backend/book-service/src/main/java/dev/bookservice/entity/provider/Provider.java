package dev.bookservice.entity.provider;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность поставщика (провайдера).
 * <p>
 * Представляет запись в таблице {@code PROVIDERS} и содержит справочные,
 * юридические и контактные данные о компании, поставляющей книги в магазин.
 * <p>
 * Для автоматической генерации геттеров, сеттеров, конструкторов и билдера
 * используются аннотации Lombok.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PROVIDERS")
public class Provider {

    /**
     * Уникальный идентификатор поставщика.
     * <p>Является первичным ключом таблицы {@code PROVIDERS}.
     */
    @Id
    private Long providerId;

    /**
     * Название поставщика или организации.
     * <p>Используется для отображения в интерфейсе, чеках покупок и внутренней аналитике.
     */
    private String title;

    /**
     * Юридический или фактический адрес поставщика.
     * <p>Может использоваться для логистики, документооборота и верификации контрагента.
     */
    private String address;

    /**
     * Контактный номер телефона для связи с поставщиком.
     * <p>Хранится в формате строки для поддержки различных масок и форматов номеров.
     */
    private String contactNumber;
}