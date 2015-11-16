
# makefile for jOpen62541 Windows 32-bit test executable
# uses MinGW toolchain
CC = gcc
CCFLAGS = -Wall -c -std=c99 -D_WIN32  -O3 -I..\Open62541\src -include wrapper62541.h 
LDFLAGS = -L..\Open62541\build -lOpen62541Ext
SOURCE = src
BIN = bin
BUILD = build
EXE = $(BUILD)\CallDll.exe

# EXE target
exe: $(EXE)

$(EXE): $(BIN)\CallDll.o 
	$(CC) $< $(LDFLAGS) -o $@

$(BIN)\CallDll.o : $(SOURCE)\CallDll.c
	$(CC) $(CCFLAGS) $< -o $@

clean:
	del  $(BIN)\*.o && del $(EXE)