package com.github.rmannibucau.boon.jaxrs;

import java.nio.charset.Charset;

import org.boon.core.reflection.fields.FieldAccessMode;
import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializerFactory;

public class Boons {
    private Boons() {
        // no-op
    }

    public static JsonParserFactory createJsonParserFactory(final String charset, final boolean lax, final boolean chop, final boolean lazyChop,
            final String fieldAccessType, final boolean useAnnotations) {
        final JsonParserFactory jsonParserFactory = new JsonParserFactory();
        jsonParserFactory.setCharset(Charset.forName(charset));
        jsonParserFactory.setLazyChop(lazyChop);
        jsonParserFactory.setChop(chop);
        jsonParserFactory.setUseAnnotations(useAnnotations);
        if (lax) {
            jsonParserFactory.lax();
        }
        if (FieldAccessMode.FIELD.name().equals(fieldAccessType)) {
            jsonParserFactory.useFieldsOnly();
        } else if (FieldAccessMode.PROPERTY.name().equals(fieldAccessType)) {
            jsonParserFactory.usePropertyOnly();
        } else if (FieldAccessMode.FIELD_THEN_PROPERTY.name().equals(fieldAccessType)) {
            jsonParserFactory.isUseFieldsFirst();
        } else if (FieldAccessMode.PROPERTY_THEN_FIELD.name().equals(fieldAccessType)) {
            jsonParserFactory.usePropertyOnly();
        }
        return jsonParserFactory;
    }

    public static JsonSerializerFactory createJsonSerializerFactory(final boolean outputType, final String fieldAccessType,
            final boolean includeNulls, final boolean useAnnotations, final boolean includeEmpty, final boolean jsonFormatForDates,
            final boolean handleComplexBackReference, final boolean handleSimpleBackReference, final boolean includeDefault,
            final boolean cacheInstances, final String view) {
        final JsonSerializerFactory jsonSerializerFactory = new JsonSerializerFactory();
        if (FieldAccessMode.FIELD.name().equals(fieldAccessType)) {
            jsonSerializerFactory.useFieldsOnly();
        } else if (FieldAccessMode.PROPERTY.name().equals(fieldAccessType)) {
            jsonSerializerFactory.usePropertyOnly();
        } else if (FieldAccessMode.FIELD_THEN_PROPERTY.name().equals(fieldAccessType)) {
            jsonSerializerFactory.isUseFieldsFirst();
        } else if (FieldAccessMode.PROPERTY_THEN_FIELD.name().equals(fieldAccessType)) {
            jsonSerializerFactory.usePropertyOnly();
        }
        jsonSerializerFactory.setOutputType(outputType);
        jsonSerializerFactory.setIncludeNulls(includeNulls);
        jsonSerializerFactory.setUseAnnotations(useAnnotations);
        jsonSerializerFactory.setIncludeEmpty(includeEmpty);
        jsonSerializerFactory.setJsonFormatForDates(jsonFormatForDates);
        jsonSerializerFactory.setHandleComplexBackReference(handleComplexBackReference);
        jsonSerializerFactory.setHandleSimpleBackReference(handleSimpleBackReference);
        jsonSerializerFactory.setIncludeDefault(includeDefault);
        jsonSerializerFactory.setCacheInstances(cacheInstances);
        jsonSerializerFactory.setView(view);
        return jsonSerializerFactory;
    }
}
