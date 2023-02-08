package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class encapsulates the functionality of {@link JsonSchema} simple types
 * @author jphelan
 */
public abstract class SimpleTypeSchema extends JsonSchema
{
	/**
	 * This attribute defines the default value of the instance when the
	 * instance is undefined.
	 */
	protected String defaultdefault;

	/**
	 * This attribute is a string that provides a short description of the
	 * instance property.
	 */
	protected String title;

	/**
	 * This attribute is a URI that defines what the instance's URI MUST start with in order to validate.
	 */
	protected String pathStart;

	/**
	 * This attribute is a string that provides a links related to description of the
	 * instance property.
	 */
	protected LinkDescriptionObject[] links;

	@Override
	public SimpleTypeSchema asSimpleTypeSchema() {
		return this;
	}

	public String getDefault() {
		return defaultdefault;
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

	public void setTitle(String title) {
		this.title = title;
	}

    public void setPathStart(String pathStart) {
        this.pathStart = pathStart;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof SimpleTypeSchema)) return false;
        return _equals((SimpleTypeSchema) obj);
    }

    protected boolean _equals(SimpleTypeSchema that)
    {
        return equals(getDefault(), that.getDefault())
                && equals(getTitle(), that.getTitle())
                && equals(getPathStart(), that.getPathStart())
                && arraysEqual(getLinks(), that.getLinks())
                && super._equals(that);
    }
}
