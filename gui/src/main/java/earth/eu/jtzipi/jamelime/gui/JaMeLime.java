package earth.eu.jtzipi.jamelime.gui;

import earth.eu.jtzipi.jamelime.data.DaBa;
import earth.eu.jtzipi.jamelime.data.HibernateManager;
import earth.eu.jtzipi.modules.io.IOUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public final class JaMeLime  extends Application {





    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( "JaMeLime" );


    //  -- keys for properties --
    private static final String KEY_JAMELIME_CREATION_DATE = "jamelime.creation.date";
    private static final String KEY_JAMELIME_START_DATE = "jamelime.start.date";
    private static final String KEY_JAMELIME_DB_CREATION_DATE ="jamelime.db.creation.date";


    @Override
    public void start( Stage stage ) throws Exception {

        double w = Double.parseDouble( Res.getPropertyGUI( Res.KEY_GUI_WIDTH ) );
        double h = Double.parseDouble( Res.getPropertyGUI( Res.KEY_GUI_HEIGHT ) );
        final Scene mainScene = new Scene( new MainFrame(), w, h );

        stage.setScene( mainScene );
        stage.setTitle( "jaMeLime" );
        stage.show();
    }

    @Override
    public void init() {


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String nowStr = dtf.format( now );

        try {

            LOG.info( "~~~ INIT ~~~" );
            Properties prop =  IO.readGlobalProperties();

            // maybe first start of JaMeLime
            if( prop.getProperty( KEY_JAMELIME_CREATION_DATE ).isEmpty()) {
            LOG.info( "First start of JaMeLime :)" );
                prop.setProperty( KEY_JAMELIME_CREATION_DATE, nowStr );

                try {
                    DaBa.createDB();
                } catch (final IOException | SQLException muE ) {
                    Path pwd = IOUtils.getProgramDir().resolve( "error_log_" + nowStr +".log" );
                    try( BufferedWriter bf = Files.newBufferedWriter( pwd ) ) {
                        bf.append( muE.getLocalizedMessage() );
                        bf.newLine();
                        bf.flush();
                    }
                }

                boolean dbCreated = DaBa.isDBCreated();


                LOG.info( "DB created ? " + dbCreated  );
                if( !dbCreated ) {
                    LOG.error( "Error creating DB!" );
                    // TODO: ???
                } else {


                    prop.setProperty( KEY_JAMELIME_DB_CREATION_DATE, nowStr );
                }
            }
            LOG.info( "Init Hibernate" );
            HibernateManager.init();
            prop.setProperty( KEY_JAMELIME_START_DATE, nowStr );
            IO.writeGlobalProperties( prop, "Start" );

            Res.init();

        }catch ( IOException muE ) {

                LOG.error( "Error loading ''", muE );
            }
        }
        }

