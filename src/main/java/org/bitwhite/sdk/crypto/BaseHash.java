package org.bitwhite.sdk.crypto;

public abstract class BaseHash {

    protected String name;
    protected int hashSize;
    protected int blockSize;
    protected long count;
    protected byte[] buffer;

    protected BaseHash(String name, int hashSize, int blockSize)
    {
        super();

        this.name = name;
        this.hashSize = hashSize;
        this.blockSize = blockSize;
        this.buffer = new byte[blockSize];

        resetContext();
    }

    public String name()
    {
        return name;
    }

    public int hashSize()
    {
        return hashSize;
    }

    public int blockSize()
    {
        return blockSize;
    }

    public void update(byte b)
    {
        int i = (int) (count % blockSize);
        count++;
        buffer[i] = b;
        if (i == (blockSize - 1))
        {
            transform(buffer, 0);
        }
    }

    public void update(byte[] b) {
        update(b, 0, b.length);
    }

    public void update(byte[] b, int offset, int len) {
        int n = (int) (count % blockSize);
        count += len;
        int partLen = blockSize - n;
        int i = 0;

        if (len >= partLen) {
            System.arraycopy(b, offset, buffer, n, partLen);
            transform(buffer, 0);
            for (i = partLen; i + blockSize - 1 < len; i += blockSize)
            {
                transform(b, offset + i);
            }
            n = 0;
        }

        if (i < len) {
            System.arraycopy(b, offset + i, buffer, n, len - i);
        }
    }

    public byte[] digest() {
        byte[] tail = padBuffer(); // pad remaining bytes in buffer
        update(tail, 0, tail.length); // last transform of a message
        byte[] result = getResult(); // make a result out of context

        reset(); // reset this instance for future re-use

        return result;
    }

    public void reset() { // reset this instance for future re-use
        count = 0L;
        for (int i = 0; i < blockSize;)
        {
            buffer[i++] = 0;
        }

        resetContext();
    }

    // methods to be implemented by concrete subclasses ------------------------
    public abstract Object clone();


    /**
     * <p>Returns the byte array to use as padding before completing a hash
     * operation.</p>
     *
     * @return the bytes to pad the remaining bytes in the buffer before
     * completing a hash operation.
     */
    protected abstract byte[] padBuffer();

    /**
     * <p>Constructs the result from the contents of the current context.</p>
     *
     * @return the output of the completed hash operation.
     */
    protected abstract byte[] getResult();

    /** Resets the instance for future re-use. */
    protected abstract void resetContext();

    /**
     * <p>The block digest transformation per se.</p>
     *
     * @param in the <i>blockSize</i> long block, as an array of bytes to digest.
     * @param offset the index where the data to digest is located within the
     * input buffer.
     */
    protected abstract void transform(byte[] in, int offset);
}