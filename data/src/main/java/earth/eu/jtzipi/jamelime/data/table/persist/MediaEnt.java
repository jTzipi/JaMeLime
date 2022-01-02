package earth.eu.jtzipi.jamelime.data.table.persist;/*
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

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Base class for data base media data.
 * TODO: many to many is not efficient if unidirectional. better
 * use a mediatagent table and a mediatagid embeddable id .
 * <p>
 *
 * This table is made for inheritance.
 * We use joined strategy because we want to set not null constraints later. Which is not
 * possible with SINGLE_TABLE approach.
 *
 * @author jTzipi
 */
@Entity(name = "Media")
@Table(name = "media")
@Inheritance(strategy = InheritanceType.JOINED)
public class MediaEnt {

    public static final int NOT_RATED = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Each media file may have a category.
     * This is a uni-directional relation.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CatEnt cat;

    @OneToMany( mappedBy = "mediaEnt", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<MediaTagEnt> tagS = new HashSet<>();

    /**
     * Each media file has an archivation date.
     */
    @Column(name = "archive_date", nullable = false, updatable = false)
    private final LocalDateTime archiveLDT;

    /**
     * Each media file has a file name.
     * TODO: file name with or without ending
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    private final String name;
    /**
     * Each media file has an optional description.
     */
    @Column
    private String desc;

    /**
     * Each media file with a rating model can have a rating &ge; 0.
     * A value of -1 indicate a not rated file.
     */
    @Column
    @Min(NOT_RATED)
    private int rating;

    /**
     * Media.
     * @param nameStr media name
     * @param descStr media description
     * @param catEnt media category
     * @param rating media rating
     * @param archiveLDT media date of archive
     * @param tagSet media tags
     */
    MediaEnt( final String nameStr, final String descStr, final CatEnt catEnt, final int rating, final LocalDateTime archiveLDT, final Set<MediaTagEnt> tagSet ) {
        this.name = nameStr;
        this.desc = descStr;
        this.cat = catEnt;
        this.rating = rating;
        this.archiveLDT = archiveLDT;
        if( null != tagSet ) {
            this.tagS.addAll( tagSet );
        }
    }

    /**
     * Default Constructor.
     */
    MediaEnt() {
        this( "<Not Set>", null, null, 0, null, null );
    }

    /**
     * Return ID.
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Return date of archive.
     * this is a final method since we must not modify archiveLDT.
     * @return archive date
     */
    public  LocalDateTime getArchiveLDT() {
        return archiveLDT;
    }

    /**
     * Set category.
     * @param cat category
     */
    public void setCat( CatEnt cat ) {

        this.cat = cat;
    }

    /**
     * Return category this media is part.
     * @return category
     */
    public CatEnt getCat() {

        return cat;
    }

    /**
     * Return description.
     * @return description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Set description.
     * @param descStr description
     */
    public void setDesc( final String descStr ) {
        this.desc = descStr;

    }

    /**
     * Return rating for this file.
     * @return rating
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Set rating for this media.
     * @param rating rating &ge; 0
     */
    public void setRating(   int rating ) {
        if( rating < 0 ) {
            rating = NOT_RATED;
        }
        
        this.rating = rating;
    }


    /**
     * Return media name.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Add a tag to this media.
     *
     * @param tag tag
     * @throws NullPointerException if {@code tag} is null
     */
    public void addTag( final TagEnt tag ) {
        Objects.requireNonNull( tag );
        MediaTagEnt mediaTagEnt = new MediaTagEnt( this, tag );
        tag.getMediaTag().add( mediaTagEnt );
        this.tagS.add( mediaTagEnt );

    }

    /**
     * Remove tag.
     * @param tagDB tag
     * @throws NullPointerException if {@code tag} is null
     */
    public void removeTag( TagEnt tagDB ) {
        Objects.requireNonNull( tagDB );

        for( Iterator<MediaTagEnt> mti = tagS.iterator(); mti.hasNext();){
            MediaTagEnt row = mti.next();
            MediaEnt media = row.getMediaEnt();
            TagEnt tag = row.getTagEnt();
            if( media.equals( this ) && tag.equals( tagDB ) ) {
                mti.remove();
                tag.getMediaTag().remove( row );
                row.setMediaEnt( null );
                row.setTagEnt( null );break;
            }
        }


    }

    /**
     * Return tags for this media.
     * @return tags
     */
    public Set<MediaTagEnt> getTags() {
        return Collections.unmodifiableSet( this.tagS );
    }

    /**
     * Base .
     * @return base toString
     */
    String toStringBase() {
        return "id='" + id
                +"', cat='" + cat
                +"', name='" + name
                +"', desc='" + desc
                +"', date of archive='" + archiveLDT
                +"', rating=" +rating
                +", tags=[" + tagS + "]";
    }

    @Override
    public boolean equals( Object o ) {
        if( !( o instanceof MediaEnt other ) ) { return false; }
        if( this == o ) {
            return true;
        }

        return other.getName().equals( this.getName() );
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 53 * result + archiveLDT.hashCode();
        return result;
    }
}