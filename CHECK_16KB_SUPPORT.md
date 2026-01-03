# Инструкция по проверке поддержки 16 КБ страниц

## Методы проверки

### 1. Проверка через командную строку (bundletool)

#### Установка bundletool:
1. Скачайте bundletool с [GitHub](https://github.com/google/bundletool/releases)
2. Сохраните как `bundletool.jar`

#### Проверка AAB файла:
```bash
# Соберите release AAB
./gradlew bundleRelease

# Проверьте поддержку 16 КБ страниц
java -jar bundletool.jar validate --bundle=app/build/outputs/bundle/release/app-release.aab
```

#### Проверка APK файла:
```bash
# Соберите release APK
./gradlew assembleRelease

# Используйте aapt2 для проверки
# Путь к aapt2 обычно: Android/Sdk/build-tools/35.0.0/aapt2
aapt2 dump badging app/build/outputs/apk/release/app-release.apk | grep "native-code"
```

### 2. Проверка через Android Studio (рекомендуется)

#### Метод 1: APK Analyzer (самый надежный способ)
1. **Соберите release APK:**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - Или через командную строку: `gradlew assembleRelease`

2. **Откройте APK Analyzer:**
   - Build → Analyze APK
   - Выберите собранный APK: `app/build/outputs/apk/release/app-release.apk`

3. **Проверьте выравнивание:**
   - Откройте раздел **"lib"** или **"Native libs"**
   - В столбце **"Alignment"** должно быть указано **16384** (16 КБ) для всех `.so` файлов
   - Если видите **4096** (4 КБ) — библиотека не выровнена на 16 КБ

4. **Проверьте манифест:**
   - В разделе "AndroidManifest.xml" должно быть: `android:extractNativeLibs="false"`

**Важно:** Согласно [статье на Habr](https://habr.com/ru/companies/ncloudtech/articles/960520/), рекомендуется использовать Android Studio 2025.1.3 или новее, так как в более ранних версиях были проблемы с проверкой выравнивания.

#### Метод 2: Проверка манифеста
1. Откройте `app/src/main/AndroidManifest.xml`
2. Убедитесь, что есть: `android:extractNativeLibs="false"`

### 3. Проверка через adb (на реальном устройстве)

```bash
# Установите приложение на устройство
adb install app/build/outputs/apk/release/app-release.apk

# Проверьте установленное приложение
adb shell dumpsys package com.instruction.paperka20 | grep -i "extractNativeLibs"
```

### 4. Проверка через эмулятор с 16 КБ страницами

#### Создание эмулятора:
1. Android Studio → Tools → Device Manager
2. Create Device → выберите устройство
3. System Image → выберите Android 15 (API 35) или выше
4. В Advanced Settings проверьте настройки эмулятора

#### Проверка размера страницы на устройстве:
```bash
adb shell getconf PAGE_SIZE
# Должно вернуть 16384 для устройств с 16 КБ страницами
```

### 5. Автоматическая проверка через скрипт

См. файл `check_16kb.sh` или `check_16kb.bat`

## Что проверять:

### ✅ Обязательные требования:

1. **AndroidManifest.xml:**
   - `android:extractNativeLibs="false"` ✓

2. **build.gradle:**
   - `targetSdk 35` или выше ✓
   - `compileSdk 35` или выше ✓
   - `packaging { jniLibs { useLegacyPackaging = false } }` ✓

3. **Нативные библиотеки:**
   - Все `.so` файлы должны быть правильно выровнены
   - Проверка через `readelf` или `objdump`

### ⚠️ Важные замечания:

- Google Play Console автоматически проверяет поддержку 16 КБ страниц
- Если приложение не поддерживает, оно будет отклонено
- Проверка происходит при загрузке AAB в Google Play Console

## Проверка через Google Play Console (предварительная)

1. Загрузите AAB в Internal Testing или Closed Testing
2. Google Play автоматически проверит поддержку
3. Если есть проблемы, вы увидите предупреждения в разделе "Release" → "Production" → "App bundle explorer"

## Дополнительные инструменты:

### apkanalyzer (встроен в Android SDK)
```bash
apkanalyzer manifest print app/build/outputs/apk/release/app-release.apk | grep extractNativeLibs
```

### Проверка выравнивания нативных библиотек:
```bash
# Для каждой .so библиотеки
readelf -l lib/arm64-v8a/libjniPdfium.so | grep LOAD
# Проверьте, что выравнивание кратно 16384 (16 КБ)
```

## Решение проблем:

### Если проверка не проходит:

1. **Убедитесь, что `extractNativeLibs="false"` установлен** в AndroidManifest.xml
2. **Проверьте версию Android Gradle Plugin** — должна быть **8.5.1 или новее** (у вас 8.13.0 ✅)
3. **Пересоберите проект:**
   ```bash
   gradlew clean
   gradlew bundleRelease
   ```
4. **Проверьте через APK Analyzer** — в столбце "Alignment" должно быть 16384 (16 КБ)

### Если библиотека из зависимостей не поддерживает 16 КБ:

Если нативная библиотека из зависимости (например, `android-pdf-viewer`) не выровнена на 16 КБ:

1. **AGP 8.5.1+ автоматически выравнивает** все нативные библиотеки при сборке
2. **Проверьте через APK Analyzer** — если после пересборки выравнивание 16384, значит AGP обработал библиотеки
3. **Если проблема сохраняется:**
   - Проверьте, есть ли обновление библиотеки
   - Проверьте issues библиотеки на GitHub
   - AGP 8.13.0 должен автоматически выравнивать библиотеки из зависимостей

**Примечание:** Согласно [статье на Habr](https://habr.com/ru/companies/ncloudtech/articles/960520/), AGP 8.5.1+ автоматически включает поддержку 16KB при сборке App Bundle и APK, а также корректно выравнивает нативные библиотеки под 16KB.

