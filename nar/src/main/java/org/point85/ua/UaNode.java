package org.point85.ua;

import java.util.ArrayList;
import java.util.List;

import org.bridj.Pointer;
import org.point85.open62541.Open62541ExtLibrary;
import org.point85.open62541.UA_DataType;
import org.point85.open62541.UA_NodeId;
import org.point85.open62541.UA_ReferenceDescription;
import org.point85.open62541.UA_Variant;

/**
 * This class contains information about a node in the OPC UA name space. Values
 * are either set prior to a writing to a node or upon reading (browsing) the
 * node.
 */
public class UaNode {
	// scalar value
	private Object scalarValue;

	// browse name
	private String browseName;

	// display name
	private String displayName;

	// main NodeId
	private UaNodeId mainNodeId;

	// data type NodeId
	private UaNodeId dataTypeNodeId;

	// reference NodeId
	private UaNodeId referenceNodeId;

	// scalar data value flag
	private boolean isScalar = false;

	// native data type
	private short nativeDataType = -1;

	// folder nodes do not have a value
	private boolean hasValue = false;

	// flag to determine if the description has been set
	private boolean descriptionSet = false;

	// a parent node's child nodes
	private List<UaNode> children;

	UaNode() {
		children = new ArrayList<>();
	}

	/**
	 * Construct a node
	 * 
	 * @param namespaceIndex
	 *            Name space indes
	 * @param numericIdentifier
	 *            Numeric node identifier
	 */
	public UaNode(short namespaceIndex, int numericIdentifier) {
		this();
		this.mainNodeId = new UaNodeId(namespaceIndex);
		this.mainNodeId.setNumericIdentifier(numericIdentifier);
	}

	/**
	 * Construct a node
	 * 
	 * @param namespaceIndex
	 *            Name space index
	 * @param stringIdentifier
	 *            Node identifier
	 */
	public UaNode(short namespaceIndex, String stringIdentifier) {
		this();
		this.mainNodeId = new UaNodeId(namespaceIndex);
		this.mainNodeId.setStringIdentifier(stringIdentifier);
	}

	// sets the reference description data into the node
	void setReferenceData(UA_ReferenceDescription referenceDescription) {

		if (referenceDescription == null || descriptionSet) {
			return;
		}

		// display name
		displayName = UaUtils.stringFromUaString(referenceDescription.browseName().name());

		// browse name
		browseName = UaUtils.stringFromUaString(referenceDescription.displayName().text());

		// set the primary node data
		UA_NodeId nodeId = referenceDescription.nodeId().nodeId();
		setMainNodeIdData(nodeId);

		// reference type id
		UA_NodeId referenceType = referenceDescription.referenceTypeId();
		setReferenceTypeNodeIdData(referenceType);

		this.descriptionSet = true;
	}

	// sets the value data held in the variant
	void setVariantData(UA_Variant variant) {
		if (variant == null) {
			hasValue = false;
			return;
		}

		hasValue = true;

		// check for scalar value
		isScalar = Open62541ExtLibrary.UA_Variant_isScalar(Pointer.getPointer(variant));

		scalarValue = UaUtils.getScalarValue(variant);

		// get the data thpe
		UA_DataType dataType = variant.type().get();

		// native data type
		nativeDataType = dataType.typeIndex();

		// type identifier
		UA_NodeId typeId = dataType.typeId();

		// set data type NodeId info
		UaNodeId dataTypeNodeId = new UaNodeId(typeId.namespaceIndex());
		setDataTypeNodeId(dataTypeNodeId);
		dataTypeNodeId.setNumericIdentifier(typeId.identifier().numeric());
		dataTypeNodeId.setTypeEnum(typeId.identifierType());
	}

	// set the main node id information
	void setMainNodeIdData(UA_NodeId nodeId) {
		UaNodeId mainNodeId = new UaNodeId(nodeId.namespaceIndex());

		// numeric or string identifier
		mainNodeId.setNumericIdentifier(nodeId.identifier().numeric());
		mainNodeId.setStringIdentifier(UaUtils.stringFromUaString(nodeId.identifier().string()));

		// enumerated type
		mainNodeId.setTypeEnum(nodeId.identifierType());
		setMainNodeId(mainNodeId);
	}

	// set reference node id information
	void setReferenceTypeNodeIdData(UA_NodeId nodeId) {
		UaNodeId refTypeNodeId = new UaNodeId(nodeId.namespaceIndex());

		// numeric or string identifier
		refTypeNodeId.setNumericIdentifier(nodeId.identifier().numeric());
		refTypeNodeId.setStringIdentifier(UaUtils.stringFromUaString(nodeId.identifier().string()));

		// enumerated type
		refTypeNodeId.setTypeEnum(nodeId.identifierType());
		setReferenceNodeId(refTypeNodeId);
	}

