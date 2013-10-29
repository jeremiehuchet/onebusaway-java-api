/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.onebusaway.model;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Agency details.
 * 
 * @author Jeremie Huchet
 */
public class Agency {

    /** The agency id. */
    private String id;

    /** The agency name. */
    private String name;

    /** The agency URL. */
    private String url;

    /** The agency timezone. */
    private TimeZone timezone;

    /** The agency phone. */
    private String phone;

    /** The agency language. */
    private Locale lang;

    /**
     * Gets the agency id.
     * 
     * @return the agency id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the agency id.
     * 
     * @param id
     *            the agency id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the agency name.
     * 
     * @return the agency name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the agency name.
     * 
     * @param name
     *            the agency name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the agency URL.
     * 
     * @return the agency URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the agency URL.
     * 
     * @param url
     *            the agency URL to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Gets the agency timezone.
     * 
     * @return the agency timezone
     */
    public TimeZone getTimezone() {
        return timezone;
    }

    /**
     * Sets the agency timezone.
     * 
     * @param timezone
     *            the agency timezone to set
     */
    public void setTimezone(final TimeZone timezone) {
        this.timezone = timezone;
    }

    /**
     * Gets the agency phone.
     * 
     * @return the agency phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the agency phone.
     * 
     * @param phone
     *            the agency phone to set
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Gets the agency language.
     * 
     * @return the agency language
     */
    public Locale getLang() {
        return lang;
    }

    /**
     * Sets the agency language.
     * 
     * @param lang
     *            the agency language to set
     */
    public void setLang(final Locale lang) {
        this.lang = lang;
    }

}
