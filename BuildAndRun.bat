echo off

echo Please make sure that you have JDK installed and JAVA_HOME set properly!

pause

set ROOT=%cd%
set PATH_TO_FX="%cd%\lib\javafx-sdk-11.0.2\lib"
cd src\
%JAVA_HOME%\bin\javac.exe -d %ROOT%\bin --module-path %PATH_TO_FX% --add-modules=ALL-MODULE-PATH app\App.java

copy app\srtPlayer.fxml %ROOT%\bin\app
copy app\srtPlayer.png %ROOT%\bin\app
copy app\style.css %ROOT%\bin\app

cd %ROOT%
%JAVA_HOME%\bin\java.exe -cp bin --module-path %PATH_TO_FX% --add-modules=ALL-MODULE-PATH app.App


