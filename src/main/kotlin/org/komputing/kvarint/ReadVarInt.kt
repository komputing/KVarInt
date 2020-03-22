package org.komputing.kvarint

import okio.BufferedSource
import java.lang.IllegalArgumentException
import kotlin.UInt.Companion.MIN_VALUE
import kotlin.experimental.and

@ExperimentalUnsignedTypes
@Throws(IllegalArgumentException::class)
fun BufferedSource.readVarUInt(): UInt {
    var result = 0U

    if (exhausted()) return result

    (0 until 4 * 7 step 7).forEach { shift ->
        val current = readByte().toUInt()
        result = result or current.and(0x7FU).shl(shift)
        if (current and 0x80U == 0U || exhausted()) return result
    }

    throw IllegalArgumentException("VarInt input too big")
}