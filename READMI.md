# REST API для управления бассейном

## Описание

REST API для управления записями на посещение бассейна. API позволяет:

- Регистрировать клиентов
- Создавать и управлять записями на посещение как самим клиентом так и админом
- Ограничивать количество записей в час (не более 10)
- Ограничивать количество посещений в день (не более 1)
- Искать записи по дате и времени 
- Получать доступные и занятые записи на определенную дату

## Для запуска требуется postgres, конфигурацию которого нужно указать в application.yaml
