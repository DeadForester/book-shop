package dev.bookservice.entity.book;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Перечисление типов переплёта книги.
 * <p>
 * Используется для типизации и валидации значений переплёта в приложении.
 * Каждый элемент содержит человекочитаемое название на русском языке,
 * доступное через метод {@link #getName()}.
 *
 * @see #getName()
 * @see #parseName(String)
 */
@Getter
@Slf4j
public enum Binding {
    /**
     * Мягкий переплёт — книга с гибкой обложкой, обычно из бумаги или картона.
     */
    SOFTCOVER("Мягкий переплет"),
    /**
     * Твёрдый переплёт — книга с жёсткой обложкой, повышенной износостойкости.
     */
    HARDCOVER("Твердый переплет");

    /**
     * Человекочитаемое название типа переплёта на русском языке.
     * <p>
     * Значение поля инициализируется один раз при создании экземпляра перечисления
     * и не может быть изменено в дальнейшем.
     */
    private final String name;


    Binding(String name) {
        this.name = name;
    }

    /**
     * Выполняет парсинг строкового значения в соответствующий элемент перечисления {@link Binding}.
     * <p>
     * Метод выполняет поиск по <strong>системному имени</strong> элемента (значению, возвращаемому
     * методом {@link Enum#name()}), а не по человекочитаемому полю {@link #name}.
     * Например, для получения {@link #SOFTCOVER} необходимо передать строку {@code "SOFTCOVER"}.
     * <p>
     * Алгоритм работы:
     * <ol>
     *     <li>Перебирает все значения перечисления через {@link #values()};</li>
     *     <li>Сравнивает переданное значение с {@link Enum#name()} каждого элемента с учётом регистра;</li>
     *     <li>При совпадении возвращает найденный элемент;</li>
     *     <li>Если совпадение не найдено — логирует ошибку и выбрасывает исключение.</li>
     * </ol>
     *
     * @param bindingName системное имя элемента перечисления (например, {@code "SOFTCOVER"} или {@code "HARDCOVER"})
     * @return соответствующий элемент {@link Binding}, если найден
     * @throws RuntimeException если переданное имя не соответствует ни одному из элементов перечисления
     * @see Enum#name()
     * @see #values()
     */
    public static Binding parseName(String bindingName) {
        for (Binding binding : Binding.values()) {
            if (binding.name().equals(bindingName)) {
                return binding;
            }
        }
        log.error("Данного переплета нет в Базе");
        throw new RuntimeException("Данного переплета нет в Базе " + bindingName);
    }
}
