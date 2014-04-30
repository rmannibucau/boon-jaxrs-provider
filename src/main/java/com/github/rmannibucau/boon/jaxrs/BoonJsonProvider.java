package com.github.rmannibucau.boon.jaxrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.WILDCARD)
@Consumes(MediaType.WILDCARD)
public class BoonJsonProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {
    private final BoonJsonMessageBodyReader<T> reader = new BoonJsonMessageBodyReader<T>();
    private final BoonJsonMessageBodyWriter<T> writer = new BoonJsonMessageBodyWriter<T>();

    @Override
    public void writeTo(final T t, final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> stringObjectMultivaluedMap, final OutputStream outputStream) throws IOException {
        writer.writeTo(t, aClass, type, annotations, mediaType, stringObjectMultivaluedMap, outputStream);
    }

    @Override
    public long getSize(final T t, final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return writer.getSize(t, aClass, type, annotations, mediaType);
    }

    @Override
    public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return writer.isWriteable(aClass, type, annotations, mediaType);
    }

    @Override
    public T readFrom(final Class<T> tClass, final Type type, final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, String> stringStringMultivaluedMap, final InputStream inputStream) throws IOException {
        return reader.readFrom(tClass, type, annotations, mediaType, stringStringMultivaluedMap, inputStream);
    }

    @Override
    public boolean isReadable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return reader.isReadable(aClass, type, annotations, mediaType);
    }

    public void setCharset(final String charset) {
        reader.setCharset(charset);
    }

    public void setLax(final boolean lax) {
        reader.setLax(lax);
    }

    public void setChop(final boolean chop) {
        reader.setChop(chop);
    }

    public void setLazyChop(final boolean lazyChop) {
        reader.setLazyChop(lazyChop);
    }

    public void setFieldAccessType(final String fieldAccessType) {
        reader.setFieldAccessType(fieldAccessType);
        writer.setFieldAccessType(fieldAccessType);
    }

    public void setUseAnnotations(final boolean useAnnotations) {
        reader.setUseAnnotations(useAnnotations);
        writer.setUseAnnotations(useAnnotations);
    }

    public void setOutputType(final boolean outputType) {
        writer.setOutputType(outputType);
    }

    public void setIncludeNulls(final boolean includeNulls) {
        writer.setIncludeNulls(includeNulls);
    }

    public void setIncludeEmpty(final boolean includeEmpty) {
        writer.setIncludeEmpty(includeEmpty);
    }

    public void setJsonFormatForDates(final boolean jsonFormatForDates) {
        writer.setJsonFormatForDates(jsonFormatForDates);
    }

    public void setHandleSimpleBackReference(final boolean handleSimpleBackReference) {
        writer.setHandleSimpleBackReference(handleSimpleBackReference);
    }

    public void setHandleComplexBackReference(final boolean handleComplexBackReference) {
        writer.setHandleComplexBackReference(handleComplexBackReference);
    }

    public void setIncludeDefault(final boolean includeDefault) {
        writer.setIncludeDefault(includeDefault);
    }

    public void setCacheInstances(final boolean cacheInstances) {
        writer.setCacheInstances(cacheInstances);
    }

    public void setView(final String view) {
        writer.setView(view);
    }
}
