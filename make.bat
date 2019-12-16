@echo off
setlocal enabledelayedexpansion
cd .\scr
if exist Class\*.class del /f /q Class\*.class
set classpath=
for %%a in (../lib/*.jar) do set classpath=!classpath!;../lib/%%a
javac Class/SpaceInvaders.java
set classpath=%classpath:;= %
set classpath=%classpath:../=%
if not errorlevel 1 (
echo.Class-Path: %classpath% > manifest
echo.Main-Class: Class.SpaceInvaders >> manifest
jar cfm ../SpaceInvaders.jar manifest Class\*.class Class Images Music Sounds
del /f /q manifest
cd..
.\run.bat
)
if errorlevel 1 pause
exit /b