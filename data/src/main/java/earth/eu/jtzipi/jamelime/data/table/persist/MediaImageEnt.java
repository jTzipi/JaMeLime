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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "media_image")
public class MediaImageEnt extends MediaEnt{
    /**
     * Image type.
     * e.G. JPEG
     */
    @Column
    private String type;

    @Min(value = 1)
    @Column(nullable = false)
    private int width;
    @Min(value = 1)
    @Column(nullable = false)
    private int height;

    /**
     * Public MediaImageEnt.
     */
    public MediaImageEnt() {
        this( "<?>", 1, 1);
    }

    /**
     * MediaImageEnt C.
     * @param type image type i.E. jpg or png and so
     * @param width image width
     * @param height image height
     */
    public MediaImageEnt( final String type, final int width, final int height ) {
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public String getType() {

        return type;
    }

    public void setType( String type ) {

        this.type = type;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth( int width ) {
        if( width <= 0 ) {
            throw new IllegalArgumentException("Image width[="+width+"]<=0");
        }
        this.width = width;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight( int height ) {
        if( height <= 0 ) {
            throw new IllegalArgumentException("Image width[="+height+"]<=0");
        }
        this.height = height;
    }
}

