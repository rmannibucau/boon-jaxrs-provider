package com.github.rmannibucau.boon.jaxrs;

import org.boon.IO;
import org.boon.json.JsonSerializerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

@Provider
@Produces(MediaType.WILDCARD)
public class BoonJsonMessageBodyWriter<T> implements MessageBodyWriter<T> {
    private final AtomicReference<JsonSerializerFactory> factory = new AtomicReference<JsonSerializerFactory>();

    private boolean outputType = false;
    private String fieldAccessType = "FIELD";
    private boolean includeNulls = false;
    private boolean useAnnotations = false;
    private boolean includeEmpty = false;
    private boolean jsonFormatForDates = false;
    private boolean handleSimpleBackReference = true;
    private boolean handleComplexBackReference = false;
    private boolean includeDefault = false;
    private boolean cacheInstances = true;
    private String view = null;

    private JsonSerializerFactory factory() {
        JsonSerializerFactory value = factory.get();
        if (value == null) {
            synchronized (factory) {
                value = factory.get();
                if (value == null) {
                    value = Boons.createJsonSerializerFactory(outputType, fieldAccessType, includeNulls, useAnnotations, includeEmpty, jsonFormatForDates, handleComplexBackReference, handleSimpleBackReference, includeDefault, cacheInstances, view);
                    factory.set(value);
                }
            }
        }
        return value;
    }

    protected void init(final JsonSerializerFactory factory) {
        if (factory != null) {
            this.factory.set(factory);
        } else {
            factory(); // force init
        }
    }

    @Override
    public void writeTo(final T t, final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, Object> stringObjectMultivaluedMap, final OutputStream outputStream) throws IOException {
        IO.writeNoClose(outputStream, factory().create().serialize(t).toString());
    }

    @Override
    public long getSize(final T t, final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return Jsons.isJson(mediaType)
                && InputStream.class != aClass
                && OutputStream.class != aClass
                && Writer.class != aClass
                && StreamingOutput.class != aClass
                && Response.class != aClass;
    }

    public void setOutputType(final boolean outputType) {
        this.outputType = outputType;
    }

    public void setFieldAccessType(final String fieldAccessType) {
        this.fieldAccessType = fieldAccessType;
    }

    public void setIncludeNulls(final boolean includeNulls) {
        this.includeNulls = includeNulls;
    }

    public void setUseAnnotations(final boolean useAnnotations) {
        this.useAnnotations = useAnnotations;
    }

    public void setIncludeEmpty(final boolean includeEmpty) {
        this.includeEmpty = includeEmpty;
    }

    public void setJsonFormatForDates(final boolean jsonFormatForDates) {
        this.jsonFormatForDates = jsonFormatForDates;
    }

    public void setHandleSimpleBackReference(final boolean handleSimpleBackReference) {
        this.handleSimpleBackReference = handleSimpleBackReference;
    }

    public void setHandleComplexBackReference(final boolean handleComplexBackReference) {
        this.handleComplexBackReference = handleComplexBackReference;
    }

    public void setIncludeDefault(final boolean includeDefault) {
        this.includeDefault = includeDefault;
    }

    public void setCacheInstances(final boolean cacheInstances) {
        this.cacheInstances = cacheInstances;
    }

    public void setView(final String view) {
        this.view = view;
    }
}
