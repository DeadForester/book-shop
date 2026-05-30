#!/bin/bash

#Сборка проекта на docker-compose.yaml

set -e

echo "=== Развертывание проекта через docker-compose.yaml ==="

echo "Сборка образа бекенда"

docker compose build --pull backend

echo "Сборка образа фронтенда"

docker compose build --pull frontend

echo "Запуск всех сервисов проекта"

if ! docker compose up -d; then
  ./send-error.sh
fi

sleep 5

timeout 60s bash -c 'until docker compose ps | grep -q "healthy"; do sleep 2; done' || {
  ./send-error.sh
}

echo "Все сервисы успешно запущены и работают"

docker compose ps

echo "=== Сборка прошла успешно ==="