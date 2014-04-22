package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class encapsulates the functionality of {@link JsonSchema} simple types
 * @author jphelan
 */
public abstract class SimpleTypeSchema extends JsonSchema {

	/**
	 * This attribute defines the default value of the instance when the
	 * instance is undefined.
	 */
	private String defaultdefault;

	/**
	 * This attribute is a string that provides a full description of the of
	 * purpose the instance property.
	 */
	private String description;

	/**
	 * This attribute is a string that provides a short description of the
	 * instance property.
	 */
	private String title;

    /**
     * This attribute is a URI that defines what the instance's URI MUST start with in order to validate.
     */
    private String pathStart;

    /**
     * This attribute is a string that provides a links related to description of the
     * instance property.
     */
    private LinkDescriptionObject[] links;

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#asSimpleTypeSchema()
	 */
	@Override
	public SimpleTypeSchema asSimpleTypeSchema() {
		return this;
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleTypeSchema) {
			SimpleTypeSchema that = (SimpleTypeSchema)obj;
			return equals(getDefault(), that.getDefault()) &&
				equals(getDescription(), that.getDescription()) &&
				equals(getTitle(), that.getTitle()) &&
				super.equals(obj);
		} else {
			return false;
		}
	} 

	public String getDefault() {
		return defaultdefault;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

    public String getPathStart() {
        return pathStart;
    }

    public LinkDescriptionObject[] getLinks() {
        return links;
    }

    public void setLinks(LinkDescriptionObject[] links) {
        this.links = links;
    }

	@Override
	public boolean isSimpleTypeSchema() {
		return true;
	}

	public void setDefault(String defaultdefault) {
		this.defaultdefault = defaultdefault;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public void setPathStart(String pathStart) {
        this.pathStart = pathStart;
    }
}