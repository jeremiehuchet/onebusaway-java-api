package fr.dudie.onebusaway.model;

/*
 * [license]
 * ItineRennes Java API client
 * ----
 * Copyright (C) 2010 - 2013 Dudie
 * ----
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * [/license]
 */

/**
 * Bean representing a bus station.
 * 
 * @author Olivier Boudet
 */
public class BusStation {

    /** The identifier of the station. */
    private String id;

    /** The name of the station. */
    private String name;

    /** The latitude of the station. */
    private int latitude;

    /** The longitude of the station. */
    private int longitude;

    /**
     * Gets the identifier of the station.
     * 
     * @return the identifier of the station
     */
    public final String getId() {

        return id;
    }

    /**
     * Sets the identifier of the station.
     * 
     * @param id
     *            the identifier of the station to set
     */
    public final void setId(final String id) {

        this.id = id;
    }

    /**
     * Gets the name of the station.
     * 
     * @return the name of the station
     */
    public final String getName() {

        return name;
    }

    /**
     * Sets the name of the station.
     * 
     * @param name
     *            the name of the station to set
     */
    public final void setName(final String name) {

        this.name = name;
    }

    /**
     * Gets the latitude of the station.
     * 
     * @return the latitude of the station
     */
    public final int getLatitude() {

        return latitude;
    }

    /**
     * Sets the latitude of the station.
     * 
     * @param latitude
     *            the latitude of the station to set
     */
    public final void setLatitude(final int latitude) {

        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the station.
     * 
     * @return the longitude of the station
     */
    public final int getLongitude() {

        return longitude;
    }

    /**
     * Sets the longitude of the station.
     * 
     * @param longitude
     *            the longitude of the station to set
     */
    public final void setLongitude(final int longitude) {

        this.longitude = longitude;
    }

    /**
     * (non-javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append("BusStation [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", latitude=");
        builder.append(latitude);
        builder.append(", longitude=");
        builder.append(longitude);
        builder.append("]");
        return builder.toString();
    }

}
