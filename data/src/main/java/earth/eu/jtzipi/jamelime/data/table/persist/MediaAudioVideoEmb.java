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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This is a shared value class for audio and video media.
 * Each audio or video have
 * <ul>
 *     <li>Codec</li>
 *     <li>Time in seconds</li>
 *     <li>Creation year</li>
 * </ul>
 */
@Embeddable
public class MediaAudioVideoEmb {


    /**
     * Codec .
     */
    @Column(nullable = false)
    private String codec;
    /**
     * Time of media in second .
     */
    @Column(nullable = false)
    private long timeInSecond;
    /**
     * Creation year.
     */
    @Column(name = "creation_year")
    private int year;

    public MediaAudioVideoEmb() {

    }

    /**
     * Media Audio and Video Embedded.
     * @param codecStr codec
     * @param timeInSecond time in sec
     * @param creationYear year
     */
    public MediaAudioVideoEmb( final String codecStr, final long timeInSecond, final int creationYear ) {
        this.codec = codecStr;
        this.timeInSecond = timeInSecond;
        this.year = creationYear;
    }

    @Override
    public String toString() {

        return "Property{ codec='" + codec +
                "', timeInSecond=" + timeInSecond +
                ", year=" + year + "}";
    }

    /**
     * Return codec.
     * @return codec
     */
    public String getCodec() {

        return codec;
    }

    /**
     * Return year of creation.
     * @return year
     */
    public int getYear() {

        return year;
    }

    /**
     * Return length in seconds.
     * @return seconds
     */
    public long getTimeInSecond() {

        return timeInSecond;
    }

    public void setCodec( String codec ) {

        this.codec = codec;
    }

    public void setYear( int year ) {

        this.year = year;
    }

    public void setTimeInSecond( long timeInSecond ) {

        this.timeInSecond = timeInSecond;
    }
}