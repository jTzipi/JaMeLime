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

import earth.eu.jtzipi.jamelime.data.table.converter.ColorConverter;
import earth.eu.jtzipi.jamelime.data.table.converter.LocaleConverter;
import earth.eu.jtzipi.jamelime.data.table.persist.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public enum HibernateManager {

    /**
     * Single.
     */
    SINGLE;

    private static  SessionFactory SEF;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( "HibernateMan" );

    // -- DBCP2 hibernate --
    private static final String DBCP2_MIN_IDLE = "hibernate.dbcp.minIdle";
    HibernateManager() {

    }

    /**
     * Init hibernate.
     * @throws IOException i/o
     */
    public static void init() throws IOException {

        if( !DaBa.isDBCreated() ) {
            throw new IllegalStateException("DB is not created!");
        }

        final Properties hibeProp = new Properties();
        hibeProp.setProperty( Environment.DIALECT, "org.hibernate.dialect.H2Dialect" );
        hibeProp.setProperty( Environment.PASS, DaBa.DB_PW );
        hibeProp.setProperty( Environment.USER, DaBa.DB_ADMIN_USER );
        hibeProp.setProperty( Environment.URL, DaBa.DB_URL );
        hibeProp.setProperty( Environment.JDBC_TIME_ZONE, "UTC" );
        hibeProp.setProperty( Environment.POOL_SIZE, "1" );
        hibeProp.setProperty( Environment.DRIVER, "org.h2.Driver" );
        hibeProp.setProperty( Environment.HBM2DDL_AUTO, "create-drop" );
        hibeProp.setProperty( Environment.SHOW_SQL, "true" );
        hibeProp.setProperty( Environment.C3P0_MIN_SIZE, "9" );
        hibeProp.setProperty( Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread" );

        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

        serviceRegistryBuilder.applySettings( hibeProp );

        StandardServiceRegistry sr = serviceRegistryBuilder.build();

        // add table
        MetadataSources ms = new MetadataSources( sr );
        ms.addAnnotatedClass( DecorationEmb.class );
        ms.addAnnotatedClass( CatEnt.class );
        ms.addAnnotatedClass( TagEnt.class );
        ms.addAnnotatedClass( MediaEnt.class );
        ms.addAnnotatedClass( MediaTagEnt.class );
        ms.addAnnotatedClass( MediaTagIdEmb.class );
        ms.addAnnotatedClass( MediaAudioVideoEmb.class );
        ms.addAnnotatedClass( MediaImageEnt.class );
        ms.addAnnotatedClass( MediaAudioEnt.class );
        ms.addAnnotatedClass( MediaVideoEnt.class );
        ms.addAnnotatedClass( VideoSpotEnt.class );
        ms.addAnnotatedClass( AudioSpotEnt.class );
        ms.addAnnotatedClass( UserEnt.class );

        // set converter
        MetadataBuilder mb = ms.getMetadataBuilder();
        mb.applyAttributeConverter(ColorConverter.class);
        mb.applyAttributeConverter(LocaleConverter.class);


        Metadata md = mb.build();

        SEF = md.buildSessionFactory();
    }

    /**
     * Return a new session or get current.
     * @return session
     * @throws IllegalStateException if session factory is null
     */
    public static Session getSession() {
        if( null == SEF ) {
            throw new IllegalStateException("SessionFactory is null");
        }
        return SEF.isOpen() ? SEF.getCurrentSession() : SEF.openSession();
    }

    /**
     * Close session.
     */
    public static void closeSession() {
        if( null == SEF ) {

            LOG.warn( "Close connection attempt of closed!" );
            return;
        }
        SEF.close();
    }
}