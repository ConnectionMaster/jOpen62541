package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : open62541.h:506</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_ExpandedNodeId extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : UA_NodeId */
	@Field(0) 
	public UA_NodeId nodeId() {
		return this.io.getNativeObjectField(this, 0);
	}
	/** C type : UA_NodeId */
	@Field(0) 
	public UA_ExpandedNodeId nodeId(UA_NodeId nodeId) {
		this.io.setNativeObjectField(this, 0, nodeId);
		return this;
	}
	/**
	 * not encoded if length=-1<br>
	 * C type : UA_String
	 */
	@Field(1) 
	public UA_String namespaceUri() {
		return this.io.getNativeObjectField(this, 1);
	}
	/**
	 * not encoded if length=-1<br>
	 * C type : UA_String
	 */
	@Field(1) 
	public UA_ExpandedNodeId namespaceUri(UA_String namespaceUri) {
		this.io.setNativeObjectField(this, 1, namespaceUri);
		return this;
	}
	/**
	 * not encoded if 0<br>
	 * C type : UA_UInt32
	 */
	@Field(2) 
	public int serverIndex() {
		return this.io.getIntField(this, 2);
	}
	/**
	 * not encoded if 0<br>
	 * C type : UA_UInt32
	 */
	@Field(2) 
	public UA_ExpandedNodeId serverIndex(int serverIndex) {
		this.io.setIntField(this, 2, serverIndex);
		return this;
	}
	public UA_ExpandedNodeId() {
		super();
	}
	public UA_ExpandedNodeId(Pointer pointer) {
		super(pointer);
	}
}