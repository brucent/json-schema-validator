package eel.kitchen.jsonschema.validators.format;

import org.codehaus.jackson.JsonNode;

public final class ISO8601DateFormatValidator
    extends AbstractDateFormatValidator
{
    public ISO8601DateFormatValidator(final JsonNode ignored)
    {
        super(ignored, "yyyy-MM-dd'T'HH:mm:ssz", "ISO 8601 date-time");
    }
}