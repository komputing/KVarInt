package org.komputing.kvarint

import com.google.common.truth.Truth.assertThat
import okio.BufferedSource
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.komputing.kvarint.model.TestVectors
import org.komputing.kvarint.model.TestVectors.binToBuffer
import kotlin.test.assertFailsWith

@ExperimentalUnsignedTypes
class ReadVarUIntTests {

    companion object {
        @JvmStatic
        fun provideTestVectors() = TestVectors.testVectors
    }

    @ParameterizedTest
    @MethodSource("provideTestVectors")
    fun `can read varUInt`(testVector: TestVectors.TestVector) {
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
