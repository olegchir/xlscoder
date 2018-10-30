package com.xlscoder.lang;

import java.io.*;

public class Files {
    public static InputStream restream(ByteArrayOutputStream src) throws IOException {
        PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream(in);
        src.writeTo(out);
        return in;
    }
}
