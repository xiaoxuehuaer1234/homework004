

import java.io.IOException;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.*;


public class RequestFacade implements ServletRequest {
    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    private ServletRequest request = null;

    public RequestFacade(Request request) {
        this.request = request;
    }

    /* implementation of the ServletRequest */
    public Object getAttribute(String attribute) {
        return request.getAttribute(attribute);
    }

    @SuppressWarnings("deprecation")
    public String getRealPath(String path) {
        return request.getRealPath(path);
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return request.getRequestDispatcher(path);
    }

    public boolean isSecure() {
        return request.isSecure();
    }

    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    public int getContentLength() {
        return request.getContentLength();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public Locale getLocale() {
        return request.getLocale();
    }

    public String getParameter(String name) {
        return request.getParameter(name);
    }

    public String[] getParameterValues(String parameter) {
        return request.getParameterValues(parameter);
    }

    public String getProtocol() {
        return request.getProtocol();
    }

    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    public String getScheme() {
        return request.getScheme();
    }

    public String getServerName() {
        return request.getServerName();
    }

    public int getServerPort() {
        return request.getServerPort();
    }

    public void removeAttribute(String attribute) {
        request.removeAttribute(attribute);
    }

    public void setAttribute(String key, Object value) {
        request.setAttribute(key, value);
    }

    public void setCharacterEncoding(String encoding)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding(encoding);
    }

}
