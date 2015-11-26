package org.point85.ua;

import org.bridj.IntValuedEnum;
import org.point85.open62541.UA_NodeId;
import org.point85.open62541.UA_NodeId.identifierType_enum;

/**
 * This class holds the {@link UA_NodeId} information.
 *
 */
public class UaNodeId {

	// namespaceIndex
	private short namespaceIndex;

	// numeric node identifier
	private int numericIdentifier;

	// string node identifier
	private String stringIdentifier;

	// canonical data type (one of Open62541ExtLibrary.UA_TYPES_AAAAA)
	private IntValuedEnum<identifierType_enum> typeEnum;

	/**
	 * Construct a {@link UaNodeId}
	 * 
	 * @param namespaceIndex
	 *            Name space index
	 */
	public UaNodeId(short namespaceIndex) {
		this.namespaceIndex = namespaceIndex;
	}

	/**
	 * Check to see if the node is numeric
	 * 
	 * @return 'true' if numeric, else'false'
	 */
	public boolean isNumeric() {
		return (typeEnum == UA_NodeId.identifierType_enum.UA_NODEIDTYPE_NUMERIC) ? true : false;
	}

	/**
	 * Check to see if the node is a textual string
	 * 
	 * @return 'true' if a string, else 'false'
	 */
	public boolean isString() {
		return (typeEnum == UA_NodeId.identifierType_enum.UA_NODEIDTYPE_STRING) ? true : false;
	}

	/**
	 * Get the name space index
	 * 
	 * @return Name space index
	 */
	public short getNamespaceIndex() {
		return namespaceIndex;
	}

	/**
	 * Get the numeric node identifier
	 * 
	 * @return Numeric node identifier
	 */
	public int getNumericIdentifier() {
		return numericIdentifier;
	}

	/**
	 * Set the numeric identifier
	 * 
	 * @param numericIdentifier
	 *            Numeric identifier
	 */
	public void setNumericIdentifier(int numericIdentifier) {
		this.numericIdentifier = numericIdentifier;
		this.typeEnum = UA_NodeId.identifierType_enum.UA_NODEIDTYPE_NUMERIC;
	}

	/**
	 * Get the string identifier
	 * 
	 * @return String identifier
	 */
	public String getStringIdentifier() {
		return stringIdentifier;
	}

	/**
	 * Set the string identifier
	 * 
	 * @param stringIdentifier
	 *            String identifier
	 */
	public void setStringIdentifier(String stringIdentifier) {
		this.stringIdentifier = stringIdentifier;
		this.typeEnum = UA_NodeId.identifierType_enum.UA_NODEIDTYPE_STRING;
	}

	/**
	 * Get the enumerated type
	 * 
	 * @return {@link IntValuedEnum}
	 */
	public IntValuedEnum<identifierType_enum> getTypeEnum() {
		return typeEnum;
	}

	/**
	 * Set the enumerated type
	 * 
	 * @param typeEnum
	 *            {@link IntValuedEnum}
	 */
	public void setTypeEnum(IntValuedEnum<identifierType_enum> typeEnum) {
		this.typeEnum = typeEnum;
	}

	@Override
	public String toString() {
		String id = null;
		identifierType_enum nodeType = null;

		if (typeEnum.equals(UA_NodeId.identifierType_enum.UA_NODEIDTYPE_NUMERIC)) {
			id = new Integer(this.numericIdentifier).toString();
			nodeType = UA_NodeId.identifierType_enum.UA_NODEIDTYPE_NUMERIC;
		} else if (typeEnum.equals(UA_NodeId.identifierType_enum.UA_NODEIDTYPE_STRING)) {
			id = this.stringIdentifier;
			nodeType = UA_NodeId.identifierType_enum.UA_NODEIDTYPE_STRING;
		} else {
			id = "UNKNOWN";
		}

		return "(" + namespaceIndex + ", " + id + "): " + nodeType;
	}
}
