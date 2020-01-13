/*
 * Copyright The Titan Project Contributors
 */
package io.titandata.plugin.remote

import com.google.protobuf.Empty
import io.titandata.plugin.StructUtil

class RemoteClient(val stub: RemoteGrpc.RemoteBlockingStub) : Remote {
    private val structUtil = StructUtil()

    override fun type(): String {
        val req = Empty.newBuilder().build()
        val res = stub.type(req)
        return res.type
    }

    override fun fromURL(url: String, properties: Map<String, String>): Map<String, Any> {
        val req = RemoteProto.ExtendedURL.newBuilder()
                .setUrl(url)
                .putAllValues(properties)
                .build()
        val res = stub.fromURL(req)
        return structUtil.structToMap(res.values)
    }

    override fun toURL(properties: Map<String, Any>): Pair<String, Map<String, String>> {
        val req = RemoteProto.RemoteProperties.newBuilder()
                .setValues(structUtil.mapToStruct(properties))
                .build()
        val res = stub.toURL(req)
        return res.url to res.valuesMap
    }

    override fun getParameters(properties: Map<String, Any>): Map<String, Any> {
        val req = RemoteProto.RemoteProperties.newBuilder()
                .setValues(structUtil.mapToStruct(properties))
                .build()
        val res = stub.getParameters(req)
        return structUtil.structToMap(res.values)
    }
}
