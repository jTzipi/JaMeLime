package earth.eu.jtzipi.jamelime.gui;

import earth.eu.jtzipi.modules.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Properties;

public enum IO {

    /**
     * Singleton.
     */
    SINGLETON;

    /**
     * test if path to global properties file is readable.
     * @return {@code false} if not else {@code true}
     */
    static boolean isGlobalPropertiesReadable() {
        return Files.isReadable(PATH_PROPERTIES_CNF);
    }

    /**
     * Read global JaMeLime Properties.
     * @return Properties
     * @throws IOException if failed
     * @throws IllegalStateException if {@code #PATH_PROPERTIES_CNF} is not readable
     */
    public static Properties readGlobalProperties(  ) throws IOException {
        if( !isGlobalPropertiesReadable() ) {
            throw new IllegalStateException(new IOException("Config File '" + JAMELIME_PROP + "' is not found!" ));
        }

        Properties prop = IOUtils.loadProperties( PATH_PROPERTIES_CNF );
        LOG.info( PATH_PROPERTIES_CNF + " read!" );
        return prop;
    }

    /**
     * Write global JaMeLime properties file.
     * @param prop properties
     * @param comment comment (optional)
     * @throws IOException
     */
    public static void writeGlobalProperties( Properties prop, String comment ) throws IOException {

        Objects.requireNonNull( prop );
        if(!isGlobalPropertiesReadable()) {

            throw new IllegalStateException(new IOException("Config File '" + JAMELIME_PROP + "' is not found!" ));
        }

        try( BufferedWriter bw = Files.newBufferedWriter( PATH_PROPERTIES_CNF, StandardOpenOption.WRITE ) ) {
                prop.store( bw, comment );
        }

        LOG.info( "Wrote '" + JAMELIME_PROP + "' Properties!" );
    }

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( IO.class );

    public static final String JAMELIME_PROP = "JaMeLime.properties";
    public static final Path PATH_PROPERTIES_CNF = IOUtils.getProgramDir().resolve( "cnf" ).resolve( JAMELIME_PROP );

}
