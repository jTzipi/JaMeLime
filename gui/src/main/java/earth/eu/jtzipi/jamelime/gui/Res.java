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

package earth.eu.jtzipi.jamelime.gui;

import earth.eu.jtzipi.modules.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public final class Res {

    private static final String PROPERTIES_GUI = "gui.properties";

    public static final String KEY_GUI_WIDTH = "gui.mainframe.width";
    public static final String KEY_GUI_HEIGHT = "gui.mainframe.height";

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( "Res" );
    private static final Properties GUI_PROPERTIES = new Properties();

    private Res() {

    }

    static void init() throws IOException {

        Path guiDir = IOUtils.getProgramDir();

        LOG.info( "try to load path '"+guiDir+"'" );
        LOG.info( "try to load '"+PROPERTIES_GUI+"'" );



        try(  InputStream is = Res.class.getResourceAsStream( PROPERTIES_GUI )  ) {

            if( null == is ) {
                throw new IllegalStateException("File not found or not readable");
            }
            GUI_PROPERTIES.load( is );
            LOG.info( "'"+PROPERTIES_GUI+"' loaded Okay!" );
        }
    }

    public static String getPropertyGUI( final String  propKey) {
    if( !GUI_PROPERTIES.containsKey( propKey )) {
        throw new IllegalArgumentException("Key '"+propKey+"' not found");
    }
        return GUI_PROPERTIES.getProperty( propKey );
    }

}
