@echo off
:Start
echo ================================
echo Starte den Minecraft Server...
echo ================================
java -Xms5G -Xmx12G -jar server.jar nogui

echo ================================
echo Der Server wurde gestoppt.
echo Starte den Server in 1 Sekunden neu...
echo (Drücke STRG+C, um die Schleife zu beenden.)
echo ================================
timeout /t 1 >nul
goto Start