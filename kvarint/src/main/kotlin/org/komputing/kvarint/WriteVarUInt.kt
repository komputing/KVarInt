package org.komputing.kvarint

import okio.BufferedSink
import java.lang.IllegalArgumentException

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