package org.komputing.kvarint

import com.google.common.truth.Truth.assertThat
import okio.BufferedSource
import okio.buffer
import okio.source
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.komputing.kbin.binToByteArray
import org.komputing.kbin.model.BinaryString
import kotlin.test.assertFailsWith

@ExperimentalUnsignedTypes
class KVarIntTests {

    private fun String.binToBuffer() = BinaryString(this).binToByteArray().inputStream().source().buffer()
    data class TestVector(val stringInput: String, val expectedUInt: UInt)

    companion object {
        private fun testVectorArgument(str: String, uint: UInt) = Arguments.of(TestVector(str, uint))

        @JvmStatic
        fun provideTestVectors() = listOf(
                testVectorArgument("00000001", 1U),
                testVectorArgument("00101010", 42U),
                testVectorArgument("01111111", 127U),
                testVectorArgument("10000000 00000001", 128U),
                testVectorArgument("11111111 00000001", 255U),
                testVectorArgument("10101100 00000010", 300U),
                testVectorArgument("10000000 10000000 00000001", 16384U),
                testVectorArgument("11111111".repeat(4) , UInt.MAX_VALUE.shr(4))
        )
    }

    @ParameterizedTest
    @MethodSource("provideTestVectors")
    fun `can read varUInt`(testVector: TestVector) {
        val binToBuffer: BufferedSource = testVector.stringInput.binToBuffer()
        assertThat(binToBuffer.readVarUInt()).isEqualTo(testVector.expectedUInt.toUInt())
    }


    @Test
    fun `should throw illegalArgumentException for too long input`() {

        assertFailsWith<IllegalArgumentException> {
            "11111111".repeat(5).binToBuffer().readVarUInt()
        }

    }

    @Test
    fun `will return 0 fo empty array`() {
        assertThat("".binToBuffer().readVarUInt()).isEqualTo(0.toUInt())
    }
}
