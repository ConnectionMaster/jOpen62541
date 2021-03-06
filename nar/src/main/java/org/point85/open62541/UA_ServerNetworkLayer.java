package org.point85.open62541;
import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.FlagSet;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Ptr;
import org.bridj.util.DefaultParameterizedType;
import org.point85.open62541.Open62541ExtLibrary.UA_Logger;
import org.point85.open62541.Open62541ExtLibrary.UA_StatusCode;
/**
 * <i>native declaration : open62541.h:2134</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("Open62541Ext") 
public class UA_ServerNetworkLayer extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : void* */
	@Field(0) 
	public Pointer<? > nlHandle() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : void* */
	@Field(0) 
	public UA_ServerNetworkLayer nlHandle(Pointer<? > nlHandle) {
		this.io.setPointerField(this, 0, nlHandle);
		return this;
	}
	/** C type : start_callback* */
	@Field(1) 
	public Pointer<UA_ServerNetworkLayer.start_callback > start() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : start_callback* */
	@Field(1) 
	public UA_ServerNetworkLayer start(Pointer<UA_ServerNetworkLayer.start_callback > start) {
		this.io.setPointerField(this, 1, start);
		return this;
	}
	/** C type : getWork_callback* */
	@Field(2) 
	public Pointer<UA_ServerNetworkLayer.getWork_callback > getWork() {
		return this.io.getPointerField(this, 2);
	}
	/** C type : getWork_callback* */
	@Field(2) 
	public UA_ServerNetworkLayer getWork(Pointer<UA_ServerNetworkLayer.getWork_callback > getWork) {
		this.io.setPointerField(this, 2, getWork);
		return this;
	}
	/** C type : stop_callback* */
	@Field(3) 
	public Pointer<UA_ServerNetworkLayer.stop_callback > stop() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : stop_callback* */
	@Field(3) 
	public UA_ServerNetworkLayer stop(Pointer<UA_ServerNetworkLayer.stop_callback > stop) {
		this.io.setPointerField(this, 3, stop);
		return this;
	}
	/** C type : free_callback* */
	@Field(4) 
	public Pointer<UA_ServerNetworkLayer.free_callback > free() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : free_callback* */
	@Field(4) 
	public UA_ServerNetworkLayer free(Pointer<UA_ServerNetworkLayer.free_callback > free) {
		this.io.setPointerField(this, 4, free);
		return this;
	}
	/** C type : UA_String* */
	@Field(5) 
	public Pointer<UA_String > discoveryUrl() {
		return this.io.getPointerField(this, 5);
	}
	/** C type : UA_String* */
	@Field(5) 
	public UA_ServerNetworkLayer discoveryUrl(Pointer<UA_String > discoveryUrl) {
		this.io.setPointerField(this, 5, discoveryUrl);
		return this;
	}
	/** <i>native declaration : open62541.h:2130</i> */
	public static abstract class start_callback extends Callback<start_callback > {
		public IntValuedEnum<UA_StatusCode > apply(Pointer<? > nlHandle, Pointer<Pointer<UA_Logger > > logger) {
			return FlagSet.fromValue(apply(Pointer.getPeer(nlHandle), Pointer.getPeer(logger)), UA_StatusCode.class);
		}
		public int apply(@Ptr long nlHandle, @Ptr long logger) {
			return (int)apply(Pointer.pointerToAddress(nlHandle), (Pointer)Pointer.pointerToAddress(logger, DefaultParameterizedType.paramType(Pointer.class, UA_Logger.class))).value();
		}
	};
	/** <i>native declaration : open62541.h:2131</i> */
	public static abstract class getWork_callback extends Callback<getWork_callback > {
		public int apply(Pointer<? > nlhandle, Pointer<Pointer<UA_WorkItem > > workItems, short timeout) {
			return apply(Pointer.getPeer(nlhandle), Pointer.getPeer(workItems), timeout);
		}
		public int apply(@Ptr long nlhandle, @Ptr long workItems, short timeout) {
			return apply(Pointer.pointerToAddress(nlhandle), (Pointer)Pointer.pointerToAddress(workItems, DefaultParameterizedType.paramType(Pointer.class, UA_WorkItem.class)), timeout);
		}
	};
	/** <i>native declaration : open62541.h:2132</i> */
	public static abstract class stop_callback extends Callback<stop_callback > {
		public int apply(Pointer<? > nlhandle, Pointer<Pointer<UA_WorkItem > > workItems) {
			return apply(Pointer.getPeer(nlhandle), Pointer.getPeer(workItems));
		}
		public int apply(@Ptr long nlhandle, @Ptr long workItems) {
			return apply(Pointer.pointerToAddress(nlhandle), (Pointer)Pointer.pointerToAddress(workItems, DefaultParameterizedType.paramType(Pointer.class, UA_WorkItem.class)));
		}
	};
	/** <i>native declaration : open62541.h:2133</i> */
	public static abstract class free_callback extends Callback<free_callback > {
		public void apply(Pointer<? > nlhandle) {
			apply(Pointer.getPeer(nlhandle));
		}
		public void apply(@Ptr long nlhandle) {
			apply(Pointer.pointerToAddress(nlhandle));
		}
	};
	public UA_ServerNetworkLayer() {
		super();
	}
	public UA_ServerNetworkLayer(Pointer pointer) {
		super(pointer);
	}
}
