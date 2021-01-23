package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : open62541.h:1007</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_DataTypeMember extends StructObject {
	static {
		BridJ.register();
	}
	/**
	 * < Index of the member in the datatypetable<br>
	 * C type : UA_UInt16
	 */
	@Field(0) 
	public short memberTypeIndex() {
		return this.io.getShortField(this, 0);
	}
	/**
	 * < Index of the member in the datatypetable<br>
	 * C type : UA_UInt16
	 */
	@Field(0) 
	public UA_DataTypeMember memberTypeIndex(short memberTypeIndex) {
		this.io.setShortField(this, 0, memberTypeIndex);
		return this;
	}
	/**
	 * < The type of the member is defined in namespace<br>
	 * zero. In this implementation, types from custom<br>
	 * namespace may contain members from the same<br>
	 * namespace or ns0 only.<br>
	 * C type : UA_Boolean
	 */
	@Field(1) 
	public boolean namespaceZero() {
		return this.io.getBooleanField(this, 1);
	}
	/**
	 * < The type of the member is defined in namespace<br>
	 * zero. In this implementation, types from custom<br>
	 * namespace may contain members from the same<br>
	 * namespace or ns0 only.<br>
	 * C type : UA_Boolean
	 */
	@Field(1) 
	public UA_DataTypeMember namespaceZero(boolean namespaceZero) {
		this.io.setBooleanField(this, 1, namespaceZero);
		return this;
	}
	/**
	 * < How much padding is there before this member element? For<br>
	 * arrays this is split into 2 bytes padding before the<br>
	 * length index (max 4 bytes) and 3 bytes padding before the<br>
	 * pointer (max 8 bytes)<br>
	 * C type : UA_Byte
	 */
	@Field(2) 
	public byte padding() {
		return this.io.getByteField(this, 2);
	}
	/**
	 * < How much padding is there before this member element? For<br>
	 * arrays this is split into 2 bytes padding before the<br>
	 * length index (max 4 bytes) and 3 bytes padding before the<br>
	 * pointer (max 8 bytes)<br>
	 * C type : UA_Byte
	 */
	@Field(2) 
	public UA_DataTypeMember padding(byte padding) {
		this.io.setByteField(this, 2, padding);
		return this;
	}
	/**
	 * < The member is an array of the given type<br>
	 * C type : UA_Boolean
	 */
	@Field(3) 
	public boolean isArray() {
		return this.io.getBooleanField(this, 3);
	}
	/**
	 * < The member is an array of the given type<br>
	 * C type : UA_Boolean
	 */
	@Field(3) 
	public UA_DataTypeMember isArray(boolean isArray) {
		this.io.setBooleanField(this, 3, isArray);
		return this;
	}
	public UA_DataTypeMember() {
		super();
	}
	public UA_DataTypeMember(Pointer pointer) {
		super(pointer);
	}
}