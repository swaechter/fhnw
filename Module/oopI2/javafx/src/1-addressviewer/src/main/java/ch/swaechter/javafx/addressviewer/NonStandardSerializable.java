package ch.swaechter.javafx.addressviewer;

public interface NonStandardSerializable {

    byte[] serialize();

    void deserialize(byte[] b);

    int datasetLength();
}
