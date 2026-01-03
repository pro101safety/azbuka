#!/bin/bash
# Скрипт для проверки поддержки 16 КБ страниц в Linux/Mac

echo "========================================"
echo "Проверка поддержки 16 КБ страниц"
echo "========================================"
echo ""

# Проверка AndroidManifest.xml
echo "[1/4] Проверка AndroidManifest.xml..."
if grep -q 'extractNativeLibs="false"' app/src/main/AndroidManifest.xml; then
    echo "[OK] extractNativeLibs=\"false\" найден в манифесте"
else
    echo "[ERROR] extractNativeLibs=\"false\" НЕ найден в манифесте!"
    exit 1
fi

# Проверка build.gradle
echo "[2/4] Проверка build.gradle..."
if grep -q "targetSdk 35" app/build.gradle; then
    echo "[OK] targetSdk 35 найден"
else
    echo "[WARNING] targetSdk 35 не найден, проверьте вручную"
fi

if grep -q "useLegacyPackaging = false" app/build.gradle; then
    echo "[OK] useLegacyPackaging = false найден"
else
    echo "[WARNING] useLegacyPackaging = false не найден, проверьте вручную"
fi

# Проверка наличия APK/AAB
echo "[3/4] Проверка собранных файлов..."
if [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
    echo "[OK] Release APK найден"
    echo "      Путь: app/build/outputs/apk/release/app-release.apk"
else
    echo "[INFO] Release APK не найден. Соберите его командой: ./gradlew assembleRelease"
fi

if [ -f "app/build/outputs/bundle/release/app-release.aab" ]; then
    echo "[OK] Release AAB найден"
    echo "      Путь: app/build/outputs/bundle/release/app-release.aab"
else
    echo "[INFO] Release AAB не найден. Соберите его командой: ./gradlew bundleRelease"
fi

# Проверка через aapt2 (если доступен)
echo "[4/4] Проверка через aapt2..."
AAPT2_PATH=""

# Попытка найти aapt2
if [ -f "$HOME/Android/Sdk/build-tools/35.0.0/aapt2" ]; then
    AAPT2_PATH="$HOME/Android/Sdk/build-tools/35.0.0/aapt2"
elif [ -f "$ANDROID_HOME/build-tools/35.0.0/aapt2" ]; then
    AAPT2_PATH="$ANDROID_HOME/build-tools/35.0.0/aapt2"
fi

if [ -n "$AAPT2_PATH" ] && [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
    echo "Проверка манифеста в APK..."
    if "$AAPT2_PATH" dump badging "app/build/outputs/apk/release/app-release.apk" | grep -q "extractNativeLibs"; then
        echo "[OK] extractNativeLibs найден в APK"
    else
        echo "[WARNING] extractNativeLibs не найден в APK манифесте"
    fi
else
    echo "[INFO] aapt2 не найден. Установите Android SDK Build Tools 35.0.0"
fi

echo ""
echo "========================================"
echo "Проверка завершена!"
echo "========================================"
echo ""
echo "Следующие шаги:"
echo "1. Соберите release APK: ./gradlew assembleRelease"
echo "2. Соберите release AAB: ./gradlew bundleRelease"
echo "3. Загрузите AAB в Google Play Console для финальной проверки"
echo ""

