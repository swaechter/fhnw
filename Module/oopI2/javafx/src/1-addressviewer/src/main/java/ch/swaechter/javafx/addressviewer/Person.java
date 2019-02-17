package ch.swaechter.javafx.addressviewer;

import java.io.*;

public class Person implements NonStandardSerializable {

    public Person() {
        lastName.setLength(32);
        firstName.setLength(16);
        address.setLength(32);
    }

    //***** setter suite ***********************************************************
    public void setName(String lastName) {
        this.lastName = new StringBuilder(lastName);
        this.lastName.setLength(32);
    }

    public void setFirstName(String firstName) {
        this.firstName = new StringBuilder(firstName);
        this.firstName.setLength(16);
    }

    public void setAddress(String address) {
        this.address = new StringBuilder(address);
        this.address.setLength(32);
    }

    public void setBirthdate(int date) {
        this.birthDate = date;
    }

    public void setFinances(double money) {
        this.account = (float) money;
    }

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public void setMarried(boolean isMarried) {
        this.isMarried = isMarried;
    }

    public void setGlasses(boolean wearsGlasses) {
        this.wearsGlasses = wearsGlasses;
    }

    public void setMarker(boolean value) {
        this.isMarked = value;
    }

    //***** getter suite ***********************************************************
    public int getBirthdate() {
        return birthDate;
    }

    public float getMoney() {
        return account;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public boolean wearsGlasses() {
        return wearsGlasses;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public String getFirstName() {
        return firstName.toString().trim();
    }

    public String getLastName() {
        return lastName.toString().trim();
    }

    public String getAddress() {
        return address.toString().trim();
    }

    //***** display & serialization*************************************************
    public void show() {
        System.out.print("name         : ");
        System.out.println(lastName.toString().trim());
        System.out.print("first name   : ");
        System.out.println(firstName.toString().trim());
        System.out.print("birthdate    : ");
        System.out.println(birthDate);
        System.out.print("address      : ");
        System.out.println(address.toString().trim());
        System.out.print("account      : ");
        System.out.println(account);
        System.out.print("attributes   : ");
        System.out.print(isFemale ? "female " : "male ");
        System.out.print(isMarried ? "married " : "unmarried ");
        System.out.print(wearsGlasses ? "glasses " : "noGlasses ");
        System.out.print(isMarked ? "MARKED " : " ");
    }

    @Override
    public byte[] serialize() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bo);
        try {
            int bitSet = 0;
            out.writeBytes(lastName.toString());
            out.writeBytes(firstName.toString());
            out.writeInt(birthDate);
            out.writeBytes(address.toString());
            out.writeFloat(account);
            if (isFemale) ++bitSet;
            bitSet <<= 1;
            if (isMarried) ++bitSet;
            bitSet <<= 1;
            if (wearsGlasses) ++bitSet;
            bitSet <<= 1;
            if (isMarked) ++bitSet;
            out.writeByte(bitSet);
            bo.close();
        } catch (IOException e) {
            System.out.println("writeExternal: error");
        }
        return bo.toByteArray();
    }

    @Override
    public void deserialize(byte[] b) {
        int attributes;
        ByteArrayInputStream bi = new ByteArrayInputStream(b);
        DataInputStream in = new DataInputStream(bi);
        lastName.setLength(32);
        firstName.setLength(16);
        address.setLength(32);
        try {
            for (int j = 0; j < 32; ++j) {
                lastName.setCharAt(j, (char) in.read());
            }
            for (int j = 0; j < 16; ++j) {
                firstName.setCharAt(j, (char) in.read());
            }
            birthDate = in.readInt();
            for (int j = 0; j < 32; ++j) {
                address.setCharAt(j, (char) in.read());
            }
            account = in.readFloat();
            attributes = in.read();
            isFemale = ((attributes & 0x08) != 0);
            isMarried = ((attributes & 0x04) != 0);
            wearsGlasses = ((attributes & 0x02) != 0);
            isMarked = ((attributes & 0x01) != 0);
        } catch (IOException e) {
            System.out.println("readExternal: error");
        }
    }

    @Override
    public int datasetLength() {
        return SERIAL_DATA_SIZE;
    }

    //***** attributes & constants**************************************************
    private StringBuilder lastName = new StringBuilder();
    private StringBuilder firstName = new StringBuilder();
    private StringBuilder address = new StringBuilder();
    private int birthDate;
    private float account;
    private boolean isFemale;
    private boolean isMarried;
    private boolean wearsGlasses;
    private boolean isMarked;

    private static final int SERIAL_DATA_SIZE = 89;
}