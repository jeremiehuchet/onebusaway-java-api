package fr.itinerennes.api.client.model;

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
 * Class representing a ItineRennes stop element.
 * 
 * @author Olivier Boudet
 */
public class Stop {

    /** Id of the stop. */
    private String id;

    /** Latitude of the stop. */
    private Double lat;

    /** Longitude of the stop. */
    private Double lon;

    /** Direction of the stop. */
    private String direction;

    /** Name of the stop. */
    private String name;

    /** Code of the stop. */
    private int code;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {

        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(final String id) {

        this.id = id;
    }

    /**
     * Gets the latitude of the stop.
     * 
     * @return the latitude
     */
    public Double getLat() {

        return lat;
    }

    /**
     * Sets the latitude of the stop.
     * 
     * @param latitude
     *            the latitude to set
     */
    public void setLat(final Double lat) {

        this.lat = lat;
    }

    /**
     * Gets the longitude of the stop.
     * 
     * @return the longitude
     */
    public Double getLon() {

        return lon;
    }

    /**
     * Sets the longitude of the stop.
     * 
     * @param lon
     *            the longitude to set
     */
    public void setLon(final Double lon) {

        this.lon = lon;
    }

    /**
     * Gets the direction of the stop.
     * 
     * @return the direction
     */
    public String getDirection() {

        return direction;
    }

    /**
     * Sets the direction.
     * 
     * @param direction
     *            the direction to set
     */
    public void setDirection(final String direction) {

        this.direction = direction;
    }

    /**
     * Gets the name of the stop.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the name of the stop.
     * 
     * @param name
     *            the name to set
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * Gets the code of the stop.
     * 
     * @return the code
     */
    public int getCode() {

        return code;
    }

    /**
     * Sets the code of the stop.
     * 
     * @param code
     *            the code to set
     */
    public void setCode(final int code) {

        this.code = code;
    }

}
