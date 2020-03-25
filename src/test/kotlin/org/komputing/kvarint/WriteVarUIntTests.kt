package org.komputing.kvarint

import com.google.common.truth.Truth.assertThat
import okio.Buffer
import okio.BufferedSink
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.komputing.kvarint.model.TestVectors
import org.komputing.kvarint.model.TestVectors.binToBuffer

@ExperimentalUnsignedTypes
class WriteVarUIntTests {

    companion object {
        @JvmStatic
        fun provideTestVectors() = TestVectors.testVectors
    }

    @ParameterizedTest
    @MethodSource("provideTestVectors")
    fun `can write varUInt`(testVector: TestVectors.TestVector) {
        val sink = Buffer() as BufferedSink
        sink.writeVarUInt(testVector.expectedUInt)

        assertThat(sink.buffer.readByteArray()).isEqualTo(testVector.stringInput.binToBuffer().readByteArray())
    }

}
