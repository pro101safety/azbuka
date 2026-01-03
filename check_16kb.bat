@echo off
REM Скрипт для проверки поддержки 16 КБ страниц в Windows

echo ========================================
echo Проверка поддержки 16 КБ страниц
echo ========================================
echo.

REM Проверка AndroidManifest.xml
echo [1/4] Проверка AndroidManifest.xml...
findstr /C:"extractNativeLibs=\"false\"" app\src\main\AndroidManifest.xml >nul
if %errorlevel% equ 0 (
    echo [OK] extractNativeLibs="false" найден в манифесте
) else (
    echo [ERROR] extractNativeLibs="false" НЕ найден в манифесте!
    exit /b 1
)

REM Проверка build.gradle
echo [2/4] Проверка build.gradle...
findstr /C:"targetSdk 35" app\build.gradle >nul
if %errorlevel% equ 0 (
    echo [OK] targetSdk 35 найден
) else (
    echo [WARNING] targetSdk 35 не найден, проверьте вручную
)

findstr /C:"useLegacyPackaging = false" app\build.gradle >nul
if %errorlevel% equ 0 (
    echo [OK] useLegacyPackaging = false найден
) else (
    echo [WARNING] useLegacyPackaging = false не найден, проверьте вручную
)

REM Проверка наличия APK/AAB
echo [3/4] Проверка собранных файлов...
if exist "app\build\outputs\apk\release\app-release.apk" (
    echo [OK] Release APK найден
    echo       Путь: app\build\outputs\apk\release\app-release.apk
) else (
    echo [INFO] Release APK не найден. Соберите его командой: gradlew assembleRelease
)

if exist "app\build\outputs\bundle\release\app-release.aab" (
    echo [OK] Release AAB найден
    echo       Путь: app\build\outputs\bundle\release\app-release.aab
) else (
    echo [INFO] Release AAB не найден. Соберите его командой: gradlew bundleRelease
)

REM Проверка через aapt2 (если доступен)
echo [4/4] Проверка через aapt2...
set AAPT2_PATH=
if exist "%LOCALAPPDATA%\Android\Sdk\build-tools\35.0.0\aapt2.exe" (
    set AAPT2_PATH=%LOCALAPPDATA%\Android\Sdk\build-tools\35.0.0\aapt2.exe
) else if exist "%USERPROFILE%\AppData\Local\Android\Sdk\build-tools\35.0.0\aapt2.exe" (
    set AAPT2_PATH=%USERPROFILE%\AppData\Local\Android\Sdk\build-tools\35.0.0\aapt2.exe
)

if defined AAPT2_PATH (
    if exist "app\build\outputs\apk\release\app-release.apk" (
        echo Проверка манифеста в APK...
        "%AAPT2_PATH%" dump badging "app\build\outputs\apk\release\app-release.apk" | findstr /C:"extractNativeLibs" >nul
        if %errorlevel% equ 0 (
            echo [OK] extractNativeLibs найден в APK
        ) else (
            echo [WARNING] extractNativeLibs не найден в APK манифесте
        )
    )
) else (
    echo [INFO] aapt2 не найден. Установите Android SDK Build Tools 35.0.0
)

echo.
echo ========================================
echo Проверка завершена!
echo ========================================
echo.
echo Следующие шаги:
echo 1. Соберите release APK: gradlew assembleRelease
echo 2. Соберите release AAB: gradlew bundleRelease
echo 3. Загрузите AAB в Google Play Console для финальной проверки
echo.
pause

