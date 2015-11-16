rem Visual Studio include files
set VISUAL_STUDIO_HOME="C:\Program Files (x86)\Microsoft Visual Studio 12.0"
rem set VISUAL_STUDIO_INCLUDES="C:\MinGW\include"
set VISUAL_STUDIO_INCLUDES="C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\include"
set WINDOWS_SDK_HOME="C:\Program Files (x86)\Microsoft SDKs\Windows\v7.1A"
rem delete the old generated java files first
del src\org\point85\open62541\*.*
rem A specific JDK 7 is used to execute the configuration file
rem Note: "File not found" errors are output for C header files, but the java code is still created
C:\jdk1.8.0_40-32bit\bin\java -jar ..\lib\jnaerator-0.12-shaded.jar config.jnaerator
rem copy generated source
copy .\src\org\point85\open62541\*.java ..\..\java\client_api\src\main\java\org\point85\open62541