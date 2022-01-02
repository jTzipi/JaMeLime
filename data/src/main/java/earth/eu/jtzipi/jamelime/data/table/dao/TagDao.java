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
import earth.eu.jtzipi.jamelime.data.table.persist.DecorationEmb;
import earth.eu.jtzipi.jamelime.data.table.persist.TagEnt;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class TagDao extends AbstractCrudDao<TagEnt, Long> {

    private static final String DB_NAME = "tag";

    public TagDao() {

    }

    public void persistTag( final String text, final String desc, final DecorationEmb deco ) {

        TagEnt tagEnt = new TagEnt(text, desc, deco);
        Serializable id =  create( tagEnt );

    }

    public void updateTag( final TagEnt first, final TagEnt... other  ) {
        update( first );
        if( null != other ) {
            for( TagEnt db : other ) {
                update( db );
            }
        }
    }

    public List<TagEnt> lookLikeTag( final String text ) {

        try( Session se = HibernateManager.getSession() ) {

            org.hibernate.query.Query<TagEnt> q = se.createQuery( "FROM " + DB_NAME +" WHERE text like " + text, TagEnt.class );
            return q.list();
        }
    }

    @Override
    public List<TagEnt> readAll() {

        return doReadRows( TagEnt.class, DB_NAME );
    }
}
