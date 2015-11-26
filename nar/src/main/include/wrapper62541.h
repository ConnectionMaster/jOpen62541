#ifndef WRAPPER_62541_H_
#define WRAPPER_62541_H_

#ifdef __cplusplus
extern "C" {
#endif

#include "open62541.h"

// An implementation is needed for the JNA and BridJ implementation
// The actual struct is not used in the client API
struct UA_Server {
	int i;
};

// create and free client memory
UA_EXPORT UA_Client *createClient();
UA_EXPORT void deleteClient(UA_Client *client);

// connect and disconnect a client
UA_EXPORT UA_StatusCode connectClient(UA_Client *client, char *endpointUrl);
UA_EXPORT UA_StatusCode disconnectClient(UA_Client *client);

// blocking read and free read response memory
UA_EXPORT UA_ReadResponse *synchRead(UA_Client *client, UA_NodeId *nodeId);
UA_EXPORT void deleteReadResponse(UA_ReadResponse *readResponse);

// blocking write and free write response memory
UA_EXPORT UA_WriteResponse *synchWrite(UA_Client *client, UA_NodeId *nodeId, UA_Variant *value);
UA_EXPORT void deleteWriteResponse(UA_WriteResponse *writeResponse);

// browse a node and free the browse response memory
UA_EXPORT UA_BrowseResponse *browseNode(UA_Client *client, UA_NodeId *nodeId);
UA_EXPORT void deleteBrowseResponse(UA_BrowseResponse *browseResponse);

// create node IDs and free their memory
UA_EXPORT UA_NodeId *createNumericNodeId(UA_UInt16 nsIndex, UA_Int32 identifier);
UA_EXPORT UA_NodeId *createCharNodeId(UA_UInt16 nsIndex, char const identifier[]);
UA_EXPORT UA_NodeId *createStringNodeId(UA_UInt16 nsIndex, UA_String *identifier);
UA_EXPORT void deleteNodeId(UA_NodeId *nodeId);

// create a scalar variant and free the memory
UA_EXPORT UA_Variant *createScalarVariant(UA_UInt16 dataType, void *value);
UA_EXPORT void deleteVariant(UA_Variant *variant);

// create a date & time struct and free the memory
UA_EXPORT UA_DateTimeStruct *createDateTimeStruct(UA_DateTime time);
UA_EXPORT void deleteDateTimeStruct(UA_DateTimeStruct *value);

#ifdef __cplusplus
} // extern "C"
#endif

#endif /* WRAPPER_62541_H_  */

