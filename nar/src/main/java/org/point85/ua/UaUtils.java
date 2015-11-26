package org.point85.ua;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.point85.open62541.Open62541ExtLibrary;
import org.point85.open62541.Open62541ExtLibrary.UA_StatusCode;
import org.point85.open62541.UA_DataType;
import org.point85.open62541.UA_DateTimeStruct;
import org.point85.open62541.UA_NodeId;
import org.point85.open62541.UA_String;
import org.point85.open62541.UA_Variant;

/**
 * This class contains utility methods.
 *
 */
public class UaUtils {

	// Number of seconds from 1 Jan. 1601 00:00 to 1 Jan 1970 00:00 UTC
	private static final long UNIX_EPOCH_BIAS_SEC = 11644473600L;

	/**
	 * Create a {@link UA_NodeId} from a {@link UaNode}
	 * 
	 * @param node
	 *            {@link UaNode}
	 * @return {@link Pointer}
	 */
	public static Pointer<UA_NodeId> uaNodeIdFromNode(UaNode node) {
		Pointer<UA_NodeId> nodeId = null;

		UaNodeId mainNodeId = node.getMainNodeId();
		short nsIndex = mainNodeId.getNamespaceIndex();

		if (mainNodeId.isNumeric()) {
			nodeId = Open62541ExtLibrary.createNumericNodeId(nsIndex, mainNodeId.getNumericIdentifier());
		} else if (mainNodeId.isString()) {
			// TODO: potential memory leak. Open62541 library should free this
			// memory
			Pointer<UA_String> nodeName = UaUtils.uaStringFromString(mainNodeId.getStringIdentifier());
			nodeId = Open62541ExtLibrary.createStringNodeId(nsIndex, nodeName);
		}

		return nodeId;
	}

	/**
	 * Create a {@link UA_String} from a String. Note: The UA_String must be
	 * subsequently released!
	 * 
	 * @param value
	 *            String value
	 * @return {@link Pointer}
	 */
	public static Pointer<UA_String> uaStringFromString(String value) {
		Pointer<UA_String> pUaString = Pointer.allocate(UA_String.class);
		UA_String uaString = pUaString.get();
		uaString.length(value.length());
		ByteBuffer bb = ByteBuffer.wrap(value.getBytes(), 0, value.length());
		Pointer<Byte> pBytes = Pointer.pointerToBytes(bb);
		uaString.data(pBytes);
		return pUaString;
	}

	/**
	 * Create a char array of bytes from a String
	 * 
	 * @param value
	 *            String
	 * @return {@link Pointer}
	 */
	public static Pointer<Byte> charArrayFromString(String value) {
		return Pointer.pointerToBytes(value.getBytes());
	}

	/**
	 * Create a String from a char array of bytes
	 * 
	 * @param chars
	 *            {@link Pointer}
	 * @return String value
	 */
	public static String stringFromCharArray(Pointer<Byte> chars) {
		byte[] ca = chars.getBytes();
		return new String(ca);
	}

	// create a String from a UA_String
	static String stringFromUaString(UA_String uaString) {
		Pointer<Byte> data = uaString.data();

		if (data == null) {
			return new String();
		}

		int len = uaString.length();

		if (len < 0) {
			return new String();
		}

		byte[] bytes = data.getBytes(len);

		String s = new String(bytes, StandardCharsets.UTF_8);

		return s;
	}

	// convert a status code to a hexadecimal value
	static String convertStatusToHexCode(IntValuedEnum<UA_StatusCode> status) {
		return Long.toHexString(status.value());
	}

	// get the scalar data value from the UA_Variant
	static synchronized Object getScalarValue(UA_Variant variant) {
		Object scalarValue = null;

		if (variant == null) {
			return scalarValue;
		}

		// data type
		UA_DataType dataType = variant.type().get();
		short nativeType = dataType.typeIndex();
		Pointer<?> data = variant.data();

		if (data == null) {
			return scalarValue;
		}

		// must be a scalr
		boolean isScalar = Open62541ExtLibrary.UA_Variant_isScalar(Pointer.getPointer(variant));

		if (!isScalar) {
			return scalarValue;
		}

		// create the scalar value from its type
		if (nativeType == Open62541ExtLibrary.UA_TYPES_BOOLEAN) {
			scalarValue = new Boolean(data.getBoolean());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_INT32
				|| nativeType == Open62541ExtLibrary.UA_TYPES_UINT32) {
			scalarValue = new Integer(data.getInt());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_BYTE
				|| nativeType == Open62541ExtLibrary.UA_TYPES_SBYTE) {
			scalarValue = new Byte(data.getByte());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_INT64
				|| nativeType == Open62541ExtLibrary.UA_TYPES_UINT64) {
			scalarValue = new Long(data.getLong());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_INT16
				|| nativeType == Open62541ExtLibrary.UA_TYPES_UINT16) {
			scalarValue = new Short(data.getShort());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_FLOAT) {
			scalarValue = new Float(data.getFloat());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_DOUBLE) {
			scalarValue = new Double(data.getDouble());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_STRING) {
			Pointer<UA_String> pString = data.as(UA_String.class);
			scalarValue = UaUtils.stringFromUaString(pString.get());

		} else if (nativeType == Open62541ExtLibrary.UA_TYPES_DATETIME) {
			scalarValue = UaUtils.timeFromUaDateTime(data.getLong());

		} else {
			// Read unknown value type (check all types)
			scalarValue = new String("UNKNOWN, type: " + nativeType);
		}

		return scalarValue;
	}

	// create a Open62541 date and time from a java ZonedDateTime
	static synchronized long uaDateTimeFromTime(ZonedDateTime time) {
		Instant i = Instant.from(time);

		/*
		 * A DateTime value is encoded as a 64-bit signed integer which
		 * represents the number of 100 nanosecond intervals since January 1,
		 * 1601 (UTC).
		 */
		long seconds = i.getEpochSecond();
		int nanoOfSecond = i.getNano();
		long value = (UNIX_EPOCH_BIAS_SEC + seconds) * 10000000L + nanoOfSecond / 100L;
		return value;
	}

	// create a ZonedDateTime from the Open62541 date and time
	static synchronized ZonedDateTime timeFromUaDateTime(long uaTime) {
		Pointer<UA_DateTimeStruct> pdt = Open62541ExtLibrary.createDateTimeStruct(uaTime);

		// UTC
		UA_DateTimeStruct uaDate = pdt.get();

		int nanoOfSec = uaDate.milliSec() * 1000000 + uaDate.microSec() * 1000 + uaDate.nanoSec();

		ZoneId utcId = ZoneId.ofOffset("UTC", ZoneOffset.UTC);

		ZonedDateTime value = ZonedDateTime.of(uaDate.year(), uaDate.month(), uaDate.day(), uaDate.hour(), uaDate.min(),
				uaDate.sec(), nanoOfSec, utcId);

		Open62541ExtLibrary.deleteDateTimeStruct(pdt);

		return value;
	}

	// get the name space index from the node identifier
	static short getNamespaceIndex(UA_NodeId nodeId) {
		return nodeId.namespaceIndex();
	}
}
