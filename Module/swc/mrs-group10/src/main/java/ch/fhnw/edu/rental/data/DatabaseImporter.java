package ch.fhnw.edu.rental.data;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

/**
 * used to import data into the database.
 * 
 */
public class DatabaseImporter {

    /**
     * Location of the default database relative to
     * project root (the res folder is in the classpath). 
     */
    private String datafile = "/data/dataset.xml";
    
    /**
     *  
     * @return gets the name of the data file to be imported 
     */
    protected String getDataFile() {
        return datafile;
    }
    
    /**
     *  
     * @param datafile sets the name of the data file to be imported 
     */
    protected void setDataFile(String datafile) {
        this.datafile = datafile;
    }
    
    /**
     * Import data into database.
     * 
     * @param connection database connection
     * @throws Exception throws various exceptions
     */
    public void importData(Connection connection) throws Exception {
        InputStream inStream;
        IDataSet dataSet;

        DatabaseConnection dbconnection = new DatabaseConnection(connection);

        // initialize the data set
        DatabaseConfig config = dbconnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        inStream = this.getClass().getResourceAsStream(getDataFile());

        try {
            
            // put check here to ensure, finally is always executed
            if (inStream == null) {
                throw new FileNotFoundException("Datafile: " + getDataFile() + " not found");
            }

            // if no modified data set is available the test data set is loaded
            dataSet = new FlatXmlDataSetBuilder().build(inStream);

            DatabaseOperation.CLEAN_INSERT.execute(dbconnection, dataSet);
        } finally {
            dbconnection.close();
            connection.close();
        }
    }

}
