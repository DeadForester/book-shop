# Book Shop Frontend

Фронтенд-часть интернет-магазина книг. Приложение предоставляет каталог книг, корзину, авторизацию/регистрацию, личный кабинет, историю заказов и функционал заказа книг у поставщиков для пополнения склада.

## Технологический стек

| Технология                        | Назначение                                      |
|-----------------------------------|-------------------------------------------------|
| **React 19**                      | Библиотека для построения пользовательского интерфейса |
| **TypeScript**                    | Статическая типизация                           |
| **Vite 8**                        | Сборщик и инструмент разработки (HMR, быстрая сборка) |
| **React Router 7**                | Клиентская маршрутизация (SPA)                 |
| **MUI (Material UI) 9**           | Библиотека компонентов и иконок                 |
| **Emotion**                       | CSS-in-JS стилизация                           |
| **Redux Toolkit**                 | Управление состоянием приложения                |
| **Axios**                         | HTTP-клиент для взаимодействия с бэкендом       |
| **Embala Carousel**               | Карусель для слайдеров                          |
| **Zod**                           | Схемы валидации данных                          |
| **ESLint**                        | Линтер для контроля качества кода               |
| **Prettier**                      | Форматировщик кода                              |
| **Docker**                        | Контейнеризация приложения                      |

## Структура проекта (основные директории)

```
book-shop-front/
├── src/
│   ├── api/            # HTTP-сервисы (auth, book, order, publisher, purchase, user, warehouse)
│   ├── components/     # UI-компоненты по разделам (basket, book-id-page, books-page,
│   │                   #   order-history-page, profile-page, supplier-order-page)
│   ├── data/           # Моковые данные (товары, поставщики, пользователи, генератор заказов)
│   ├── hooks/          # Кастомные хуки (useAppDispatch, useAppSelector, useAppStore,
│   │                   #   useDebounce, useIsMobile)
│   ├── pages/          # Компоненты страниц (BookIdPage, Books, Error, Login,
│   │                   #   OrderHistory, Profile, Register, SupplierOrder)
│   ├── router/         # Конфигурация маршрутов SPA
│   ├── shared/         # Общие компоненты, схемы (Zod) и типы
│   ├── store/          # Redux store и reducers
│   ├── styles/         # Глобальные стили (CSS)
│   └── utils/          # Утилиты (валидация)
├── public/             # Статические файлы (favicon)
├── Dockerfile          # Docker-образ
├── eslint.config.ts    # Конфигурация ESLint
├── tsconfig.json       # Конфигурация TypeScript
└── vite.config.ts      # Конфигурация Vite
```

## Запуск проекта

### Локальная разработка

```bash
npm install
npm run dev
```

Приложение будет доступно по адресу, который выведет Vite в терминале (по умолчанию `http://localhost:5173`).

### Сборка для продакшена

```bash
npm run build
npm run preview
```

### Проверка типов и линтинг

```bash
npm run type-check   # Проверка TypeScript
npm run lint         # Проверка ESLint
npm run lint:fix     # Автоматическое исправление ESLint
```

### Запуск через Docker

```bash
docker build -t book-shop-front .
docker run -d -p 8080:8080 --name book-shop-front book-shop-front
```

Приложение будет доступно по адресу `http://localhost:8080`.