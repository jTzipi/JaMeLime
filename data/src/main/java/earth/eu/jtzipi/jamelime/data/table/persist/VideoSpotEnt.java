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
 * A labeled point on a video media track.
 *
 * @author jTzipi
 */
@Entity(name = "VideoSpot")
@Table(name = "video_spot")
public final class VideoSpotEnt implements Comparable<VideoSpotEnt> {

    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "spot_milli_sec")
    private long spotMillisecond;

    @Column(name = "spot_label")
    private String label;

    @Column
    @Lob
    private byte[] thumbnail;

    @ManyToOne
    @JoinColumn(name = "media_video_id")
    private MediaVideoEnt mediaVideo;



    public VideoSpotEnt( long spotMillisecond, String label, byte[] thumbnail, MediaVideoEnt mediaVideoEnt ) {
        this.spotMillisecond = spotMillisecond;
        this.label = label;
        this.thumbnail = thumbnail;
        this.mediaVideo = mediaVideoEnt;
    }

    public VideoSpotEnt() {

    }

    /**
     * Set video.
     * @param mediaVideoEnt video row
     *
     */
    public void setMediaVideo( final MediaVideoEnt mediaVideoEnt ){
        this.mediaVideo =mediaVideoEnt;
    }

    /**
     * Return media video.
     * @return media video
     */
    public MediaVideoEnt getMediaVideo() {
        return this.mediaVideo;
    }
    /**
     * Return position.
     *
     * @return position
     */
    public long getSpotMillisecond() {
        return this.spotMillisecond;
    }

    /**
     * Set spot millisecond.
     *
     * @param spotMillisecond millisecond
     * @throws IllegalStateException if
     */
    public void setSpotMillisecond( long spotMillisecond ) {
        if ( spotMillisecond < 0L ) {
            throw new IllegalStateException( "negative milli '" + spotMillisecond + "'" );
        }
        this.spotMillisecond = spotMillisecond;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail( final byte[] thumbnail ) {
        this.thumbnail = thumbnail;
    }

    /**
     * Return label.
     *
     * @return label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Set label.
     *
     * @param label label
     * @throws NullPointerException if {@code label} is null
     */
    public void setLabel( String label ) {
        this.label = Objects.requireNonNull( label );
    }

    /**
     * Return id.
     *
     * @return id
     */
    public long getId() {
        return this.id;
    }

    @Override
    public int compareTo( VideoSpotEnt spot ) {
        return Long.compare( this.spotMillisecond, spot.spotMillisecond );
    }

    @Override
    public int hashCode() {
        int result = ( int ) ( id ^ ( id >>> 32 ) );
        result = 31 * result + ( int ) ( spotMillisecond ^ ( spotMillisecond >>> 32 ) );

        return result;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        VideoSpotEnt spot = ( VideoSpotEnt ) o;

        if ( id != spot.id ) return false;

        return this.spotMillisecond == spot.spotMillisecond;
    }
}
