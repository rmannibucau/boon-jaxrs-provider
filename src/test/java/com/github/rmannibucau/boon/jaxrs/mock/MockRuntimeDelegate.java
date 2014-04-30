package com.github.rmannibucau.boon.jaxrs.mock;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.RuntimeDelegate;

public class MockRuntimeDelegate extends RuntimeDelegate {
    @Override
    public <T> T createEndpoint(final Application app, final Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UriBuilder createUriBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Variant.VariantListBuilder createVariantListBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> HeaderDelegate<T> createHeaderDelegate(final Class<T> headerType) {
        return new HeaderDelegate<T>() {
            @Override
            public T fromString(final String str) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String toString(final T obj) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Response.ResponseBuilder createResponseBuilder() {
        throw new UnsupportedOperationException();
    }
}
