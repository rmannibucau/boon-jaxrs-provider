package com.github.rmannibucau.boon.jaxrs;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBoonJsonMessageBodyWriter {
    @BeforeClass // 1.8.0 is not handled, merged on master
    public static void forceJava() {
        System.setProperty("java.version", "1.7.0_0");
    }

    @Test
    public void sizeIsMinusOne() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().getSize(null, null, null, null, null)).isEqualTo(-1);
    }

    @Test
    public void isWritableJson() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(Object.class, Object.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isTrue();
    }

    @Test
    public void isNotWritableInputStream() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(InputStream.class, InputStream.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(InputStream.class, InputStream.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }
    @Test
    public void isNotWritableOutputStream() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(OutputStream.class, OutputStream.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(OutputStream.class, OutputStream.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void isNotWritableWriter() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(Writer.class, Writer.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(Writer.class, Writer.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void isNotWritableStreamingOutput() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(StreamingOutput.class, StreamingOutput.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(StreamingOutput.class, StreamingOutput.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void isNotWritableResponse() {
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(Response.class, Response.class, new Annotation[0], MediaType.APPLICATION_XML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyWriter<Object>().isWriteable(Response.class, Response.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void marshallingPojo() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new BoonJsonMessageBodyWriter<WritablePojo>().writeTo(
                new WritablePojo("a", "the name", 25),
                WritablePojo.class, WritablePojo.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                baos
        );
        assertThat(new String(baos.toByteArray())).isEqualTo("{\"id\":\"a\",\"name\":\"the name\",\"age\":25}");
    }

    @Test
    public void marshallingList() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new BoonJsonMessageBodyWriter<List>().writeTo(
                new ArrayList<String>(asList("a", "b", "c")),
                List.class, List.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                baos
        );
        assertThat(new String(baos.toByteArray())).isEqualTo("[\"a\",\"b\",\"c\"]");
    }

    @Test
    public void marshallingMap() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new BoonJsonMessageBodyWriter<Map>().writeTo(
                new TreeMap<String, String>(new HashMap<String, String>() {{ // inner classes are not well serialized
                    put("id", "a");
                    put("name", "the name");
                    put("age", "25");
                }}),
                Map.class, Map.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                baos
        );
        assertThat(new String(baos.toByteArray())).isEqualTo("{\"age\":\"25\",\"id\":\"a\",\"name\":\"the name\"}");
    }

    public static class WritablePojo {
        private String id;
        private String name;
        private int age;

        public WritablePojo(final String id, final String name, final int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final WritablePojo that = WritablePojo.class.cast(o);
            return age == that.age && id.equals(that.id) && name.equals(that.name);

        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + name.hashCode();
            result = 31 * result + age;
            return result;
        }
    }
}
