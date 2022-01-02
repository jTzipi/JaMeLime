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

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * MediaTag Id Table.
 * This is the embedded id for the join table {@linkplain MediaTagEnt} between
 * {@linkplain MediaEnt} and {@linkplain TagEnt}
 */
@Embeddable
public class MediaTagIdEmb implements Serializable {

    // MediaAudioEnt Id
    private Long mediaEntId;
    // TagEnt Id
    private Long tagEntId;

    public MediaTagIdEmb() {

    }


    public MediaTagIdEmb( final Long mediaEntId, final Long tagEntId ) {
        this.mediaEntId = mediaEntId;
        this.tagEntId = tagEntId;
    }

    /**
     * Return media id.
     * @return media id
     */
    public Long getMediaEntId() {

        return mediaEntId;
    }

    /**
     * Return tag id.
     * @return tag id
     */
    public Long getTagEntId() {

        return tagEntId;
    }

    @Override
    public int hashCode() {
        return Objects.hash( mediaEntId, tagEntId );
    }

    @Override
    public boolean equals( Object other ) {
        if( null == other) {
            return false;
        }
        if( !( other instanceof MediaTagIdEmb db )) {
            return false;
        }

        return Objects.equals( db.mediaEntId , this.mediaEntId ) &&
                Objects.equals( db.tagEntId, this.tagEntId );
    }

    @Override
    public String toString() {
        return "MediaAudioTagIdEmb{ media_audio_id=" + mediaEntId + ", tag_id=" + tagEntId +"}";
    }
}