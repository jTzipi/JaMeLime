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
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Audio Data table.
 *
 *
 */
@Entity
@Table(name = "media_audio")
public class MediaAudioEnt extends MediaEnt {


    @Embedded
    private MediaAudioVideoEmb mav;

    /**
     * Bidirectional one to many relation. Controlled by AudioSpotEnt.
     */
    @OneToMany(mappedBy = "mediaAudio", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<AudioSpotEnt> mediaSpots = new HashSet<>();

    public MediaAudioEnt() {

    }
    /**
     *
     * @param mediaAudioVideoEmb embedded info
     *
     * @param audioSpotSet audio spot
     */
    public MediaAudioEnt( MediaAudioVideoEmb mediaAudioVideoEmb, final Set<AudioSpotEnt> audioSpotSet ) {
        this.mav = mediaAudioVideoEmb;

        //
        if(null != audioSpotSet) {
            this.mediaSpots.addAll( audioSpotSet );
        }
    }

    /**
     * Add a spot.
     * @param audioSpotEnt spot
     * @throws NullPointerException if {@code audioSpotEnt} is null
     */
    public void addSpot( AudioSpotEnt audioSpotEnt ) {
        Objects.requireNonNull( audioSpotEnt );
        this.mediaSpots.add( audioSpotEnt );
        audioSpotEnt.setMediaAudio( this );
    }

    /**
     * Remove an audio spot.
     * @param audioSpotEnt spot
     * @throws NullPointerException if {@code audioSpotEnt} is null
     */
    public void removeSpot( AudioSpotEnt audioSpotEnt ) {
        Objects.requireNonNull( audioSpotEnt );
        this.mediaSpots.remove( audioSpotEnt );
        audioSpotEnt.setMediaAudio( null );
    }

    /**
     * Return embedded audio property.
     * @return embedded property
     */
    public MediaAudioVideoEmb getMav() {

        return mav;
    }

    /**
     * Return all audio spots.
     * @return audio spots
     */
    public Set<AudioSpotEnt> getMediaSpots() {
        return Collections.unmodifiableSet( mediaSpots );
    }
}