# Решение проблемы с названием репозитория JitPack

## ❌ Проблема

Ошибка:
```
Could not find com.github.barteksc:android-pdf-viewer:2.8.2
```

## 🔍 Причина

Для JitPack важно правильное название репозитория. Название должно совпадать с названием репозитория на GitHub.

## ✅ Решение

### Правильное название репозитория

GitHub репозиторий: `barteksc/AndroidPdfViewer` (с заглавными буквами)

**Неправильно:**
```gradle
implementation 'com.github.barteksc:android-pdf-viewer:2.8.2'
```

**Правильно:**
```gradle
implementation 'com.github.barteksc:AndroidPdfViewer:2.8.2'
```

### Формат для JitPack

Формат: `com.github.USERNAME:REPOSITORY_NAME:TAG`

Где:
- `USERNAME` = `barteksc`
- `REPOSITORY_NAME` = `AndroidPdfViewer` (точно как на GitHub)
- `TAG` = версия/тег из GitHub (например, `2.8.2`)

## 📋 Доступные версии

Проверьте доступные версии на:
- https://jitpack.io/#barteksc/AndroidPdfViewer

Или на GitHub:
- https://github.com/barteksc/AndroidPdfViewer/releases

## 🔧 Альтернативные варианты

Если версия `2.8.2` недоступна, попробуйте:

1. **Последний коммит:**
   ```gradle
   implementation 'com.github.barteksc:AndroidPdfViewer:master-SNAPSHOT'
   ```

2. **Конкретный коммит (SHA):**
   ```gradle
   implementation 'com.github.barteksc:AndroidPdfViewer:COMMIT_SHA'
   ```

3. **Другой тег:**
   ```gradle
   implementation 'com.github.barteksc:AndroidPdfViewer:3.0.0-beta.5'
   ```

## ⚠️ Важно

После изменения зависимости:
1. Очистите проект: `gradlew clean`
2. Обновите зависимости: `gradlew --refresh-dependencies`
3. Пересоберите проект: `gradlew build`

