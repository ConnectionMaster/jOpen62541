package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.point85.open62541.Open62541ExtLibrary.UA_ServerState;
/**
 * <i>native declaration : open62541.h:1873</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_ServerStatusDataType extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : UA_DateTime */
	@Field(0) 
	public long startTime() {
		return this.io.getLongField(this, 0);
	}
	/** C type : UA_DateTime */
	@Field(0) 
	public UA_ServerStatusDataType startTime(long startTime) {
		this.io.setLongField(this, 0, startTime);
		return this;
	}
	/** C type : UA_DateTime */
	@Field(1) 
	public long currentTime() {
		return this.io.getLongField(this, 1);
	}
	/** C type : UA_DateTime */
	@Field(1) 
	public UA_ServerStatusDataType currentTime(long currentTime) {
		this.io.setLongField(this, 1, currentTime);
		return this;
	}
	/** C type : UA_ServerState */
	@Field(2) 
	public IntValuedEnum<UA_ServerState > state() {
		return this.io.getEnumField(this, 2);
	}
	/** C type : UA_ServerState */
	@Field(2) 
	public UA_ServerStatusDataType state(IntValuedEnum<UA_ServerState > state) {
		this.io.setEnumField(this, 2, state);
		return this;
	}
	/** C type : UA_BuildInfo */
	@Field(3) 
	public UA_BuildInfo buildInfo() {
		return this.io.getNativeObjectField(this, 3);
	}
	/** C type : UA_BuildInfo */
	@Field(3) 
	public UA_ServerStatusDataType buildInfo(UA_BuildInfo buildInfo) {
		this.io.setNativeObjectField(this, 3, buildInfo);
		return this;
	}
	/** C type : UA_UInt32 */
	@Field(4) 
	public int secondsTillShutdown() {
		return this.io.getIntField(this, 4);
	}
	/** C type : UA_UInt32 */
	@Field(4) 
	public UA_ServerStatusDataType secondsTillShutdown(int secondsTillShutdown) {
		this.io.setIntField(this, 4, secondsTillShutdown);
		return this;
	}
	/** C type : UA_LocalizedText */
	@Field(5) 
	public UA_LocalizedText shutdownReason() {
		return this.io.getNativeObjectField(this, 5);
	}
	/** C type : UA_LocalizedText */
	@Field(5) 
	public UA_ServerStatusDataType shutdownReason(UA_LocalizedText shutdownReason) {
		this.io.setNativeObjectField(this, 5, shutdownReason);
		return this;
	}
	public UA_ServerStatusDataType() {
		super();
	}
	public UA_ServerStatusDataType(Pointer pointer) {
		super(pointer);
	}
}