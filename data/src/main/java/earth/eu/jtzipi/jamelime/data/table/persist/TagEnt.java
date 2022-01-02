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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Tag for Media.
 * <p>
 * Tags are like categories label for all media types. Instead of a category a media file can have more than one label.
 *
 * @author jTzipi
 */
@Entity(name = "Tag")
@Table(name = "tag")
public final class TagEnt {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * tag label. This must be unique.
     */
    @NaturalId
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * Tag description. Optional.
     */
    @Column(name = "description", columnDefinition = "varchar(1000)")
    private String desc;

    /**
     * Decoration. Optional
     */
    @Column(name = "decoration")
    @Embedded
    private DecorationEmb decoration;

    @OneToMany(mappedBy = "tagEnt", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<MediaTagEnt> mediaTagS = new HashSet<>();

    /**
     * Empty.
     */
    public TagEnt() {
        this( "<?>", null, null );
    }

    /**
     * Tag DB.
     *
     * @param textStr    tag text
     * @param descStr    tag description ( optional )

     * @param decoration decoration ( optional )
     */
    public TagEnt( String textStr, String descStr,  DecorationEmb decoration ) {
        this.text = textStr;
        this.desc = descStr;
        this.decoration = decoration;
    }

    /**
     * Set text.
     * @param textStr text ( not null )
     *
     */
    public void setText( final String textStr ) {
        this.text = Objects.requireNonNull( textStr );
    }

    /**
     * Return text.
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Return Description of tag.
     * @return description
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Set description.
     * @param desc desc
     */
    public void setDesc( final String desc ) {
        this.desc = desc;
    }
    /**
     * Set decoration.
     * @param decoEmb db
     */
    public void setDecoration( final DecorationEmb decoEmb ) {
        this.decoration = decoEmb;
    }

    /**
     * Return decoration row.
     * @return decoration
     */
    public DecorationEmb getDecoration() {
        return this.decoration;
    }

    /**
     * Return id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Return media tag join row.
     * @return media tag row
     */
    public Set<MediaTagEnt> getMediaTag() {

        return mediaTagS;
    }

    @Override
    public boolean equals( Object o ) {

        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        TagEnt tagEnt = ( TagEnt ) o;
        return Objects.equals( text, tagEnt.text );
    }

    @Override
    public int hashCode() {

        return Objects.hash( text );
    }

    @Override
    public String toString() {
        return "TagEnt{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}