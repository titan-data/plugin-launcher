package io.titandata.plugin.remote

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

class RemoteFactoryTest : StringSpec() {

    val pluginDirectory = System.getProperty("pluginDirectory")
    val remoteFactory = RemoteFactory(pluginDirectory)

    init {
        "pluginDirectory property is configured" {
            pluginDirectory shouldNotBe null
        }

        "can start echo process" {
            val p = remoteFactory.startProcess("echo")
            p.destroy()
        }

        "get header succeeds" {
            val p = remoteFactory.startProcess("echo")
            try {
                val header = remoteFactory.getHeader(p)
                header.coreVersion shouldBe 1
                header.protoVersion shouldBe 1
                header.protoType shouldBe "grpc"
                header.serverCert shouldBe ""
            } finally {
                p.destroy()
            }
        }

        "get managed channel succeeds" {
            val p = remoteFactory.startProcess("echo")
            try {
                val header = remoteFactory.getHeader(p)
                val mc = remoteFactory.getManagedChannel(header)
                mc.shutdownNow()
            } finally {
                p.destroy()
            }
        }

        "get remote type" {
            val r = remoteFactory.load("echo")
            try {
                r.type() shouldBe "echo"
                r.type() shouldBe "echo"
            } finally {
                remoteFactory.unload("echo")
            }
        }
    }
}
