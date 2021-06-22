[![](https://jitpack.io/v/komputing/kvarint.svg)](https://jitpack.io/#komputing/kvarint)

# KVarInt

Kotlin library providing a [okio](https://square.github.io/okio) kvarint reader

## Usage

```kotlin
val buffer: BufferedSource = "11111111 00000001".binToBuffer()
val result: UInt = buffer.readVarUInt()
```

now result will hold the value of 255U

## Links 
 * https://github.com/multiformats/unsigned-varint

# Projects using this library
 * the EIP-1577 module in KEthereum

# License
MIT
