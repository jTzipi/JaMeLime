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
import javax.validation.constraints.Min;

@Table(name = "audio_spot")
@Entity
public class AudioSpotEnt {


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String label;

    @Min( 0 )
    @Column(name = "millisecond")
    private long positionInMillis;
    /**
     * Bidirectional One to many relation where this is the child side owning this relation.
     */
    @ManyToOne
    @JoinColumn(name = "media_audio_id")
    private MediaAudioEnt mediaAudio;

    public AudioSpotEnt() {
        this( "", 0L, null );
    }

    public AudioSpotEnt( final String labelStr, final long position, final MediaAudioEnt mediaAudioEnt ) {
        this.label = labelStr;
        this.positionInMillis = position;
        this.mediaAudio = mediaAudioEnt;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel( final String labelStr )
    {
        this.label = labelStr;
    }

    public long getPositionInMillis() {
        return positionInMillis;
    }

    public void setPositionInMillis( final long positionInMillis ) {
        this.positionInMillis = positionInMillis;
    }

    public MediaAudioEnt getMediaAudio() {
        return this.mediaAudio;
    }

    public void setMediaAudio( final MediaAudioEnt mediaAudioEnt ) {
        this.mediaAudio = mediaAudioEnt;
    }




    @Override
    public boolean equals( Object other ) {

        if(this == other) {
            return true;
        } else if( !(other instanceof AudioSpotEnt)) {
            return false;
        }

        AudioSpotEnt ase = (AudioSpotEnt) other;

        return null != mediaAudio && mediaAudio.equals( ase.mediaAudio ) && positionInMillis == ase.positionInMillis;
    }
}
