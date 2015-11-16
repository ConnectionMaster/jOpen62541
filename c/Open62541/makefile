# makefile for Open62541 Windows 32-bit wrapper dll
# uses MinGW toolchain
CC = gcc
CCFLAGS = -Wall -c -std=c99 -D_WIN32 -DUA_DYNAMIC_LINKING -O3
LDFLAGS = -static -shared -lws2_32 
SOURCE = src
BIN = bin
BUILD = build
DLL = $(BUILD)\Open62541Ext.dll

# dll target
lib: $(DLL)

$(DLL): $(BIN)\open62541.o $(BIN)\wrapper62541.o 
	$(CC) $(LDFLAGS) $^ -o $@ -lws2_32 
	
$(BIN)\wrapper62541.o: $(SOURCE)\wrapper62541.c $(SOURCE)\wrapper62541.h
	$(CC) $(CCFLAGS) $< -o $@
	
$(BIN)\open62541.o : $(SOURCE)\open62541.c $(SOURCE)\open62541.h
	$(CC) $(CCFLAGS) $< -o $@

clean:
	del  $(BIN)\*.o && del $(DLL)
	
copylib:
	copy $(DLL) ..\..\dist