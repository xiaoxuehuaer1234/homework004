/**
 * Created by lenovo on 2016/10/7.
 */

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Container {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public void process(Request request, Response response){
        String uri = request.getUri();

        //String servletName = uri.substring(uri.lastIndexOf('/') + 1);
        XMLParser xmlParser = new XMLParser();


        int paraFlag = -1;
        paraFlag = uri.indexOf("?");
        String servletName = null;
        if(paraFlag!=-1){
            try {
                servletName = xmlParser.getServlet(uri.substring(uri.lastIndexOf("/"),paraFlag));
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            HashMap<String,String> param = new HashMap<String, String>();
            int lastParaFlag = paraFlag;
            int nextParaFlag = -1;
            String p,p1,p2;
            while(true){
                nextParaFlag = uri.indexOf("&",nextParaFlag+1);
                if(nextParaFlag!=-1){
                    p = uri.substring(lastParaFlag+1, nextParaFlag);
                }else{
                    p = uri.substring(lastParaFlag+1);
                }
                lastParaFlag = nextParaFlag;
                p1 = p.substring(0, p.indexOf("="));
                p2 = p.substring(p.indexOf("=")+1);
                param.put(p1, p2);
                request.setParams(param);
                //String line = p1+":"+param.get(p1);
                //System.out.println(line);
                if(nextParaFlag==-1){
                    break;
                }
            }
        }else{
            try {
                servletName = xmlParser.getServlet(uri.substring(uri.lastIndexOf("/")));
                HashMap<String,String> param = new HashMap<String, String>();
                request.setParams(param);
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }





//        URLClassLoader loader = null;
//        try {
//            URL[] urls = new URL[1];
//            URLStreamHandler streamHandler = null;
//            File classPath = new File(WEB_ROOT);
//            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
//            urls[0] = new URL(null, repository, streamHandler);
//            loader = new URLClassLoader(urls);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        Object myClass = null;
        try {
            myClass = Class.forName(servletName).newInstance();
//            myClass = loader.loadClass(className);
        }catch (Exception e){
            e.printStackTrace();
        }
        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass;
            //servlet = (Servlet)myClass.newInstance();
            //System.out.print(servlet+"1234");
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
            //servlet.service((ServletRequest) request, (ServletResponse) response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
