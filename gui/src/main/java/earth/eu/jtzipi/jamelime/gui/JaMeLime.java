package earth.eu.jtzipi.jamelime.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JaMeLime  extends Application {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( "JaMeLime" );
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

        try {
            Res.init();
        }catch ( IOException ioE ) {

                LOG.error( "Error loading '[gui]/resources/gui.properties'", ioE );
            }
        }
        }

