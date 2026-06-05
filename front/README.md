# Book Shop Frontend

Фронтенд-часть интернет-магазина книг. Приложение предоставляет каталог книг, корзину, авторизацию/регистрацию, личный
кабинет, историю заказов, панель управления (dashboard) и функционал заказа книг у поставщиков для пополнения склада.

## Технологический стек

| Технология              | Назначение                                                                          |
|-------------------------|-------------------------------------------------------------------------------------|
| **React 19**            | Библиотека для построения пользовательского интерфейса                              |
| **Vite 8**              | Сборщик и инструмент разработки (HMR, быстрая сборка)                               |
| **React Router 7**      | Клиентская маршрутизация (SPA)                                                      |
| **MUI (Material UI) 9** | Библиотека компонентов и иконок (@mui/material, @mui/icons-material, @mui/x-charts) |
| **Emotion**             | CSS-in-JS стилизация (@emotion/react, @emotion/styled)                              |
| **Axios**               | HTTP-клиент для взаимодействия с бэкендом                                           |
| **ESLint**              | Линтер для контроля качества кода                                                   |
| **Prettier**            | Форматировщик кода                                                                  |
| **Docker**              | Контейнеризация приложения                                                          |

## Структура проекта

```
book-shop-front/
├── public/                         # Статические файлы (favicon)
├── src/                            # Исходный код
│   ├── API/                        # Сервисы для работы с бэкендом
│   │   ├── AuthService.js          #   Аутентификация (логин, регистрация)
│   │   ├── BookService.js          #   CRUD книг (список, детали)
│   │   ├── OrderService.js         #   Заказы пользователей (список, создание)
│   │   ├── PublisherService.js     #   Издательства
│   │   ├── PurchaseService.js      #   Закупки у поставщиков
│   │   ├── UserService.js          #   Пользователи
│   │   └── WarehouseService.js     #   Складской учёт
│   ├── components/                 # UI-компоненты по разделам
│   │   ├── basket/                 #   Корзина
│   │   ├── book-id-page/           #   Страница книги
│   │   ├── books-page/             #   Каталог книг
│   │   ├── dashboard-page/         #   Панель управления / админка
│   │   ├── order-history-page/     #   История заказов
│   │   ├── profile-page/           #   Профиль пользователя
│   │   ├── supplier-order-page/    #   Заказ у поставщика
│   │   └── UI/                     #   Переиспользуемые UI-компоненты
│   ├── context/                    # React Context
│   │   ├── auth.js                 #   Контекст аутентификации
│   │   ├── basket.js               #   Контекст корзины
│   │   └── user.js                 #   Контекст пользователя
│   ├── data/                       # Моковые данные
│   │   ├── generateMockOrders.js   #   Генератор тестовых заказов
│   │   ├── goods.js                #   Товары (книги)
│   │   ├── providers.js            #   Поставщики
│   │   └── user.js                 #   Пользователи
│   ├── hooks/                      # Кастомные хуки
│   │   ├── useAuthContext.js       #   Доступ к контексту auth
│   │   ├── useBasketContext.js     #   Доступ к контексту корзины
│   │   ├── useFetching.js          #   Универсальный хук для запросов
│   │   └── useUserContext.js       #   Доступ к контексту пользователя
│   ├── pages/                      # Компоненты страниц
│   │   ├── BookIdPage.jsx          #   Страница отдельной книги
│   │   ├── Books.jsx               #   Каталог книг
│   │   ├── Dashboard.jsx           #   Панель управления / админка
│   │   ├── Error.jsx               #   Страница ошибки
│   │   ├── Login.jsx               #   Страница входа
│   │   ├── OrderHistory.jsx        #   История заказов
│   │   ├── Profile.jsx             #   Профиль пользователя
│   │   ├── Register.jsx            #   Страница регистрации
│   │   └── SupplierOrder.jsx       #   Заказ книг у поставщика
│   ├── router/                     # Конфигурация маршрутов
│   │   └── routes.js               #   Определение путей SPA
│   ├── shared/                     # Общие компоненты, контексты, утилиты
│   ├── styles/                     # Глобальные стили
│   │   └── index.css               #   Основной CSS-файл
│   ├── utils/                      # Утилиты
│   │   └── validateCredentials.js  #   Валидация email/пароля
│   ├── App.jsx                     # Корневой компонент приложения
│   └── main.jsx                    # Точка входа
├── .dockerignore
├── .gitignore
├── .prettierignore
├── .prettierrc                     # Конфигурация Prettier
├── Dockerfile                      # Docker-образ (многостадийная сборка)
├── eslint.config.js                # Конфигурация ESLint
├── index.html                      # HTML-шаблон
├── package.json                    # Зависимости и скрипты
└── vite.config.ts                  # Конфигурация Vite
```

## Запуск проекта

### Локальная разработка

```bash
# 1. Установка зависимостей
npm install

# 2. Запуск дев-сервера с HMR
npm run dev
```

Приложение будет доступно по адресу, который выведет Vite в терминале (по умолчанию `http://localhost:5173`).

### Сборка для продакшена

```bash
# Сборка проекта
npm run build

# Предпросмотр собранного проекта
npm run preview
```

### Запуск через Docker

```bash
# Сборка Docker-образа
docker build -t book-shop-front .

# Запуск контейнера
docker run -d -p 8080:8080 --name book-shop-front book-shop-front
```

Приложение будет доступно по адресу `http://localhost:8080`.

### Прочие скрипты

```bash
# Проверка кода линтером
npm run lint

# Автоматическое исправление ошибок линтера
npm run lint:fix
```

## API

Фронтенд взаимодействует с REST API бэкенда по адресу `http://localhost:8080/api/v1`. Основные эндпоинты:

### Аутентификация

- `POST /api/v1/login` — вход в систему (с куками)
- `POST /api/v1/registration` — регистрация нового пользователя

### Книги

- `GET /api/v1/books?page={page}&size={size}` — получение списка книг с пагинацией
- `GET /api/v1/books/{id}` — получение информации о конкретной книге

### Заказы

- `GET /api/v1/orders/me` — список заказов текущего пользователя
- `GET /api/v1/orders/{id}` — детали заказа
- `POST /api/v1/orders/create` — создание нового заказа

### Закупки (поставщики / склад)

- `GET /api/v1/purchases/{id}` — информация о закупке
- `POST /api/v1/purchases/create` — создание закупки
- `POST /api/v1/purchases/add_supply?id={id}` — дублирование поставки

### Пользователи

- `GET /api/v1/user/{id}` — получение данных пользователя

### Издательства

- `GET /api/v1/publishers/{id}` — получение информации об издательстве

### Склады

- `GET /api/v1/warehouses/{id}` — информация о складе
- `GET /api/v1/warehouses/{warehouseId}/books/{bookId}` — остаток книги на складе
