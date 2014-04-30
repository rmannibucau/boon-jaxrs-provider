package com.github.rmannibucau.boon.jaxrs;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoonJsonMessageBodyReaderTest {
    @BeforeClass // 1.8.0 is not handled, merged on master
    public static void forceJava() {
        System.setProperty("java.version", "1.7.0_0");
    }

    @Test
    public void isReadableInputStream() {
        assertThat(new BoonJsonMessageBodyReader<Object>().isReadable(InputStream.class, InputStream.class, new Annotation[0], MediaType.TEXT_HTML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyReader<Object>().isReadable(InputStream.class, InputStream.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void isReadableReader() {
        assertThat(new BoonJsonMessageBodyReader<Object>().isReadable(Reader.class, Reader.class, new Annotation[0], MediaType.TEXT_HTML_TYPE)).isFalse();
        assertThat(new BoonJsonMessageBodyReader<Object>().isReadable(Reader.class, Reader.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isFalse();
    }

    @Test
    public void isReadableJson() {
        assertThat(new BoonJsonMessageBodyReader<Object>().isReadable(Object.class, Object.class, new Annotation[0], MediaType.APPLICATION_JSON_TYPE)).isTrue();
    }

    @Test
    public void unmarshallingPojo() throws IOException {
        assertThat(new BoonJsonMessageBodyReader<ReadablePojo>().readFrom(
                ReadablePojo.class, ReadablePojo.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                new ByteArrayInputStream(
                        "{\"id\":\"a\", \"name\":\"the name\", \"age\":25}".getBytes()
                )
        )).isEqualTo(new ReadablePojo("a", "the name", 25));
    }

    @Test
    public void unmarshallingList() throws IOException {
        assertThat(new BoonJsonMessageBodyReader<List>().readFrom(
                List.class, List.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                new ByteArrayInputStream(
                        "[\"a\",\"b\",\"c\"]".getBytes()
                )
        )).isEqualTo(new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }});
    }

    @Test
    public void unmarshallingMap() throws IOException {
        assertThat(new BoonJsonMessageBodyReader<Map>().readFrom(
                Map.class, Map.class,
                new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null,
                new ByteArrayInputStream(
                        "{\"id\":\"a\", \"name\":\"the name\", \"age\":\"25\"}".getBytes()
                )
        )).isEqualTo(new HashMap<String, String>() {{
            put("id", "a");
            put("name", "the name");
            put("age", "25");
        }});
    }

    public static class ReadablePojo {
        private String id;
        private String name;
        private int age;

        public ReadablePojo(final String id, final String name, final int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final ReadablePojo that = ReadablePojo.class.cast(o);
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
