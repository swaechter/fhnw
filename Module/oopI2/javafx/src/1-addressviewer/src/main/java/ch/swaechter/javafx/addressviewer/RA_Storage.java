package ch.swaechter.javafx.addressviewer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RA_Storage<T extends NonStandardSerializable> {

    public RA_Storage(String fileName, Class<T> type) {
        File fname = new File(fileName);
        storedClass = new String(type.getName());
        try {
            //***** file doesn't exist: create new one. ******************************
            if (!fname.exists()) {
                if (!fname.createNewFile())                         // create has failed!!!
                    return;
                else {                                              // new file: write header
                    dFile = new RandomAccessFile(fileName, "rw");
                    dFile.writeInt(MAGIC_WORD);                        // write magic word
                    dFile.writeInt(0);                                 // write record length (yet unknown)
                    int i = 0;                                           // write class name
                    while (i < storedClass.length())
                        dFile.writeByte(storedClass.charAt(i++));
                    while (i++ < MAX_NAME_SIZE)
                        dFile.writeByte(0x00);
                    isConnected = true;
                }
                //***** file exists already: validation **********************************
            } else {
                dFile = new RandomAccessFile(fileName, "rw");
                if (dFile.length() < HEADER_SIZE) {                    // !! header too short
                    dFile.close();
                    return;
                }
                dFile.seek(0);
                if (dFile.readInt() != MAGIC_WORD) {                   // !! magic doesn't match
                    dFile.close();
                    return;
                }
                recordLength = dFile.readInt();
                if (recordLength != 0 && (dFile.length() - HEADER_SIZE) % recordLength != 0) {
                    dFile.close();                                      // !! recsize doesn't match
                    return;
                }

                byte[] nameBuffer = new byte[MAX_NAME_SIZE];
                dFile.read(nameBuffer);
                int i = 0;
                while (i < MAX_NAME_SIZE && nameBuffer[i] != 0) ++i;
                String actualElementClass = new String(nameBuffer, 0, i);
                if (!actualElementClass.equals(storedClass)) {         // !! element type doesn't match
                    dFile.close();
                    throw new ElementTypeMismatchException("mismatching element type");
                }

                isConnected = true;                                   // :) file is recognized as correct
            }
        } catch (IOException e) {
            System.out.println("I/O exception!!");
        }
    }

    // clean up ressources when object is disposed by gc
    @Override
    public void finalize() {
        close();
    }


    // close file & disconnect adapter
    public void close() {
        if (isConnected) {
            try {
                dFile.close();
            } catch (IOException e) {
            }
            isConnected = false;
            recordLength = 0;
            storedClass = null;
        }
    }


    // append a new element to the file
    public void appendItem(T element) {
        try {
            if (isConnected) {
                dFile.seek(dFile.length());       // append record
                byte[] t = element.serialize();
                dFile.write(t);
                if (recordLength == 0) {             // if record length is not known already
                    dFile.seek(0);
                    dFile.writeInt(MAGIC_WORD);
                    dFile.writeInt(element.datasetLength());
                    recordLength = element.datasetLength();
                }
            }
        } catch (IOException e) {
        }
    }


    public void writeItem(int index, T element) {
        // pre-condition: obj must be a valid object
        try {
            if (isConnected && itemCount() > index) {
                dFile.seek(HEADER_SIZE + index * recordLength);
                byte[] b = element.serialize();
                dFile.write(b, 0, recordLength);
            }
        } catch (IOException e) {
        }
    }


    public void readItem(int index, T element) {
        // pre-condition: obj must be a valid object
        try {
            if (isConnected && itemCount() > index && element != null) {
                dFile.seek(HEADER_SIZE + index * recordLength);
                byte[] b = new byte[recordLength];
                dFile.read(b, 0, recordLength);
                element.deserialize(b);
            }
        } catch (IOException e) {
        }
    }


    public long itemCount() {
        if (!isConnected)
            return -1;
        else if (recordLength == 0)
            return 0;
        else {
            long s = 0;
            try {
                s = dFile.length();
            } catch (IOException e) {
            }
            return (s - HEADER_SIZE) / recordLength;
        }
    }


    public int datasetSize() {
        return recordLength;
    }

    public String className() {
        return storedClass.toString();
    }

    public boolean isConnected() {
        return isConnected;
    }

//***** own exception types ****************************************************

    public static class ElementTypeMismatchException extends RuntimeException {
        public static final long serialVersionUID = 20160204;

        public ElementTypeMismatchException(String message) {
            super(message);
        }
    }


    //***** attributes & constants *************************************************
    private int recordLength;     // length of dataset
    private String storedClass;      // branded classname
    private boolean isConnected;      // connection state
    private RandomAccessFile dFile;            // file stream

    private static int MAX_NAME_SIZE = 56;
    private static int HEADER_SIZE = MAX_NAME_SIZE + 8;
    private static int MAGIC_WORD = 0xAFFEBABA;

}
