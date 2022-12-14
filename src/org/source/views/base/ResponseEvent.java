package org.source.views.base;

import java.util.Map;

public interface ResponseEvent {
	public void onSuccessResponse(Map<String, String> response);
	public void onErrorResponse(Map<String, String> response);
}
