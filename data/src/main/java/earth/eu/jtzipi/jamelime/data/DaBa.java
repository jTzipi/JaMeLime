/*
 * MIT License
 *
 * Copyright (c) 2021 Tim Langhammer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package earth.eu.jtzipi.jamelime.data;

import earth.eu.jtzipi.modules.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;

public enum DaBa {

    SINGLE;

    public static void init() throws IOException, SQLException {



        try( InputStream is = DaBa.class.getResourceAsStream( FILE_DB_PROPERTIES ) ) {

            DB_PROP.load( is );
            LOG.info( FILE_DB_PROPERTIES + " loaded!" );
        }

     boolean dbCreated = isDBCreated();
        LOG.info( "DB found ? " +dbCreated );
        if(!dbCreated){
            create(  );
        }
    }

    /**
     * Return whether db file is created.
     * @return
     * @throws IOException
     */
    public static boolean isDBCreated() throws IOException {

        LOG.info( "Path for db '" +PATH_DB+"'" );
        return Files.isReadable( PATH_DB ) && Files.size( PATH_DB ) > 0L;
    }

    /**
     * Try to create the 'JaMeLime' DB.
     * @throws IOException if dir to db not read
     * @throws SQLException if creation failed
     * @throws IllegalStateException if DB is created
     */
    public static void createDB() throws IOException, SQLException {
        if( isDBCreated()) {
            throw new IllegalStateException("Warning! DB for path " + PATH_DB + " is already created"  );
        }
        if( !Files.isReadable( Paths.get( DB_DIR ) ) ){
            throw new IOException("DB dir not readable");
        }
        create();
    }

    private static void create(  ) throws SQLException, IOException {


        // DB Path we want to create our h2 db
        String dbUrl = "jdbc:h2:~/" +DB_DIR_REL + "/" + DB; // ~/CPT/Java/Projects/Maven/JaMeLime/res/db/JaMeLime

        LOG.warn( "Create DB >> '" + dbUrl );

        // TODO: change db user and pw
        // ARM
        try ( Connection c = DriverManager.getConnection( dbUrl, "sa", "" );
              Statement stmt = c.createStatement() ) {


            LOG.info( "Connected! use  " + c.getSchema() );
            boolean gadi = stmt.execute( "CREATE USER IF NOT EXISTS " + DB_ADMIN_USER + " PASSWORD '" + DB_PW + "' ADMIN" );
            //LOG.info( "Admin created :" + gadi );
            gadi = stmt.execute( "CREATE SCHEMA IF NOT EXISTS "+ DB_SCHEMA + " AUTHORIZATION " + DB_ADMIN_USER );
            //LOG.info( "GADI created :"  + gadi );
        }



        LOG.info( "DB File is created:" + isDBCreated() );

    }




    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( DaBa.class );

    private static final Properties DB_PROP = new Properties();
    private static final Properties HIBERNATE_PROP = new Properties();

    private static final String FILE_DB_PROPERTIES = "db.properties";
    private static final String FILE_HIBERNATE_PROPERTIES = "hibernate.properties";

    private static final String KEY_DB_CREATION_DATE = "db.creation.date";

    static final String DB = "JaMeLime";
     static final String DB_SUB_DIR = "res/db";
    static final String DB_DIR_REL = IOUtils.getHomeDir().relativize( IOUtils.getProgramDir() ) .resolve( DB_SUB_DIR ).toString();
     static final String DB_DIR = IOUtils.getProgramDir().resolve( DB_SUB_DIR ).toString();
     static final String DB_ADMIN_USER = "GADI_ADMIN";
     static final String DB_SCHEMA = "GADI";
     static final String DB_PW = "Gadi$Eisenkot&Super";
    static final String DB_URL = "jdbc:h2:~/" + DB_DIR_REL + "/" + DB + ";SCHEMA=" + DB_SCHEMA;
    private static final Path PATH_DB =Paths.get( DB_DIR ).resolve( DB + ".mv.db" );
}
