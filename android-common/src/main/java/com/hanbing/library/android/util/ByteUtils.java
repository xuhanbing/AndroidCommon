package com.hanbing.library.android.util;

/**
 * Created by hanbing on 2016/4/14.
 */
public class ByteUtils {

    public static final int LE = 0;
    public static final int BE = 1;

    static BigEndian mBigEndian;
    static LittleEndian mLittleEndian;


    public static Endian getInstance(int Endian)
    {
        if (BE == Endian)
        {
            if (null == mBigEndian)
                mBigEndian = new BigEndian();

            return mBigEndian;
        } else {
            if (null == mLittleEndian)
                mLittleEndian = new LittleEndian();

            return mLittleEndian;
        }
    }

    public static abstract class Endian {
        public abstract byte[] shortToBytes(short value);

        public short bytesToShort(byte[] bytes)
        {
            return bytesToShort(bytes, 0);
        }
        public abstract short bytesToShort(byte[] bytes, int offset);

        public abstract byte[] intToBytes(int value);

        public int bytesToInt(byte[] bytes)
        {
            return bytesToInt(bytes, 0);
        }
        public abstract int bytesToInt(byte[] bytes, int offset);

        public abstract byte[] longToBytes(long value);

        public long bytesToLong(byte[] bytes)
        {
            return bytesToLong(bytes, 0);
        }
        public abstract long bytesToLong(byte[] bytes, int offset);
    }

    public static class BigEndian extends Endian{
        public byte[] shortToBytes(short value)
        {
            byte [] bytes = new byte[2];

            bytes[0] = (byte) ((value >> 8) & 0xff);
            bytes[1] = (byte) ((value >> 0) & 0xff);

            return bytes;
        }


        public short bytesToShort(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 2);

            short value = (short) (((bytes[offset + 0] & 0xff) << 8)
                                | ((bytes[offset + 1] & 0xff) << 0));

            return value;
        }

        public byte[] intToBytes(int value)
        {
            byte [] bytes = new byte[4];

            bytes[0] = (byte) ((value >> 24) & 0xff);
            bytes[1] = (byte) ((value >> 16) & 0xff);
            bytes[2] = (byte) ((value >> 8) & 0xff);
            bytes[3] = (byte) ((value >> 0) & 0xff);

            return bytes;
        }

        public int bytesToInt(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 4);

            int v0 = (bytes[offset + 0] & 0xff) << 24;
            int v1 = (bytes[offset + 1] & 0xff) << 16;
            int v2 = (bytes[offset + 2] & 0xff) << 8;
            int v3 = (bytes[offset + 3] & 0xff) << 0;

            int value = v0 | v1 | v2 | v3;

            return value;
        }

        public byte[] longToBytes(long value)
        {
            byte [] bytes = new byte[8];

            bytes[0] = (byte) ((value >> 56) & 0xff);
            bytes[1] = (byte) ((value >> 48) & 0xff);
            bytes[2] = (byte) ((value >> 40) & 0xff);
            bytes[3] = (byte) ((value >> 32) & 0xff);
            bytes[4] = (byte) ((value >> 24) & 0xff);
            bytes[5] = (byte) ((value >> 16) & 0xff);
            bytes[6] = (byte) ((value >> 8) & 0xff);
            bytes[7] = (byte) ((value >> 0) & 0xff);

            return bytes;

        }


        public long bytesToLong(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 8);

            long v0 = (bytes[offset + 0] & 0xff) ;
            long v1 = (bytes[offset + 1] & 0xff) ;
            long v2 = (bytes[offset + 2] & 0xff);
            long v3 = (bytes[offset + 3] & 0xff) ;
            long v4 = (bytes[offset + 4] & 0xff) ;
            long v5 = (bytes[offset + 5] & 0xff);
            long v6 = (bytes[offset + 6] & 0xff) ;
            long v7 = (bytes[offset + 7] & 0xff) ;

            v0 <<= 8 * 7;
            v1 <<= 8 * 6;
            v2 <<= 8 * 5;
            v3 <<= 8 * 4;
            v4 <<= 8 * 3;
            v5 <<= 8 * 2;
            v6 <<= 8 * 1;

            long value = v0 | v1 | v2 | v3 | v4 | v5 | v6 | v7;

            return value;

        }


    }


    public static class LittleEndian extends Endian{
        public byte[] shortToBytes(short value)
        {
            byte [] bytes = new byte[2];

            bytes[1] = (byte) ((value >> 8) & 0xff);
            bytes[0] = (byte) ((value >> 0) & 0xff);

            return bytes;
        }


        public short bytesToShort(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 2);

            short value = (short) (((bytes[offset + 1] & 0xff) << 8)
                    | ((bytes[offset + 0] & 0xff) << 0));

            return value;
        }

        public byte[] intToBytes(int value)
        {
            byte [] bytes = new byte[4];

            bytes[3] = (byte) ((value >> 24) & 0xff);
            bytes[2] = (byte) ((value >> 16) & 0xff);
            bytes[1] = (byte) ((value >> 8) & 0xff);
            bytes[0] = (byte) ((value >> 0) & 0xff);

            return bytes;
        }

        public int bytesToInt(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 4);

            int v0 = (bytes[offset + 3] & 0xff) << 24;
            int v1 = (bytes[offset + 2] & 0xff) << 16;
            int v2 = (bytes[offset + 1] & 0xff) << 8;
            int v3 = (bytes[offset + 0] & 0xff) << 0;

            int value = v0 | v1 | v2 | v3;

            return value;
        }

        public byte[] longToBytes(long value)
        {
            byte [] bytes = new byte[8];

            bytes[7] = (byte) ((value >> 56) & 0xff);
            bytes[6] = (byte) ((value >> 48) & 0xff);
            bytes[5] = (byte) ((value >> 40) & 0xff);
            bytes[4] = (byte) ((value >> 32) & 0xff);
            bytes[3] = (byte) ((value >> 24) & 0xff);
            bytes[2] = (byte) ((value >> 16) & 0xff);
            bytes[1] = (byte) ((value >> 8) & 0xff);
            bytes[0] = (byte) ((value >> 0) & 0xff);

            return bytes;

        }


        public long bytesToLong(byte[] bytes, int offset)
        {
            checkValues(bytes, offset, 8);

            long v0 = (bytes[offset + 7] & 0xff) ;
            long v1 = (bytes[offset + 6] & 0xff) ;
            long v2 = (bytes[offset + 5] & 0xff);
            long v3 = (bytes[offset + 4] & 0xff) ;
            long v4 = (bytes[offset + 3] & 0xff) ;
            long v5 = (bytes[offset + 2] & 0xff);
            long v6 = (bytes[offset + 1] & 0xff) ;
            long v7 = (bytes[offset + 0] & 0xff) ;

            v0 <<= 8 * 7;
            v1 <<= 8 * 6;
            v2 <<= 8 * 5;
            v3 <<= 8 * 4;
            v4 <<= 8 * 3;
            v5 <<= 8 * 2;
            v6 <<= 8 * 1;

            long value = v0 | v1 | v2 | v3 | v4 | v5 | v6 | v7;

            return value;

        }


    }

    private static void checkValues(byte[] bytes, int offset, int length)
    {
        if (null == bytes)
        {
            throw new NullPointerException("bytes is null");
        }

        int size = bytes.length;

        if (offset < 0 || length < 0)
        {
            throw new IllegalArgumentException("offset or length must > 0, current offset = " + offset + ", length = " + length);
        }

        if (offset + length > size)
        {
            throw new IndexOutOfBoundsException("index out of bounds, size = " + size + ",  offset = " + offset + ", length = " + length);
        }

    }


}
