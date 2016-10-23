/**
 * Created by lenovo on 2016/10/7.
 */

import javax.servlet.*;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {
    private int BUFFER_SIZE = 1024;
    Request requst;
    OutputStream outputStream;
    PrintWriter printWriter;
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src";

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequst(Request requst) {
        this.requst = requst;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fileInputStream = null;
        try {
            File file = new File(WEB_ROOT, requst.getUri());
            fileInputStream = new FileInputStream(file);
            int size = fileInputStream.read(bytes, 0, BUFFER_SIZE);
            if (size != -1) {
                outputStream.write(bytes, 0, size);
                size = fileInputStream.read(bytes, 0, BUFFER_SIZE);
            }
        } catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            outputStream.write(errorMessage.getBytes());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }


    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
