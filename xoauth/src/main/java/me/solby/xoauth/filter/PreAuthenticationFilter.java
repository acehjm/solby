package me.solby.xoauth.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * me.solby.xoauth.filter
 *
 * @author majhdk
 * @date 2019-05-26
 */
public class PreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String SSO_TOKEN = "X-AUTH-TOKEN";
    private static final String SSO_CREDENTIALS = "N/A";

    public PreAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(SSO_TOKEN);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return SSO_CREDENTIALS;
    }
}
