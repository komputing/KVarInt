package org.komputing.kvarint.model

import okio.buffer
import okio.source
import org.junit.jupiter.params.provider.Arguments
import org.komputing.kbin.binToByteArray
import org.komputing.kbin.model.BinaryString

@ExperimentalUnsignedTypes
object TestVectors {
    fun String.binToBuffer() = BinaryString(this).binToByteArray().inputStream().source().buffer()
    data class TestVector constructor(val stringInput: String, val expectedUInt: UInt)

    private fun testVectorArgument(str: String, uint: UInt) = Arguments.of(TestVector(str, uint))
    val testVectors = listOf(
            testVectorArgument("00000001", 1U),
            testVectorArgument("00101010", 42U),
            testVectorArgument("01111111", 127U),
            testVectorArgument("10000000 00000001", 128U),
            testVectorArgument("11111111 00000001", 255U),
            testVectorArgument("10101100 00000010", 300U),
            testVectorArgument("10000000 10000000 00000001", 16384U),
            testVectorArgument("11111111 11111111 11111111 01111111", UInt.MAX_VALUE.shr(4))
    )
}