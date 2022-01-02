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

package earth.eu.jtzipi.jamelime.data.table.persist;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default Category.
 * <p>
 * This DB table contain information about a media category.
 * <br/>
 * <br/>
 * Each Cat have a
 * <ul>
 *     <li>Parent</li>
 *     <li>Zero or more sub categories</li>
 *     <li>Short text</li>
 *     <li>Detailed text</li>
 *     <li>Optional Decoration</li>
 * </ul>
 *
 * @author jTzipi
 */
@Entity(name = "Category")
@Table(name = "category")
public class CatEnt {

    /**
     * Root ID.
     */
    public static final Long ROOT_ID = 0L;
    public static final String SQL_INSERT_ROOT = "INSERT INTO category( parent_id, color, image, shape, desc, text, sub_cat ) VALUES( 0, '[0,0,0,0]', null, '', 'Root Category', 'Root', null)";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Parent cat. in case of root this is null.
     */
    @ManyToOne
    private CatEnt parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CatEnt> subCatL;

    @NaturalId
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "desc")
    private String desc;

    @Embedded
    private DecorationEmb decoration;

    /**
     * Default C.
     */
    public CatEnt() {
        this( null, null, "?", "!", null );
    }

    /**
     * @param parent     parent
     * @param subCatL    sub cat list
     * @param text       text
     * @param desc       desc
     * @param decoration deco
     */
    public CatEnt( CatEnt parent, List<CatEnt> subCatL, String text, String desc, DecorationEmb decoration ) {
        this.parent = parent;
        this.subCatL = null == subCatL ? new ArrayList<>() : subCatL;
        this.text = text;
        this.desc = desc;
        this.decoration = decoration;
    }

    /**
     * Return parent category or null if root.
     *
     * @return parent category
     */
    public CatEnt getParent() {
        return this.parent;
    }

    /**
     * Return all sub categories.
     *
     * @return sub categories or empty list
     */
    public List<CatEnt> getSubCatList() {
        return subCatL;
    }

    /**
     * Return text of cat.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Return description.
     *
     * @return description
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Return id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Return Decoration table.
     * @return decoration
     */
    public DecorationEmb getDecoration() {
        return decoration;
    }

    /**
     * Set category text.
     * @param text text
     * @throws NullPointerException if
     */
    public void setText( final String text ) {
        Objects.requireNonNull( text );
        this.text = text;
    }

    /**
     * Set a detailed description.
     * @param text desc
     */
    public void setDesc( String text ) {
        this.desc = text;
    }

    /**
     * Set decoration.
     * @param decoEnt decoration (optional)
     */
    public void setDecoration( final DecorationEmb decoEnt ) {
        this.decoration = decoEnt;
    }

    /**
     * Set parent category.
     * <p>
     * We did not allow null because we did not know if a client
     * set null with purpose or without.
     * Internally we use {@code null}
     * to store root.
     *
     * @param parentCat new parent if root cat is needed use {@linkplain #ROOT_ID}
     */
    public void setParent( CatEnt parentCat ) {
        Objects.requireNonNull( parentCat );
        this.parent = isRoot( parentCat ) ? null : parentCat;
    }

    /**
     * Add a sub cat to this.
     *
     * @param subCat sub category
     * @throws NullPointerException     if {@code subCat} is null
     * @throws IllegalArgumentException if {@code subCat} is root
     */
    public void addSubCat( CatEnt subCat ) {
        Objects.requireNonNull( subCat );

        if ( isRoot( subCat ) ) {
            throw new IllegalArgumentException( "Root cat can not be sub cat" );
        }
// todo: we should check for error
        // that is already used name and wrong parent
        this.getSubCatList().add( subCat );
    }

    /**
     * Remove a cat.
     *
     * @param subCat cat to remove
     * @throws NullPointerException     if {@code subCat} is null
     * @throws IllegalArgumentException if {@code subCat} is root or is not stored
     */
    public void removeSubCat( CatEnt subCat ) {
        Objects.requireNonNull( subCat );
        if ( isRoot( subCat ) ) {
            throw new IllegalArgumentException( "Root cat can not be sub cat" );
        }
        if ( !getSubCatList().contains( subCat ) ) {

            throw new IllegalArgumentException( "This category have no sub cat '" );
        }

        getSubCatList().remove( subCat );
    }

    private static boolean isRoot( CatEnt cat ) {
        return cat.getId().equals( ROOT_ID );
    }

    @Override
    public String toString() {
        return "CatEnt{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        CatEnt catEnt = ( CatEnt ) o;
// @reason 'High performance persistence Java' p202
        return text.equals( catEnt.text );
    }

    @Override
    public int hashCode() {


        return Objects.hashCode( text );
    }
}