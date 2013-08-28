/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ot.pds.utils;

/**
 *
 * @author nchebolu
 */
public class PropertyBean {

    private String comment = Constants.EMPTY_STRING;
    private String name = Constants.EMPTY_STRING;
    private String value = Constants.EMPTY_STRING;

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("line.separator"));
        sb.append(comment);
        sb.append(System.getProperty("line.separator"));
        sb.append(name);
        sb.append("=");
        sb.append(value);
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }
}
