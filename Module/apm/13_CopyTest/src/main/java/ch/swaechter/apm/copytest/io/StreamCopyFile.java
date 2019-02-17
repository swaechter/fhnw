package ch.swaechter.apm.copytest.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StreamCopyFile {

    static public void main(String args[]) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java StreamCopyFile infile outfile");
            System.exit(1);
        }

        long start = System.currentTimeMillis();
        copy(args[0], args[1], 0);
        System.out.println(System.currentTimeMillis() - start + "ms to copy " + args[0] + " to " + args[1]);
    }

    public static void copy(String src, String dest, int dummy) throws Exception {
        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(dest);

        int b = fin.read();
        while (b >= 0) {
            fout.write(b);
            b = fin.read();
        }

        fout.close();
    }
}
