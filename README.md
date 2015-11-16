# jOpen62541
Steps to build a 32-bit wrapper dll for Microsoft Windows from the Open62541 0.1.1 release source are:
(1)  Install MinGW in C:\MinGW folder (http://www.mingw.org/)
(2)  Change to the jOpen62541\c\Open62541 folder, then type "mingw32-make clean" then "mingw32-make lib" to produce
	the Open62541Ext.dll shared library in the "build" folder.
(3)  Type "mingw32-make copylib" to copy the shared library to the dist folder.
(4)  Change to the jOpen62541\c\CallDll folder, then type "mingw32-make clean" then "mingw32-make exe" to produce
	the CallDll.exe executable in the "build" folder.
(5)  The Open62541Ext.dll will need to be copied into 
	the same folder as CallDll.exe, or be on the path.  This test project is setup for the Open62541 example server
	but with some small code changes will run against the Unified Automation server.
(6) Make sure that the Open62541 test server is running and execute CallDll.exe in the build folder.  You should see console output.

Steps to generate the Bridj java interface classes are:
(1)  Change to the jOpen62541\bridj\jnaerator folder.  
(2)  Type generate.bat and answer "Yes" to deleting the old generated java files.
(3)  The newly generated files will be copied into the java project

Steps to create the client API jar and test program:
(1)  Install Gradle 2.8 (http://gradle.org/gradle-download/)
(2)  Change to the jOpen62541\java folder.  
(3)  Type "gradle clean" followed by "gradle build"
(4)  Type "gradle javadoc" to create the Javadocs.  Note that this task does fail due to Bridj HTML problems, but still produces useful output.
(5)  Type "gradle copyfiles" to zip the javadocs and copy files to the "dist" folder

Finally, you can execute the TestUaClient in the jOpen62541\java\test_client\build\libs folder by executing this command:
%JAVA_HOME%\bin\java -cp "jOpen62541TestClient-0.1.jar;jOpen62541API-0.1.jar;bridj-0.7.0.jar" org.point85.ua.TestUaClient

where:
JAVA_HOME is a java 8 32-bit JVM
the jOpen62541API-0.1.jar has been copied from the "dist" folder
the bridj-0.7.0.jar file was downloaded from Maven Central during the java build
and the Open62541Ext.dll was copied into this folder

You should see a lot of console output.
