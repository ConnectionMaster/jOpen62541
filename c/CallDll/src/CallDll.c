#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <wrapper62541.h>

// print out the read response to stdout
UA_Boolean dumpReadNode(UA_ReadResponse *rResp) {

	if (rResp->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
		printf("Bad status code!\n");
		fflush(stdout);
		return UA_FALSE;
	}

	if (rResp->resultsSize > 0 && rResp->results[0].hasValue == UA_FALSE) {
		printf("Node does not have a value.\n");
		fflush(stdout);
		return UA_FALSE;
	}

	if (UA_Variant_isScalar(&rResp->results[0].value) == UA_FALSE) {
		printf("Node is not a scalar (is array).\n");
		fflush(stdout);
		return UA_FALSE;
	}

	// only scalars
	const UA_DataType *type = rResp->results[0].value.type;
	void *data = rResp->results[0].value.data;

	if (data == (void *) 0) {
		printf("Read null value\n");
		return UA_TRUE;
	}

	if (type == &UA_TYPES[UA_TYPES_BOOLEAN]) {
		UA_Boolean value = *(UA_Boolean*) data;
		printf("Read BOOLEAN value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_INT32]) {
		UA_Int32 value = *(UA_Int32*) data;
		printf("Read INT32 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_UINT32]) {
		UA_UInt32 value = *(UA_UInt32*) data;
		printf("Read UINT32 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_INT64]) {
		UA_Int64 value = *(UA_Int64*) data;
		printf("Read INT64 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_UINT64]) {
		UA_UInt64 value = *(UA_UInt64*) data;
		printf("Read UINT64 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_INT16]) {
		UA_Int16 value = *(UA_Int16*) data;
		printf("Read INT16 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_UINT16]) {
		UA_UInt16 value = *(UA_UInt16*) data;
		printf("Read UINT16 value: %i\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_FLOAT]) {
		UA_Float value = *(UA_Float*) data;
		printf("Read FLOAT value: %g\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_DOUBLE]) {
		UA_Double value = *(UA_Double*) data;
		printf("Read DOUBLE value: %g\n", value);
	} else if (type == &UA_TYPES[UA_TYPES_STRING]) {
		UA_String value = *(UA_String*) data;

		if (value.length > 0) {
			char stringData[value.length + 1];
			memset(stringData, 0, value.length + 1);
			memcpy(&stringData, value.data, value.length);
			printf("Read STRING value: %s\n", stringData);
		} else {
			printf("Read string type of length %i\n", value.length);
		}

	} else if (type == &UA_TYPES[UA_TYPES_DATETIME]) {
		UA_DateTime time = *(UA_DateTime*) data;

		UA_DateTimeStruct dts = UA_DateTime_toStruct(time);

		printf("Read DATE_TIME M/D/Y H:M:S: %i/%i/%i %i:%i:%i\n", dts.month,
				dts.day, dts.year, dts.hour, dts.min, dts.sec);

	} else {
		printf("Read unknown value type with index %i\n", type->typeIndex);
	}

	fflush(stdout);
	return UA_TRUE;
}

// print out the browse response to stdout
// if recurse == true, then browse children
// if readAlso == true, then read the value while browsing
void dumpBrowseNode(UA_BrowseResponse *bResp, UA_Client *client,
		UA_Boolean recurse, UA_Boolean readAlso) {
	printf("\n%-9s %-16s %-16s %-16s\n", "NAMESPACE", "NODEID", "BROWSE NAME",
			"DISPLAY NAME");

	for (int i = 0; i < bResp->resultsSize; ++i) {
		for (int j = 0; j < bResp->results[i].referencesSize; ++j) {

			UA_ReferenceDescription *ref = &(bResp->results[i].references[j]);

			if (ref->nodeId.nodeId.identifierType == UA_NODEIDTYPE_NUMERIC) {
				printf("%-9d %-16d %-16.*s %-16.*s\n",
						ref->browseName.namespaceIndex,
						ref->nodeId.nodeId.identifier.numeric,
						ref->browseName.name.length, ref->browseName.name.data,
						ref->displayName.text.length,
						ref->displayName.text.data);

				UA_Boolean readValue = UA_TRUE;

				if (readAlso == UA_TRUE) {
					UA_NodeId readNode = UA_NODEID_NUMERIC(
							ref->nodeId.nodeId.namespaceIndex,
							ref->nodeId.nodeId.identifier.numeric);
					UA_ReadResponse *rResp = synchRead(client, &readNode);
					readValue = dumpReadNode(rResp);
				}

				if (recurse == UA_TRUE && readValue == UA_FALSE) {

					UA_NodeId *nodeToBrowse = createNumericNodeId(
							ref->nodeId.nodeId.namespaceIndex,
							ref->nodeId.nodeId.identifier.numeric);

					UA_BrowseResponse *bRespInner = browseNode(client, nodeToBrowse);
					dumpBrowseNode(bRespInner, client, recurse, readAlso);
				}

			} else if (ref->nodeId.nodeId.identifierType
					== UA_NODEIDTYPE_STRING) {
				printf("%-9d %-16.*s %-16.*s %-16.*s\n",
						ref->browseName.namespaceIndex,
						ref->nodeId.nodeId.identifier.string.length,
						ref->nodeId.nodeId.identifier.string.data,
						ref->browseName.name.length, ref->browseName.name.data,
						ref->displayName.text.length,
						ref->displayName.text.data);

				UA_Boolean readValue = UA_TRUE;

				if (readAlso == UA_TRUE) {
					UA_NodeId *readNode = createStringNodeId(
							ref->nodeId.nodeId.namespaceIndex,
							&ref->nodeId.nodeId.identifier.string);

					UA_ReadResponse *rResp = synchRead(client, readNode);
					dumpReadNode(rResp);
				}

				if (recurse == UA_TRUE && readValue == UA_FALSE) {

					UA_NodeId *nodeToBrowse = createStringNodeId(
							ref->nodeId.nodeId.namespaceIndex,
							&ref->nodeId.nodeId.identifier.string);
					UA_BrowseResponse *bRespInner = browseNode(client,
							nodeToBrowse);
					dumpBrowseNode(bRespInner, client, recurse, readAlso);
				}

			} else {
				printf("\nUnrecognized identifier type");
			}
		}
	}
}

// call the native Open62541 functions
UA_StatusCode runClientTest() {
	UA_Client *client = UA_Client_new(UA_ClientConfig_standard,
			Logger_Stdout_new());
	UA_StatusCode retval = UA_Client_connect(client,
			ClientNetworkLayerTCP_connect, "opc.tcp://localhost:16664");
	if (retval != UA_STATUSCODE_GOOD) {
		UA_Client_delete(client);
		return retval;
	}

	// Browse some objects
	printf("Browsing nodes in objects folder:\n");

	UA_BrowseRequest bReq;
	UA_BrowseRequest_init(&bReq);
	bReq.requestedMaxReferencesPerNode = 0;
	bReq.nodesToBrowse = UA_BrowseDescription_new();
	bReq.nodesToBrowseSize = 1;
	bReq.nodesToBrowse[0].nodeId = UA_NODEID_NUMERIC(0, UA_NS0ID_OBJECTSFOLDER); //browse objects folder
	bReq.nodesToBrowse[0].resultMask = UA_BROWSERESULTMASK_ALL; //return everything

	UA_BrowseResponse bResp = UA_Client_browse(client, &bReq);
	printf("%-9s %-16s %-16s %-16s\n", "NAMESPACE", "NODEID", "BROWSE NAME",
			"DISPLAY NAME");
	for (int i = 0; i < bResp.resultsSize; ++i) {
		for (int j = 0; j < bResp.results[i].referencesSize; ++j) {
			UA_ReferenceDescription *ref = &(bResp.results[i].references[j]);
			if (ref->nodeId.nodeId.identifierType == UA_NODEIDTYPE_NUMERIC) {
				printf("%-9d %-16d %-16.*s %-16.*s\n",
						ref->browseName.namespaceIndex,
						ref->nodeId.nodeId.identifier.numeric,
						ref->browseName.name.length, ref->browseName.name.data,
						ref->displayName.text.length,
						ref->displayName.text.data);
			} else if (ref->nodeId.nodeId.identifierType
					== UA_NODEIDTYPE_STRING) {
				printf("%-9d %-16.*s %-16.*s %-16.*s\n",
						ref->browseName.namespaceIndex,
						ref->nodeId.nodeId.identifier.string.length,
						ref->nodeId.nodeId.identifier.string.data,
						ref->browseName.name.length, ref->browseName.name.data,
						ref->displayName.text.length,
						ref->displayName.text.data);
			}
			//TODO: distinguish further types
		}
	}
	UA_BrowseRequest_deleteMembers(&bReq);
	UA_BrowseResponse_deleteMembers(&bResp);

	UA_Int32 value = 0;
	// Read node's value
	printf("\nReading the value of node (1, \"the.answer\"):\n");
	UA_ReadRequest rReq;
	UA_ReadRequest_init(&rReq);
	rReq.nodesToRead = UA_ReadValueId_new();
	rReq.nodesToReadSize = 1;
	rReq.nodesToRead[0].nodeId = UA_NODEID_STRING_ALLOC(1, "the.answer"); /* assume this node exists */
	rReq.nodesToRead[0].attributeId = UA_ATTRIBUTEID_VALUE;

	UA_ReadResponse rResp = UA_Client_read(client, &rReq);
	if (rResp.responseHeader.serviceResult == UA_STATUSCODE_GOOD
			&& rResp.resultsSize > 0 && rResp.results[0].hasValue
			&& UA_Variant_isScalar(&rResp.results[0].value)
			&& rResp.results[0].value.type == &UA_TYPES[UA_TYPES_INT32]) {
		value = *(UA_Int32*) rResp.results[0].value.data;
		printf("the value is: %i\n", value);
	}

	UA_ReadRequest_deleteMembers(&rReq);
	UA_ReadResponse_deleteMembers(&rResp);

	value++;
	// Write node's value
	printf("\nWriting a value of node (1, \"the.answer\"):\n");
	UA_WriteRequest wReq;
	UA_WriteRequest_init(&wReq);
	wReq.nodesToWrite = UA_WriteValue_new();
	wReq.nodesToWriteSize = 1;
	wReq.nodesToWrite[0].nodeId = UA_NODEID_STRING_ALLOC(1, "the.answer"); /* assume this node exists */
	wReq.nodesToWrite[0].attributeId = UA_ATTRIBUTEID_VALUE;
	wReq.nodesToWrite[0].value.hasValue = UA_TRUE;
	wReq.nodesToWrite[0].value.value.type = &UA_TYPES[UA_TYPES_INT32];
	wReq.nodesToWrite[0].value.value.storageType = UA_VARIANT_DATA_NODELETE; //do not free the integer on deletion
	wReq.nodesToWrite[0].value.value.data = &value;

	UA_WriteResponse wResp = UA_Client_write(client, &wReq);
	if (wResp.responseHeader.serviceResult == UA_STATUSCODE_GOOD)
		printf("the new value is: %i\n", value);

	UA_WriteRequest_deleteMembers(&wReq);
	UA_WriteResponse_deleteMembers(&wResp);

	UA_Client_disconnect(client);
	UA_Client_delete(client);
	return UA_STATUSCODE_GOOD;
}

// call the wrapper functions
UA_StatusCode runWrapperTest() {
	// "opc.tcp://localhost:16664" (Open62541 example server)
	// "opc.tcp://localhost:48020" (Unified Automation ANSI C test server)
	UA_Client *client = createClient();
	// connect to server
	UA_StatusCode retval = connectClient(client, "opc.tcp://localhost:16664");

	if (retval != UA_STATUSCODE_GOOD) {
		deleteClient(client);
		return retval;
	}

	// Read node's value
	UA_ReadRequest rReq;
	UA_ReadRequest_init(&rReq);
	rReq.nodesToRead = UA_ReadValueId_new();
	rReq.nodesToReadSize = 1;
	rReq.nodesToRead[0].nodeId = UA_NODEID_STRING_ALLOC(4, "Demo.Static.Matrix.ByteString");
	rReq.nodesToRead[0].attributeId = UA_ATTRIBUTEID_VALUE;

	UA_ReadResponse rResp = UA_Client_read(client, &rReq);
	if (rResp.responseHeader.serviceResult == UA_STATUSCODE_GOOD
			&& rResp.resultsSize > 0
			&& rResp.results[0].hasValue) {
		printf("Read a value\n");
	}

	UA_ReadRequest_deleteMembers(&rReq);
	UA_ReadResponse_deleteMembers(&rResp);

	// string r/w
	UA_UInt16 nsIndex = 1;
	UA_Int32 nodeId = 0;
	UA_Byte stringValue[] = "string value";

	UA_String *uaString = UA_String_new();
	uaString->length = sizeof(stringValue);
	uaString->data = stringValue;
	nodeId = 51023;

	UA_NodeId *nodeToWrite = createNumericNodeId(nsIndex, nodeId);
	UA_Variant *valueToWrite = createScalarVariant(UA_TYPES_STRING, uaString);

	UA_WriteResponse *response = synchWrite(client, nodeToWrite, valueToWrite);

	UA_ResponseHeader responseHeader = response->responseHeader;

	if (responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
		printf("Bad status code on write: " + responseHeader.serviceResult);
	} else {
		printf("Wrote string value: %s\n", stringValue);
	}

	deleteWriteResponse(response);
	deleteVariant(valueToWrite);

	// string - read
	UA_NodeId *nodeToRead = createNumericNodeId(nsIndex, nodeId);
	UA_ReadResponse *rResponse = synchRead(client, nodeToRead);
	dumpReadNode(rResponse);
	deleteReadResponse(rResponse);

	// date time r/w
	UA_DateTime time = UA_DateTime_now();
	nodeId = 51025;

	nodeToWrite = createNumericNodeId(nsIndex, nodeId);
	valueToWrite = createScalarVariant(UA_TYPES_DATETIME, &time);

	response = synchWrite(client, nodeToWrite, valueToWrite);
	responseHeader = response->responseHeader;

	if (responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
		printf("Bad status code on write: " + responseHeader.serviceResult);
	} else {
		printf("Wrote time value: %x\n", time);
	}

	deleteWriteResponse(response);
	deleteVariant(valueToWrite);

	// time - read
	nodeToRead = createNumericNodeId(nsIndex, nodeId);
	rResponse = synchRead(client, nodeToRead);
	dumpReadNode(rResponse);
	deleteReadResponse(rResponse);

	UA_Int32 value = 100;

	// read with wrapper
	for (int i = 0; i < 1; i++) {
		printf(
				"\nReading the value of node (1, \"the.answer\") with wrapper:\n");

		UA_NodeId *nodeToRead = createCharNodeId(1, "the.answer");
		UA_ReadResponse *rResp2 = synchRead(client, nodeToRead);

		dumpReadNode(rResp2);
		deleteReadResponse(rResp2);

		fflush(stdout);
	}

	// write with wrapper
	for (int i = 0; i < 1; i++) {
		printf(
				"\nWriting a value of node (1, \"the.answer\") with wrapper:%i\n",
				value);

		UA_NodeId *nodeToWrite = createCharNodeId(1, "the.answer");

		UA_Variant *valueToWrite = createScalarVariant(UA_TYPES_INT32, &value);

		UA_WriteResponse *wResp2 = synchWrite(client, nodeToWrite,
				valueToWrite);

		if (wResp2->responseHeader.serviceResult == UA_STATUSCODE_GOOD) {
			printf("the new value is: %i\n", value);
		} else {
			printf("failed to write value: %i\n", value);
		}
		fflush(stdout);

		deleteWriteResponse(wResp2);
		deleteNodeId(nodeToWrite);
		deleteVariant(valueToWrite);

		value++;
	}

	// browse using wrapper
	for (int i = 0; i < 1; i++) {
		printf("Browsing nodes in objects folder using wrapper:\n");

		UA_NodeId *nodeToBrowse = createNumericNodeId(0,
		UA_NS0ID_OBJECTSFOLDER);
		UA_BrowseResponse *bResp2 = browseNode(client, nodeToBrowse);

		dumpBrowseNode(bResp2, client, UA_TRUE, UA_TRUE);

		fflush(stdout);

		deleteBrowseResponse(bResp2);
		// NodeId has already been deleted
	}

	disconnectClient(client);
	deleteClient(client);

	return UA_STATUSCODE_GOOD;

}

/*
 * This is a program to test the Open62541 API and the C wrapper functions.
 */
int main(int argc, char *argv[]) {

	UA_DateTime time = UA_DateTime_now();
	UA_DateTimeStruct dts = UA_DateTime_toStruct(time);

	printf("Time now, M/D/Y H:M:S: %i/%i/%i %i:%i:%i\n", dts.month, dts.day,
			dts.year, dts.hour, dts.min, dts.sec);

	UA_DateTimeStruct *pdts = createDateTimeStruct(time);
	printf("Time now, M/D/Y H:M:S: %i/%i/%i %i:%i:%i\n", pdts->month, pdts->day,
			pdts->year, pdts->hour, pdts->min, pdts->sec);
	deleteDateTimeStruct(pdts);
	fflush(stdout);

	// uncomment to run the Open62541 test
	//runClientTest();

	// call the wrapper functions
	runWrapperTest();

	printf("Done with test.\n");
	fflush(stdout);
}

