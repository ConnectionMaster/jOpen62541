package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : open62541.h:1470</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_ViewAttributes extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : UA_UInt32 */
	@Field(0) 
	public int specifiedAttributes() {
		return this.io.getIntField(this, 0);
	}
	/** C type : UA_UInt32 */
	@Field(0) 
	public UA_ViewAttributes specifiedAttributes(int specifiedAttributes) {
		this.io.setIntField(this, 0, specifiedAttributes);
		return this;
	}
	/** C type : UA_LocalizedText */
	@Field(1) 
	public UA_LocalizedText displayName() {
		return this.io.getNativeObjectField(this, 1);
	}
	/** C type : UA_LocalizedText */
	@Field(1) 
	public UA_ViewAttributes displayName(UA_LocalizedText displayName) {
		this.io.setNativeObjectField(this, 1, displayName);
		return this;
	}
	/** C type : UA_LocalizedText */
	@Field(2) 
	public UA_LocalizedText description() {
		return this.io.getNativeObjectField(this, 2);
	}
	/** C type : UA_LocalizedText */
	@Field(2) 
	public UA_ViewAttributes description(UA_LocalizedText description) {
		this.io.setNativeObjectField(this, 2, description);
		return this;
	}
	/** C type : UA_UInt32 */
	@Field(3) 
	public int writeMask() {
		return this.io.getIntField(this, 3);
	}
	/** C type : UA_UInt32 */
	@Field(3) 
	public UA_ViewAttributes writeMask(int writeMask) {
		this.io.setIntField(this, 3, writeMask);
		return this;
	}
	/** C type : UA_UInt32 */
	@Field(4) 
	public int userWriteMask() {
		return this.io.getIntField(this, 4);
	}
	/** C type : UA_UInt32 */
	@Field(4) 
	public UA_ViewAttributes userWriteMask(int userWriteMask) {
		this.io.setIntField(this, 4, userWriteMask);
		return this;
	}
	/** C type : UA_Boolean */
	@Field(5) 
	public boolean containsNoLoops() {
		return this.io.getBooleanField(this, 5);
	}
	/** C type : UA_Boolean */
	@Field(5) 
	public UA_ViewAttributes containsNoLoops(boolean containsNoLoops) {
		this.io.setBooleanField(this, 5, containsNoLoops);
		return this;
	}
	/** C type : UA_Byte */
	@Field(6) 
	public byte eventNotifier() {
		return this.io.getByteField(this, 6);
	}
	/** C type : UA_Byte */
	@Field(6) 
	public UA_ViewAttributes eventNotifier(byte eventNotifier) {
		this.io.setByteField(this, 6, eventNotifier);
		return this;
	}
	public UA_ViewAttributes() {
		super();
	}
	public UA_ViewAttributes(Pointer pointer) {
		super(pointer);
	}
}
