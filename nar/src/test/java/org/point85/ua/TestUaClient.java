package org.point85.ua;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.bridj.Pointer;
import org.point85.open62541.Open62541ExtLibrary;
import org.point85.open62541.UA_NodeId;
import org.point85.open62541.UA_String;
import org.point85.open62541.UA_Variant;

public class TestUaClient {

	private UaClient uaClient = new UaClient();

	private void readWriteTheAnswer(int value) throws UaException {
		short nsIndex = 1;
		String id = "the.answer";

		// write
		UaNode node = new UaNode(nsIndex, id);
		uaClient.write(node, value);
		System.out.println("Wrote to node: " + node);

		// read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// write
		value++;
		uaClient.write(node, value);
		System.out.println("Wrote to node: " + node);
	}

	private void readWriteSingleNode(UaNode node) throws UaException {
		boolean value = true;

		// read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// write
		value = !value;
		uaClient.write(node, value);
		System.out.println("Wrote to node: " + node);
	}

	private void readWriteDemoScalarNodes() throws UaException {
		short nsIndex = 1;
		int nodeId = 0;
		boolean boolValue = true;
		byte byteValue = 0x1;
		short shortValue = 2;
		int intValue = 3;
		long longValue = 4l;
		float floatValue = 5.5f;
		double doubleValue = 6.6666d;
		String stringValue = UUID.randomUUID().toString();
		long dtValue = Open62541ExtLibrary.UA_DateTime_now();
		ZonedDateTime javaTime = ZonedDateTime.now();
		System.out.println("Local java time: " + javaTime);

		Pointer<UA_NodeId> nodeToWrite = null;
		Pointer<UA_Variant> valueToWrite = null;
		UaNode node = null;

		// ************************* boolean - write
		// ********************************
		nodeId = 51001;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, boolValue);
		System.out.println("Wrote node: " + node);

		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* byte - write
		// ********************************
		nodeId = 51005;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, byteValue);
		System.out.println("Wrote node: " + node);

		// byte - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* short - write
		// ******************************
		nodeId = 51007;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, byteValue);
		System.out.println("Wrote node: " + node);

		// short - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* integer - write
		// ******************************
		nodeId = 51011;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, byteValue);
		System.out.println("Wrote node: " + node);

		// integer - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// **************************** long - write
		// ************************************
		nodeId = 51015;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, longValue);
		System.out.println("Wrote node: " + node);

		// long - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* float - write
		// ***********************************
		nodeId = 51019;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, floatValue);
		System.out.println("Wrote node: " + node);

		// float - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* double - write
		// ***********************************
		nodeId = 51021;
		node = new UaNode(nsIndex, nodeId);
		uaClient.write(node, doubleValue);
		System.out.println("Wrote node: " + node);

		// double - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* string - write
		// ***********************************
		nodeId = 51023;
		node = new UaNode(nsIndex, nodeId);

		uaClient.write(node, stringValue);
		System.out.println("Wrote node: " + node);

		// string - read
		uaClient.read(node);
		System.out.println("Read node: " + node);

		// ************************* date time - write
		// ***********************************
		nodeId = 51025;
		node = new UaNode(nsIndex, nodeId);

		uaClient.write(node, javaTime);
		System.out.println("Wrote node: " + node);

		// date time - read
		uaClient.read(node);
		System.out.println("Read node: " + node);
	}

	private void browseRecursively(UaNode parentNode, int level)
			throws UaException {

		uaClient.browse(parentNode);

		if (parentNode.isLeaf()) {
			return;
		}

		level++;
		List<UaNode> childNodes = parentNode.getChildren();
		
		for (int i = 0; i < childNodes.size(); i++) {
			UaNode childNode = childNodes.get(i);
			System.out.println("   [" + i + "] Level: " + level + ", browsed child node: "
					+ childNode);
			browseRecursively(childNode, level);
		}
		level--;
	}

	private void testStrings() {
		String id = "string";
		Pointer<UA_String> nodeName = UaUtils.uaStringFromString(id);
		String id2 = UaUtils.stringFromUaString(nodeName.get());
		System.out.println("ID2: " + id2);
		nodeName.release();

		Pointer<Byte> bytes = UaUtils.charArrayFromString(id);
		String id3 = UaUtils.stringFromCharArray(bytes);
		System.out.println("ID3: " + id3);
	}
	
	private void testUnifiedAutomationServer() throws UaException {
		// Unified Automation
		String serverUrl = "opc.tcp://localhost:48020";

		// connect to server
		uaClient.connect(serverUrl);

		// read write single node
		for (int i = 0; i < 1; i++) {
			short nsIndex = 4;
			String id = "Demo.Static.Scalar.String";

			UaNode node = new UaNode(nsIndex, id);
			readWriteSingleNode(node);
		}

		// browse the single node
		for (int i = 0; i < 1; i++) {
			browseSingleNode((short) 4, -1, "Demo.Static.Scalar.Boolean");
		}

		for (int i = 0; i < 1; i++) {
			System.out.println("Browse recursively, Pass #" + i);
			browseRecursivelyFromNode((short) 4, -1, "Demo.Static.Scalar");
		}

		System.out.println("Done!");
	}
	
	private void testOpen62541Server() throws UaException {
		// Open62541
		String serverUrl = "opc.tcp://localhost:16664";

		// connect to server
		uaClient.connect(serverUrl);

		// test string methods
		for (int i = 0; i < 1; i++) {
			System.out.println("Test string methods, Pass #" + i);
			testStrings();
		}

		// write to and read from "the.answer" node
		int value = 99;
		for (int i = 0; i < 1; i++) {
			System.out.println("Read and write answer, Pass #" + i);
			readWriteTheAnswer(value);
			value++;
		}

		// write to and read from the Demo nodes
		for (int i = 0; i < 1; i++) {
			System.out.println("Read and write Demo node, Pass #" + i);
			readWriteDemoScalarNodes();
		}

		// browse the single node
		for (int i = 0; i < 1; i++) {
			System.out.println("Browse single node, Pass #" + i);
			browseSingleNode((short) 1, -1, "the.answer");
		}

		for (int i = 0; i < 1; i++) {
			System.out.println("Browse recursively, Pass #" + i);
			browseRecursivelyFromNode((short) 1, -1, "the.answer");
		}

		uaClient.disconnect();
	}

	private void executeTests() {

		try {
			testOpen62541Server();
			//testUnifiedAutomationServer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				uaClient.disconnect();
				uaClient.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void browseRecursivelyFromNode(short nsIndex, int numericId,
			String stringId) throws UaException {
		UaNode topNode = null;

		// read it first to get the value and data type
		if (numericId != -1) {
			topNode = new UaNode(nsIndex, numericId);
		} else {
			topNode = new UaNode(nsIndex, stringId);
		}
		// uaClient.read(topNode);
		System.out.println("Browsing parent node:" + topNode);
		int level = -1;

		browseRecursively(topNode, level);
	}

	private void browseSingleNode(short nsIndex, int numericId, String stringId)
			throws UaException {
		UaNode topNode = null;

		// read it first to get the value and data type
		if (numericId != -1) {
			topNode = new UaNode(nsIndex, numericId);
		} else {
			topNode = new UaNode(nsIndex, stringId);
		}

		uaClient.read(topNode);
		System.out.println("Browsing parent node:" + topNode);
		uaClient.browse(topNode);

		List<UaNode> childNodes = topNode.getChildren();

		for (UaNode childNode : childNodes) {
			System.out.println("   Browsed child node: " + childNode);
		}
	}

	public static void main(String[] args) {

		TestUaClient uaClient = new TestUaClient();
		uaClient.executeTests();
	}
}
