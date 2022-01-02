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

package earth.eu.jtzipi.jamelime.data.table.dao;

import earth.eu.jtzipi.jamelime.data.HibernateManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Abstract DAO.
 * @param <T> db entity
 * @param <ID> id
 *
 * @author jTzipi
 */
public abstract class AbstractCrudDao<T, ID extends Serializable> implements ICrudDao<T, ID>{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( "AbstractCrudDao" );

    AbstractCrudDao() {

    }

    @Override
    public Serializable create( T db ) {
        Objects.requireNonNull(db);
        return doCreate( db );
    }

    @Override
    public T read( Class<T> c, ID id ) {
        Objects.requireNonNull( id );
        return doRead( c, id );
    }

    @Override
    public void update( T db ) {

        Objects.requireNonNull( db );
        doUpdate( db );

    }

    @Override
    public void delete( T id ) {
        Objects.requireNonNull( id );
        doDelete(id);
    }

    static <T> Serializable  doCreate( final T db ) {

        return HibernateManager.getSession().save( db );
    }

    static <T, ID extends Serializable> T doRead( Class<T> cls, ID id ) {

        return HibernateManager.getSession().load( cls, id );
    }

    static  <T> void doUpdate( T db ) {
        Transaction tran = null;

        try ( Session se = HibernateManager.getSession() ) {

            tran = se.beginTransaction();

            se.update( db );

            tran.commit();
        } catch ( PersistenceException persistE ) {

            rollback( tran );
            throw persistE;
        }
    }

    static <T> void doDelete( final T db ){
        Transaction tran = null;

        try( Session se = HibernateManager.getSession()  ) {
            tran=se.beginTransaction();
            se.delete( db );
            tran.commit();
        } catch ( PersistenceException persistE ) {

            rollback( tran );
            throw persistE;
        }
    }

    /**
     * Read all rows from db.
     * @param c db cls
     * @param dbname db name
     * @param <T> db type
     * @return list of rows
     * @throws PersistenceException if something happened
     * @throws NullPointerException if {@code c}|{@code dbname}
     */
    static <T> List<T> doReadRows( Class<T> c, String dbname ) {
        Objects.requireNonNull(c);
        Objects.requireNonNull( dbname );

        try( Session se = HibernateManager.getSession() ) {

            org.hibernate.query.Query<T> query = se.createQuery( "FROM " + dbname, c );

            return query.list();
        }
    }

    private static void rollback(Transaction tran) {

        if( null != tran && tran.isActive() ) {
            try {
                tran.rollback();
            }catch ( PersistenceException persistE ) {
LOG.warn( "Can not rollback!", persistE );
            }
        }
    }
}
