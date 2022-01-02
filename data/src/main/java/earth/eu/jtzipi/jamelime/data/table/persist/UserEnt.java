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


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * User Table.
 *
 * @author jTzipi
 */
@Entity
@Table(name = "lime_user")
public final class UserEnt {

    /**
     * Unknown user.s
     */
    public static final String UNKNOWN_USER_NAME= "?";
    /**
     * User pw not set.
     */
    public static final String NO_PW = "!";
    /**
     * User image not set.
     */
    public static final byte[] NO_IMAGE = null;
    /**
     * Minimal pw.
     */
    public static final int USER_MIN_LEN = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Unique user name.
     */
    @Min( value = USER_MIN_LEN )
    @Column(name = "user_name", nullable = false)
    @NaturalId
    private String user;

    /**
     * User password hash.
     */
    @Column(name = "user_pw")
    private String pw;


    @Column(name = "user_image")
    @Lob
    private byte[] userImg;
    /**
     * User without name or pw.
     */
    public UserEnt() {
        this(UNKNOWN_USER_NAME, NO_PW, NO_IMAGE );
    }

    /**
     * User .
     * @param userStr username
     * @param pwStr pw hash
     * @param userImage user image
     */
    public UserEnt( String userStr, String pwStr, byte[] userImage ) {
        this.user = userStr;
        this.pw = pwStr;
        this.userImg = userImage;
    }

    /**
     * Return user id.
     * @return user id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Return user name;
     * @return user name
     */
    public String getUser() {

        return this.user;
    }

    /**
     * Set user name.
     * @param userStr user name
     * @throws NullPointerException if {@code userStr} null
     */
    public void setUser( final String userStr ) {
        this.user = Objects.requireNonNull( userStr );
    }

    /**
     * User pw hash.
     * @return pw
     */
    public String getPW() {
        return this.pw;
    }

    /**
     * Set pw.
     * @param pw pw hash
     */
    public void setPw( String pw ) {

        this.pw = pw;
    }

    /**
     * Set user image.
     * @param image image
     */
    public void setUserImage( final byte[] image ) {
        this.userImg = image;
    }

    /**
     * Return user image data.
     * @return image data
     */
    public byte[] getUserImage() {
        return this.userImg;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        UserEnt userEnt = ( UserEnt ) o;

        return Objects.equals( user, userEnt.user );
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserEnt{" +
                "id=" + id +
                ", user='" + user + '\'' +
                '}';
    }


}