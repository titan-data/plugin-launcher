package io.titandata.plugin.remote

import com.google.protobuf.Empty

class RemoteClient(val stub: RemoteGrpc.RemoteBlockingStub) : Remote {
    override fun type(): String {
        val req = Empty.newBuilder().build()
        val res = stub.type(req)
        return res.type
    }

    override fun fromURL(url: String, properties: Map<String, String>): Map<String, Any> {
        TODO("not implemented")
    }

    override fun toURL(properties: Map<String, Any>): Pair<String, Map<String, String>> {
        TODO("not implemented")
    }

    override fun getParameters(properties: Map<String, Any>): Map<String, Any> {
        TODO("not implemented")
    }
}
