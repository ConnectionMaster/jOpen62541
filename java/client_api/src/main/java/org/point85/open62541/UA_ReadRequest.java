package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.point85.open62541.Open62541ExtLibrary.UA_TimestampsToReturn;
/**
 * <i>native declaration : open62541.h:1728</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_ReadRequest extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : UA_RequestHeader */
	@Field(0) 
	public UA_RequestHeader requestHeader() {
		return this.io.getNativeObjectField(this, 0);
	}
	/** C type : UA_RequestHeader */
	@Field(0) 
	public UA_ReadRequest requestHeader(UA_RequestHeader requestHeader) {
		this.io.setNativeObjectField(this, 0, requestHeader);
		return this;
	}
	/** C type : UA_Double */
	@Field(1) 
	public double maxAge() {
		return this.io.getDoubleField(this, 1);
	}
	/** C type : UA_Double */
	@Field(1) 
	public UA_ReadRequest maxAge(double maxAge) {
		this.io.setDoubleField(this, 1, maxAge);
		return this;
	}
	/** C type : UA_TimestampsToReturn */
	@Field(2) 
	public IntValuedEnum<UA_TimestampsToReturn > timestampsToReturn() {
		return this.io.getEnumField(this, 2);
	}
	/** C type : UA_TimestampsToReturn */
	@Field(2) 
	public UA_ReadRequest timestampsToReturn(IntValuedEnum<UA_TimestampsToReturn > timestampsToReturn) {
		this.io.setEnumField(this, 2, timestampsToReturn);
		return this;
	}
	/** C type : UA_Int32 */
	@Field(3) 
	public int nodesToReadSize() {
		return this.io.getIntField(this, 3);
	}
	/** C type : UA_Int32 */
	@Field(3) 
	public UA_ReadRequest nodesToReadSize(int nodesToReadSize) {
		this.io.setIntField(this, 3, nodesToReadSize);
		return this;
	}
	/** C type : UA_ReadValueId* */
	@Field(4) 
	public Pointer<UA_ReadValueId > nodesToRead() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : UA_ReadValueId* */
	@Field(4) 
	public UA_ReadRequest nodesToRead(Pointer<UA_ReadValueId > nodesToRead) {
		this.io.setPointerField(this, 4, nodesToRead);
		return this;
	}
	public UA_ReadRequest() {
		super();
	}
	public UA_ReadRequest(Pointer pointer) {
		super(pointer);
	}
}
