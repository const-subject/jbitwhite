package org.bitwhite.sdk.trs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Asset {

    private static final int MAX_BUFFER_SIZE = 1024 * 5;

    public byte[] assetBytes(){
        ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE)
                .order(ByteOrder.LITTLE_ENDIAN);

        addBytes(buffer);

        buffer.flip();
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);

        return result;
    }

    public void addBytes(ByteBuffer buffer) {}
}