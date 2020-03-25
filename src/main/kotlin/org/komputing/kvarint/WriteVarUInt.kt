package org.komputing.kvarint

import okio.BufferedSink
import okio.BufferedSource
import java.lang.IllegalArgumentException
import kotlin.UInt.Companion.MIN_VALUE
import kotlin.experimental.and

@ExperimentalUnsignedTypes
@Throws(IllegalArgumentException::class)
fun BufferedSink.writeVarUInt(value: UInt) {
    var currentValue = value
    while (true) {
        val maskedValue = currentValue.and(0x7fU).toInt()
        currentValue = currentValue.shr(7)
        if (currentValue == 0U) {
            writeByte(maskedValue)
            return
        } else {
            writeByte(maskedValue.or(0x80))
        }
    }
}