package ch.swaechter.apm.copytest;

import ch.swaechter.apm.copytest.io.FastStreamCopyFile;
import ch.swaechter.apm.copytest.nio.CopyFile;
import ch.swaechter.apm.copytest.nio.FastCopyFile;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class CopyTiming {

    static private int count = 31;

    static private Random random = new Random();

    static private Class<?> clazz[] = new Class[]{/*StreamCopyFile.class,*/ FastStreamCopyFile.class, CopyFile.class, FastCopyFile.class};

    static public void main(String[] args) throws Exception {
        // 1.) 8 KB aka 8192 (HDD Block Size)
        // 2.) 0.5 MB aka 524288
        // 3.) 1 MB aka 1048576
        // 4.) 10 MB aka 10485760
        // 5.) 100 MB aka 104857600
        int[] blockSizes = new int[]{8192, 524288, 1048576, 10485760, 104857600};
        for (int blockSize : blockSizes) {
            doTest(blockSize);
        }
    }

    static public void doTest(int blocksize) throws Exception {
        long[][] times = new long[count][clazz.length];
        for (int i = 0; i < count; i++) {
            int filesize = 1 << i;
            //System.out.println("Filesize: " + filesize);
            createFile(filesize);
            //System.out.println("File created");
            for (int j = 0; j < clazz.length; j++) {
                //System.out.println(clazz[j].getName());
                long m;
                if (clazz[j].getName().contains("Stream")) { // the slug
                    if (filesize < 8192) m = runRuns(clazz[j], 10, blocksize);
                    else m = runRuns(clazz[j], 5, blocksize);
                } else {
                    m = runRuns(clazz[j], 10, blocksize);
                }
                times[i][j] = m;
                //System.out.println("   Average: " + times[i][j] + "ms");
            }
            if (new File("orig.bin").delete()) {
                //System.out.println("   Deleted");
            } else {
                //System.out.println("   Deletion failed");
            }
        }
        printTestResults(times, blocksize);
    }

    static private void createFile(int size) throws Exception {
        FileOutputStream fout = new FileOutputStream("orig.bin");
        FileChannel fc = fout.getChannel();
        byte[] bytes = new byte[1024];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        if (size < 1024) {
            random.nextBytes(bytes);
            buffer.position(0).limit(size);
            fc.write(buffer);
        } else {
            for (int i = 0; i < size / 1024; i++) {
                random.nextBytes(bytes);
                buffer.position(0).limit(1024);
                fc.write(buffer);
            }
        }
        fc.close();
    }

    static private long runRuns(Class<?> clazz, int runs, int blocksize) throws Exception {
        Method m = clazz.getMethod("copy", String.class, String.class, int.class);
        long t = 0;
        for (int i = 0; i < runs; i++) {
            long start = System.currentTimeMillis();
            m.invoke(null, "orig.bin", "copy.bin", blocksize);
            long end = System.currentTimeMillis();

            t += (end - start);
            new File("copy.bin").delete();
        }
        return t / runs;
    }

    static private void printTestResults(long[][] times, int blocksize) {
        System.out.print("Filesize (" + blocksize + "): ");
        for (int i = 0; i < clazz.length; i++) {
            System.out.print(", " + clazz[i].getSimpleName());
        }
        System.out.println();
        for (int i = 0; i < count; i++) {
            System.out.print(1 << i);
            for (int j = 0; j < times[i].length; j++) {
                System.out.print(", " + times[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
