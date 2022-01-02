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

import javax.persistence.*;
import java.util.Objects;

/**
 * Join Table for {@linkplain MediaEnt} and {@linkplain TagEnt}.
 * <p>
 *     Like described on page 225 ''.
 *     We use a emb
 * </p>
 * @author jTzipi
 */
@Entity(name = "MediaTag")
@Table(name = "media_tag")
public class MediaTagEnt {

    @EmbeddedId
    private MediaTagIdEmb id;

    @ManyToOne
    @MapsId("mediaEntId")
    private MediaEnt mediaEnt;

    @ManyToOne
    @MapsId("tagEntId")
    private TagEnt tagEnt;

    private MediaTagEnt() {

    }

    public MediaTagEnt( final MediaEnt media, final TagEnt tag) {
        this.mediaEnt = media;
        this.tagEnt = tag;
        this.id = new MediaTagIdEmb( media.getId(), tag.getId() );
    }

    public MediaTagIdEmb getId() {
        return id;
    }

    public MediaEnt getMediaEnt() {

        return mediaEnt;
    }

    public void setMediaEnt( MediaEnt mediaEnt ) {

        this.mediaEnt = mediaEnt;
    }

    public TagEnt getTagEnt() {
        return tagEnt;
    }

    public void setTagEnt( TagEnt tagEnt ) {

        this.tagEnt = tagEnt;
    }

    @Override
    public int hashCode() {
        return Objects.hash( mediaEnt , tagEnt );
    }

     @Override public boolean equals(Object other) {
        if( null == other ) {
            return false;
        }
        if( !(other instanceof MediaTagEnt row) ) {
            return false;
        }

        return Objects.equals( row.mediaEnt, this.mediaEnt )
                && Objects.equals( row.tagEnt , this.tagEnt);
     }
}