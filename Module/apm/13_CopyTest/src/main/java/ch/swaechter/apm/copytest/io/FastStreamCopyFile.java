package ch.swaechter.apm.copytest.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FastStreamCopyFile {

    static public void main(String args[]) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java StreamCopyFile infile outfile");
            System.exit(1);
        }

        long start = System.currentTimeMillis();
        copy(args[0], args[1], 0);
        System.out.println(System.currentTimeMillis() - start + "ms to copy " + args[0] + " to " + args[1]);
    }

    public static void copy(String src, String dest, int blocksize) throws Exception {
        FileInputStream fin = new FileInputStream(src);
        BufferedInputStream bin = new BufferedInputStream(fin, blocksize);
        FileOutputStream fout = new FileOutputStream(dest);
        BufferedOutputStream bout = new BufferedOutputStream(fout, blocksize);

        byte[] block = new byte[blocksize];
        int b = bin.read(block, 0, blocksize);
        while (b >= 0) {
            bout.write(block, 0, b);
            b = bin.read(block, 0, blocksize);
        }
        bin.close();
        bout.close();
    }
}
