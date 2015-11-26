#include <string.h>
#include <stdlib.h>
#include "wrapper62541.h"

// allocate memory for a UA_Variant
#define UA_Variant_new() (UA_Variant*)UA_new(&UA_TYPES[UA_TYPES_VARIANT])

// delete UA_Variant memory
#define UA_Variant_delete(p) UA_delete(p, &UA_TYPES[UA_TYPES_VARIANT])

// create a client
UA_Client *createClient() {
	UA_Client *client = UA_Client_new(UA_ClientConfig_standard,
			Logger_Stdout_new());
	return client;
}

// free client memory
void deleteClient(UA_Client *client) {
	UA_Client_delete(client);
}

// connect the client to a UA server
UA_StatusCode connectClient(UA_Client *client, char *endpointUrl) {
	UA_StatusCode retval = UA_Client_connect(client,
			ClientNetworkLayerTCP_connect, endpointUrl);
	return retval;
}

// disconnect client from UA server
UA_StatusCode disconnectClient(UA_Client *client) {
	UA_StatusCode retval = UA_Client_disconnect(client);
	return retval;
}

// copy the string identifier in a string node
UA_String *copyStringNode(UA_NodeId *nodeId) {
	UA_String stringId = nodeId->identifier.string;
	UA_String *copy = UA_String_new();
	UA_String_copy(&stringId, copy);
	return copy;
}

// read node and block on a response
UA_ReadResponse *synchRead(UA_Client *client, UA_NodeId *nodeId) {
	UA_ReadRequest rReq;
	UA_ReadRequest_init(&rReq);
	rReq.nodesToRead = UA_ReadValueId_new();
	rReq.nodesToReadSize = 1;
	rReq.nodesToRead[0].nodeId = *nodeId;
	rReq.nodesToRead[0].attributeId = UA_ATTRIBUTEID_VALUE;

	UA_ReadResponse rResp = UA_Client_read(client, &rReq);

	// deep copy response and return a pointer to it
	UA_ReadResponse *reply = UA_ReadResponse_new();
	UA_ReadResponse_copy(&rResp, reply);

	UA_ReadRequest_deleteMembers(&rReq);
	UA_ReadResponse_deleteMembers(&rResp);

	return reply;
}

// free the read response memory
void deleteReadResponse(UA_ReadResponse *readResponse) {
	UA_ReadResponse_deleteMembers(readResponse);
}

// write to a node and block on the response
UA_WriteResponse *synchWrite(UA_Client *client, UA_NodeId *nodeId,
		UA_Variant *value) {

	UA_UInt16 typeIndex = value->type->typeIndex;
	void *pValue = value->data;

	UA_WriteRequest wReq;
	UA_WriteRequest_init(&wReq);
	wReq.nodesToWrite = UA_WriteValue_new();
	wReq.nodesToWriteSize = 1;
	wReq.nodesToWrite[0].nodeId = *nodeId;
	wReq.nodesToWrite[0].attributeId = UA_ATTRIBUTEID_VALUE;

	wReq.nodesToWrite[0].value.hasValue = UA_TRUE;
	wReq.nodesToWrite[0].value.value.type = &UA_TYPES[typeIndex];
	wReq.nodesToWrite[0].value.value.storageType = UA_VARIANT_DATA_NODELETE; //do not free the variant data on deletion
	wReq.nodesToWrite[0].value.value.data = pValue;

	UA_WriteResponse wResp = UA_Client_write(client, &wReq);

	// deep copy response and return a pointer to it
	UA_WriteResponse *reply = UA_WriteResponse_new();
	UA_WriteResponse_copy(&wResp, reply);

	UA_WriteRequest_deleteMembers(&wReq);
	UA_WriteResponse_deleteMembers(&wResp);

	return reply;
}

// free the write response memory
void deleteWriteResponse(UA_WriteResponse *writeResponse) {
	UA_WriteResponse_deleteMembers(writeResponse);
}

// browse the node's child nodes
UA_BrowseResponse *browseNode(UA_Client *client, UA_NodeId *nodeId) {

	UA_BrowseRequest bReq;
	UA_BrowseRequest_init(&bReq);
	bReq.requestedMaxReferencesPerNode = 0;
	bReq.nodesToBrowse = UA_BrowseDescription_new();
	bReq.nodesToBrowseSize = 1;
	bReq.nodesToBrowse[0].nodeId = *nodeId;
	bReq.nodesToBrowse[0].resultMask = UA_BROWSERESULTMASK_ALL;

	UA_BrowseResponse bResp = UA_Client_browse(client, &bReq);

	// deep copy response and return a pointer to it
	UA_BrowseResponse *reply = UA_BrowseResponse_new();
	UA_BrowseResponse_copy(&bResp, reply);

	UA_BrowseRequest_deleteMembers(&bReq);
	UA_BrowseResponse_deleteMembers(&bResp);

	return reply;
}

// free the browse response memory
void deleteBrowseResponse(UA_BrowseResponse *browseResponse) {
	UA_BrowseResponse_deleteMembers(browseResponse);
}

// create a numeric node id
UA_NodeId *createNumericNodeId(UA_UInt16 nsIndex, UA_Int32 identifier) {
	UA_NodeId nodeId = UA_NodeId_fromInteger(nsIndex, identifier);

	UA_NodeId *numericNode = UA_NodeId_new();
	UA_NodeId_copy(&nodeId, numericNode);

	return numericNode;
}

// create a string node id from an array of chars
UA_NodeId *createCharNodeId(UA_UInt16 nsIndex, char const identifier[]) {
	UA_NodeId nodeId = UA_NodeId_fromCharStringCopy(nsIndex, identifier);

	UA_NodeId *charNode = UA_NodeId_new();
	UA_NodeId_copy(&nodeId, charNode);

	return charNode;
}

// create a string node id from a UA_String
UA_NodeId *createStringNodeId(UA_UInt16 nsIndex, UA_String *identifier) {
	UA_NodeId nodeId = UA_NodeId_fromStringCopy(nsIndex, *identifier);

	UA_NodeId *stringNode = UA_NodeId_new();
	UA_NodeId_copy(&nodeId, stringNode);

	return stringNode;
}

// free the node id memory
void deleteNodeId(UA_NodeId *nodeId) {
	UA_NodeId_delete(nodeId);
}

// create a variant holding a scalar
UA_Variant *createScalarVariant(UA_UInt16 dataType, void *value) {
	UA_Variant *variant = UA_Variant_new();
	variant->type = &UA_TYPES[dataType];
	variant->storageType = UA_VARIANT_DATA_NODELETE;
	variant->data = value;
	return variant;
}

// free the variant's memory
void deleteVariant(UA_Variant *variant) {
	UA_Variant_delete(variant);
}

// create a date and time of day structure
UA_DateTimeStruct *createDateTimeStruct(UA_DateTime time) {
	UA_DateTimeStruct dts = UA_DateTime_toStruct(time);

	UA_DateTimeStruct *value = malloc(sizeof(UA_DateTimeStruct));
	memcpy(value, &dts, sizeof(UA_DateTimeStruct));
	return value;
}

// free the memory used by the date time struct
void deleteDateTimeStruct(UA_DateTimeStruct *value) {
	free(value);
}
