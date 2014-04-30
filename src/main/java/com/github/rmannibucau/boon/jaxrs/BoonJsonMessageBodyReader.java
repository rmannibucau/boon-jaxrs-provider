package com.github.rmannibucau.boon.jaxrs;

import org.boon.json.JsonParserFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

@Provider
@Consumes(MediaType.WILDCARD)
public class BoonJsonMessageBodyReader<T> implements MessageBodyReader<T>{
    private final AtomicReference<JsonParserFactory> factory = new AtomicReference<JsonParserFactory>();

	private String charset = "UTF-8";
	private boolean lax = false;
	private boolean chop = false;
	private boolean lazyChop = true;
	private String fieldAccessType = null;
	private boolean useAnnotations = false;

	private JsonParserFactory factory() {
		JsonParserFactory value = factory.get();
		if (value == null) {
			synchronized (factory) {
                value = factory.get();
				if (value == null) {
                    value = Boons.createJsonParserFactory(charset, lax, chop, lazyChop, fieldAccessType, useAnnotations);
                    factory.set(value);
				}
			}
		}
		return value;
	}

	protected void init(final JsonParserFactory factory) {
        if (factory != null) {
            this.factory.set(factory);
        } else {
            factory(); // force init
        }
	}

	@Override
	public T readFrom(final Class<T> tClass, final Type type, final Annotation[] annotations, final MediaType mediaType,
			final MultivaluedMap<String, String> stringStringMultivaluedMap, final InputStream inputStream) throws IOException {
		return factory().create().parse(tClass, inputStream);
	}

	@Override
	public boolean isReadable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
		return Jsons.isJson(mediaType) && InputStream.class != aClass && Reader.class != aClass;
	}

	public void setCharset(final String charset) {
		this.charset = charset;
	}

	public void setLax(final boolean lax) {
		this.lax = lax;
	}

	public void setChop(final boolean chop) {
		this.chop = chop;
	}

	public void setLazyChop(final boolean lazyChop) {
		this.lazyChop = lazyChop;
	}

	public void setFieldAccessType(final String fieldAccessType) {
		this.fieldAccessType = fieldAccessType;
	}

	public void setUseAnnotations(final boolean useAnnotations) {
		this.useAnnotations = useAnnotations;
	}
}
