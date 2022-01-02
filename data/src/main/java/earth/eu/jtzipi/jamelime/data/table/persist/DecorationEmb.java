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

import earth.eu.jtzipi.jamelime.data.table.converter.ColorConverter;
import javafx.scene.paint.Color;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Embeddable entity for decoration.
 * <p>
 * We can have
 *     <ul>
 *         <li>a svg path.</li>
 *         <li>a color</li>
 *         <li>and an image</li>
 *     </ul>
 *
 * </p>
 * @author jTzipi
 */
@Embeddable
public final class DecorationEmb {

    @Column
    @Convert(converter = ColorConverter.class)
    private javafx.scene.paint.Color color;

    @Column(name = "svg_path")
    private String shape;

    @Lob
    @Column(name = "image")
    private byte[] image;

    /**
     * Default C.
     */
    public DecorationEmb() {
        this(null, null, null);
    }

    /**
     * Decoration Embedded.
     * @param color color
     * @param path svg path
     * @param image image
     */
    DecorationEmb( final javafx.scene.paint.Color color, final String path, final byte[] image ) {
        this.color = color;
        this.shape = path;
        this.image = image;
    }

    /**
     * Set color.
     * @param color color
     */
    public void setColor( Color color ) {

        this.color = color;
    }

    /**
     * Set Image data.
     * @param image image
     */
    public void setImage( byte[] image ) {

        this.image = image;
    }

    /**
     * Set shape path.
     * This is Svg valid shape path like <a href="https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorial/Paths">this</a>
     * @param shape svg shape path
     */
    public void setShape( String shape ) {

        this.shape = shape;
    }

    /**
     * Return image byte data.
     * @return byte data
     */
    public byte[] getImage() {

        return image;
    }

    /**
     * Return color.
     * @return color
     */
    public Color getColor() {

        return color;
    }

    /**
     * Return svg shape.
     * @return shape
     */
    public String getShape() {

        return shape;
    }

    @Override
    public String toString() {

        return "Decoration{" +
                "color='" + color +
                "', svgPath='" + shape +
                "', image set=" +(null != image) + "}";
    }
}