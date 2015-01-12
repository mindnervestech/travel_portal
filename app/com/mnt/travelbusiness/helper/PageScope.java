package com.mnt.travelbusiness.helper;

import play.mvc.Http;

public class PageScope {
	public static void scope(String key, Object o) {
		Http.Context.current().args.put("_pageScope_" + key , o);
	}
}
