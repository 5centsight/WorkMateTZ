
<div align="center">
  <img src=".github/previews/logo.svg" width="100%" height="200px" alt="Логотип WorkMate TZ">

# WorkMateTZ

</div>

[Тестовое задание](https://docs.yandex.ru/docs/view?url=ya-disk-public%3A%2F%2FkpCSEs3sgY3o5V%2FgjWqZIc8txxp%2FhUcIeB3TTG59e9SxeCZElWVnPk57yhvFQTpuq%2FJ6bpmRyOJonT3VoXnDag%3D%3D&name=Workmate%20%D0%A2%D0%97%20(RandomUser).pdf) для [WorkMate](https://work-mate.ru/), написанное на языке программирования **Kotlin** с использованием архитектуры **MVVM (Model-View-ViewModel)** с **Single Activity** и UI на **Jetpack Compose**.

Android-приложение для добавления и хранения сгенерированных пользователей.
В качестве источника данных используется [Random User API](https://randomuser.me).

<img src=".github/previews/randomUser.gif" width="300" align="right" style="margin-left: 50px;" alt="Демонстрация работы приложения">

## Функциональность
- **Текущие юзеры** – отображение списка добавленных ранее пользователей
- **Генерация юзеров** – получение и парсинг полученных от API данных
- **Детальная информация** - расширенная информация о юзере
- **Оффлайн-режим** – работа без интернета с использованием кешированных данных

## Стек технологий и библиотек
- Минимальный **Android SDK** – 24
- На основе **Kotlin**, **Coroutines** + **Flow**
- **Jetpack**
    - **Compose** – Современный декларативный UI-фреймворк
    - **Navigation Compose** – Навигация между composable-экранами
    - **Room** – ORM библиотека для работы с локальной БД
- **Архитектура**
    - **MVVM (Model-View-ViewModel)**
    - **Clean Architecture** с разделением на слои
    - **Repository Pattern**
- **Retrofit 2** – REST API клиент
- **Dagger Hilt** – DI-фреймворк, рекомендованный Google
- **Kotlin Serialization** – JSON сериализация
- **Material Design 3** – Современная дизайн-система, созданная с помощью Material Theme Builder 