package com.manish0890.skyline.service.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Document
public abstract class BaseDocument {
    @Id
    private String id;

    private Date createdDate;

    private Date updatedDate;

    // Fields to exclude during excluded fields Equals method
    private static final List<String> defaultExcludedFields = Arrays.asList("id", "createdDate", "updatedDate");

    /**
     * Perform an exact field for field value match between this object and the provided object.
     *
     * @param object {@link Object}
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof BaseDocument && (this == object || EqualsBuilder.reflectionEquals(this, object, defaultExcludedFields));
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Gives a JSON String representation of Object.
     *
     * @return {@link String} in JSON format.
     */
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
