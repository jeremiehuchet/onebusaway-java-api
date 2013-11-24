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
 * Class representing a OneBusAway route element.
 * 
 * @author Olivier Boudet
 */
public class Route {

    /** Id of the route. */
    private String id;

    /** Text color of the route. */
    private String textColor;

    /** Color of the route. */
    private String color;

    /** Description of the route. */
    private String description;

    /** Long name of the route. */
    private String longName;

    /** Short name of the route. */
    private String shortName;

    /** Type of the route. */
    private int type;

    /** Agency id of the route. */
    private String agencyId;

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
     * Gets the textColor.
     * 
     * @return the textColor
     */
    public String getTextColor() {

        return textColor;
    }

    /**
     * Sets the textColor.
     * 
     * @param textColor
     *            the textColor to set
     */
    public void setTextColor(final String textColor) {

        this.textColor = textColor;
    }

    /**
     * Gets the color.
     * 
     * @return the color
     */
    public String getColor() {

        return color;
    }

    /**
     * Sets the color.
     * 
     * @param color
     *            the color to set
     */
    public void setColor(final String color) {

        this.color = color;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(final String description) {

        this.description = description;
    }

    /**
     * Gets the longName.
     * 
     * @return the longName
     */
    public String getLongName() {

        return longName;
    }

    /**
     * Sets the longName.
     * 
     * @param longName
     *            the longName to set
     */
    public void setLongName(final String longName) {

        this.longName = longName;
    }

    /**
     * Gets the shortName.
     * 
     * @return the shortName
     */
    public String getShortName() {

        return shortName;
    }

    /**
     * Sets the shortName.
     * 
     * @param shortName
     *            the shortName to set
     */
    public void setShortName(final String shortName) {

        this.shortName = shortName;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType() {

        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            the type to set
     */
    public void setType(final int type) {

        this.type = type;
    }

    /**
     * Gets the agencyId.
     * 
     * @return the agencyId
     */
    public String getAgencyId() {

        return agencyId;
    }

    /**
     * Sets the agencyId.
     * 
     * @param agencyId
     *            the agencyId to set
     */
    public void setAgencyId(final String agencyId) {

        this.agencyId = agencyId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((agencyId == null) ? 0 : agencyId.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((longName == null) ? 0 : longName.hashCode());
        result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
        result = prime * result + ((textColor == null) ? 0 : textColor.hashCode());
        result = prime * result + type;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Route other = (Route) obj;
        if (agencyId == null) {
            if (other.agencyId != null)
                return false;
        } else if (!agencyId.equals(other.agencyId))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (longName == null) {
            if (other.longName != null)
                return false;
        } else if (!longName.equals(other.longName))
            return false;
        if (shortName == null) {
            if (other.shortName != null)
                return false;
        } else if (!shortName.equals(other.shortName))
            return false;
        if (textColor == null) {
            if (other.textColor != null)
                return false;
        } else if (!textColor.equals(other.textColor))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    /*
     * (non-javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append("Route [id=");
        builder.append(id);
        builder.append(", textColor=");
        builder.append(textColor);
        builder.append(", color=");
        builder.append(color);
        builder.append(", description=");
        builder.append(description);
        builder.append(", longName=");
        builder.append(longName);
        builder.append(", shortName=");
        builder.append(shortName);
        builder.append(", type=");
        builder.append(type);
        builder.append(", agencyId=");
        builder.append(agencyId);
        builder.append("]");
        return builder.toString();
    }

}
