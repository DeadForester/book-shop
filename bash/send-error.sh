#!/bin/bash

echo "Один из сервисов упал!!!"

echo "Логи ошибки:"

docker compose logs --tail=20

./end-project.sh

exit 1
