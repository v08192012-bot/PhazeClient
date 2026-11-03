@echo off
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0patch_jar.ps1" %*
echo.
pause
