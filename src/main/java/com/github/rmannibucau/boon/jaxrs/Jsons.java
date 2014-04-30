package com.github.rmannibucau.boon.jaxrs;

import javax.ws.rs.core.MediaType;

public class Jsons {
	public static boolean isJson(final MediaType mediaType) {
		if (mediaType != null) {
			final String subtype = mediaType.getSubtype();
			return "json".equalsIgnoreCase(subtype)
					|| "javascript".equals(subtype)
					|| "x-json".equals(subtype)
					|| "x-javascript".equals(subtype)
					|| subtype.endsWith("+json");
		}
		return true;
	}

	private Jsons() {
		// no-op
	}
}
