package ch.swaechter.apm.copytest.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FastCopyFile {

    static public void main(String args[]) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java StreamCopyFile infile outfile");
            System.exit(1);
        }

        long start = System.currentTimeMillis();
        copy(args[0], args[1], 1024);
        System.out.println(System.currentTimeMillis() - start + "ms to copy " + args[0] + " to " + args[1]);
    }

    static public void copy(String src, String dest, int buffersize) throws Exception {
        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(dest);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(buffersize);

        int r = fcin.read(buffer);
        while (r >= 0) {
            buffer.flip();

            fcout.write(buffer);
            buffer.clear();
            r = fcin.read(buffer);
        }
    }
}
