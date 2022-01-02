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

import java.io.Serializable;
import java.util.List;

/**
 * CRUD function if.
 * @param <T> db entity
 * @param <ID> id
 *
 * @author jTzipi
 */
public interface ICrudDao <T, ID extends Serializable> {

    /**
     * Create or 'persist' an entity into db.
     * @param db db
     * @return id
     */
    Serializable create( T db );

    /**
     * Read a entity from db.
     * @param c db type
     * @param id id
     * @return db entity
     * @throws NullPointerException if {@code  id}
     */
    T read( Class<T> c, ID id );

    /**
     * Update a db entity.
     * @param db db
     */
    void update( T db );

    /**
     * Delete a db entity.
     *
     * @param id id

     */
    void delete( T id );

    /**
     * Read all rows of db entity.
     * @return rows
     */
    List<T> readAll();
}
