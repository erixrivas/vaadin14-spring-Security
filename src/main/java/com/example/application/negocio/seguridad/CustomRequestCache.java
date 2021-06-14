package com.example.application.negocio.seguridad;

public class CustomRequestCache extends HttpSessionRequestCache {

	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}

	public String resolveRedirectUrl() {
		SavedRequest savedRequest = getRequest(VaadinServletRequest.getCurrent().getHttpServletRequest(),
				VaadinServletResponse.getCurrent().getHttpServletResponse());
		if (savedRequest instanceof DefaultSavedRequest) {
			final String requestURI = ((DefaultSavedRequest) savedRequest).getRequestURI(); //
			// check for valid URI and prevent redirecting to the login view
			if (requestURI != null && !requestURI.isEmpty() && !requestURI.contains(LoginView.ROUTE)) { //
				return requestURI.startsWith("/") ? requestURI.substring(1) : requestURI; //
			}
		}

		// if everything fails, redirect to the main view
		return "";
	}
}