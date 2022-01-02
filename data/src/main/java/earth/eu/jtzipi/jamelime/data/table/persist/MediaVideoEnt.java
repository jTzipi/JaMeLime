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

import earth.eu.jtzipi.jamelime.data.table.converter.LocaleConverter;



import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.*;


/**
 * Media video.
 * We store mainly meta data of video data.
 * That is
 * <ul>
 *     <li>width</li>
 *     <li>height</li>
 *     <li>language</li>
 *     <li>sub title</li>
 *     <li>sub title language</li>
 *     <li>everything from #MediaAudioVideoEmb</li>
 *
 * </ul>
 * @author jTzipi
 */
@Entity
@Table(name = "media_video")
public final class MediaVideoEnt extends MediaEnt {



    /**
     * Width of video.
     */
    @Min( 1 )
    @Column
    private int width;

    /**
     * Height of video .
     */
    @Min(1)
    @Column
    private int height;

    /**
     * language .
     */
    @Column
    @Convert(converter = LocaleConverter.class)
    private Locale lang;

    /**
     * Subtitle language or null.
     */
    @Column
    @Convert(converter = LocaleConverter.class)
    private Locale subLang;

    /**
     * Shared property.
     */
    @Embedded
    private MediaAudioVideoEmb mav;

    /**
     * One to many bidirectional relation owned by VideoSpotEnt.
     */
    @OneToMany(mappedBy = "mediaVideo", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<VideoSpotEnt> spotS = new HashSet<>();

    /**
     * MediaVideoEnt.
     * @param width width
     * @param height height
     * @param locale video main language
     * @param subLangLocale video sub title language
     * @param spotList video spot list
     * @param mav embedded video info
     */
    public MediaVideoEnt( final int width, final int height, final Locale locale, final Locale subLangLocale, List<VideoSpotEnt> spotList, MediaAudioVideoEmb mav ) {
        this.width = width;
        this.height = height;
        this.lang = locale;
        this.subLang = subLangLocale;
        this.mav = mav;
        if( null != spotList ) {
            spotS.addAll( spotList );
        }
    }

    public MediaVideoEnt() {

    }

    public Set<VideoSpotEnt> getVideoSpots()  {
        return Collections.unmodifiableSet(spotS);
    }

    /**
     * Add a spot.
     * @param vse video spot
     * @throws NullPointerException if {@code vse} is null
     */
    public void addSpot( final VideoSpotEnt vse ) {
        Objects.requireNonNull(vse);

        spotS.add( vse );
        vse.setMediaVideo( this );
    }

    /**
     * Remove spot.
     * @param videoSpotEnt video spot
     * @throws NullPointerException if {@code videoSpotEnt}
     */
    public void removeSpot( final VideoSpotEnt videoSpotEnt ) {
        Objects.requireNonNull( videoSpotEnt );
        spotS.remove( videoSpotEnt );
        videoSpotEnt.setMediaVideo( null );
    }

    /**
     * Return main language.
     * @return main language
     */
    public Locale getLang() {
        return lang;
    }

    /**
     * Return sub languages.
     * @return sub language
     */
    public Locale getSubLang() {
        return subLang;
    }

    /**
     * Return embedded video properties.
     * @return video properties
     */
    public MediaAudioVideoEmb getMediaVideoAudioEmbedded() {
        return mav;
    }

    /**
     * Return height of video.
     * @return height
     */
    public int getHeight() {

        return height;
    }

    /**
     * Return width of video.
     * @return width
     */
    public int getWidth(){
        return width;
    }

    @Override
    public String toString() {
        return "MediaVideoEnt {"
                + toStringBase()
                + ", width=" + width
                + ", height=" + height
                + ", main language='" + lang
                + ", sub language='" + subLang
                +  mav
                + ", video spots=[" + spotS
                + "]}";
    }
}