	@Override
	public String toString() {

		String value = null;
		if (hasValue() && scalarValue != null) {
			value = scalarValue.toString();
		} else if (hasValue() && scalarValue == null) {
			value = "UNKNOWN";
		}

		String valueText = "";

		if (value != null) {
			valueText = "Data Type: " + nativeDataType + ", Value: " + value;
		}

		String dataTypeText = "";
		if (dataTypeNodeId != null) {
			dataTypeText = ", Data Type NodeId: " + dataTypeNodeId;
		}

		String referenceTypeText = "";
		if (referenceNodeId != null) {
			String typeFlag = "";

			if (referenceNodeId.getNumericIdentifier() == Open62541ExtLibrary.UA_NS0ID_HASTYPEDEFINITION) {
				typeFlag = "HasTypeDefinition";
			} else if (referenceNodeId.getNumericIdentifier() == Open62541ExtLibrary.UA_NS0ID_HASCOMPONENT) {
				typeFlag = "HasComponent";
			} else if (referenceNodeId.getNumericIdentifier() == Open62541ExtLibrary.UA_NS0ID_HASPROPERTY) {
				typeFlag = "HasProperty";
			} else if (referenceNodeId.getNumericIdentifier() == Open62541ExtLibrary.UA_NS0ID_HASEVENTSOURCE) {
				typeFlag = "HasEventSource";
			} else if (referenceNodeId.getNumericIdentifier() == Open62541ExtLibrary.UA_NS0ID_ORGANIZES) {
				typeFlag = "Organized";
			}
			referenceTypeText = ", " + typeFlag + ": " + referenceNodeId;
		}

		return mainNodeId.toString() + " [" + browseName + ", " + displayName + "] " + valueText + dataTypeText
				+ referenceTypeText;
	}

	/**
	 * Get the scalar node value
	 * 
	 * @return Node value as Object type
	 */
	public Object getScalarValue() {
		return scalarValue;
	}

	/**
	 * Get the browse name
	 * 
	 * @return Browse name
	 */
	public String getBrowseName() {
		return browseName;
	}

	/**
	 * Get the display name
	 * 
	 * @return Display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Check for a scalar value
	 * 
	 * @return 'true' if scalar, else 'false'
	 */
	public boolean isScalar() {
		return isScalar;
	}

	/**
	 * Check to see if the node has a value
	 * 
	 * @return 'true' if it has a value, else 'false'
	 */
	public boolean hasValue() {
		return hasValue;
	}

	/**
	 * Check for the node being a leaf (i.e. it has no children)
	 * 
	 * @return 'true' if a leaf else 'false'
	 */
	public boolean isLeaf() {
		boolean isLeaf = true;

		for (UaNode childNode : this.getChildren()) {
			int flag = childNode.getReferenceNodeId().getNumericIdentifier();

			if (flag == Open62541ExtLibrary.UA_NS0ID_HASPROPERTY || flag == Open62541ExtLibrary.UA_NS0ID_HASCOMPONENT
					|| flag == Open62541ExtLibrary.UA_NS0ID_ORGANIZES) {
				isLeaf = false;
				break;
			}
		}

		return isLeaf;
	}

	/**
	 * Get the main node identifier
	 * 
	 * @return {@link UaNodeId}
	 */
	public UaNodeId getMainNodeId() {
		return mainNodeId;
	}

	/**
	 * Set the main node identifier
	 * 
	 * @param nodeId
	 *            {@link UaNodeId}
	 */
	public void setMainNodeId(UaNodeId nodeId) {
		this.mainNodeId = nodeId;
	}

	/**
	 * Set the reference node identifier
	 * 
	 * @param nodeId
	 *            {@link UaNodeId}
	 */
	public void setReferenceNodeId(UaNodeId nodeId) {
		this.referenceNodeId = nodeId;
	}

	/**
	 * Get the reference node identifier
	 * 
	 * @return {@link UaNodeId}
	 */
	public UaNodeId getReferenceNodeId() {
		return this.referenceNodeId;
	}

	/**
	 * Get the data type node identifier
	 * 
	 * @return {@link UaNodeId}
	 */
	public UaNodeId getDataTypeNodeId() {
		return dataTypeNodeId;
	}

	/**
	 * Set the data type node identifier
	 * 
	 * @param dataTypeNodeId
	 *            {@link UaNodeId}
	 */
	public void setDataTypeNodeId(UaNodeId dataTypeNodeId) {
		this.dataTypeNodeId = dataTypeNodeId;
	}

	/**
	 * Get the number of child nodes
	 * 
	 * @return Number of child nodes
	 */
	public int getNumberOfChildren() {
		return this.children.size();
	}

	/**
	 * Get a list of the child nodes
	 * 
	 * @return List of {@link UaNode}
	 */
	public List<UaNode> getChildren() {
		return children;
	}
}
