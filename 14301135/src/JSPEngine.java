import com.sun.org.apache.bcel.internal.generic.LoadClass;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lenovo on 2016/10/21.
 */
public class JSPEngine {

    public void compile() {
        ArrayList<String> fileNames = getFileName(System.getProperty("user.dir") + File.separator + "src/jspFiles/");
        for (Iterator iterator0 = fileNames.iterator(); iterator0.hasNext(); ) {

            String jspFileName = iterator0.next().toString();
            System.out.print(jspFileName);
            File jspFile = new File(jspFileName);
            try {
                if (!jspFile.exists() || jspFile.isDirectory()) {
                    throw new FileNotFoundException();
                }
                BufferedReader bufferedReader = new BufferedReader(new FileReader(jspFile));
                String tmp;
                ArrayList<String> fileContent = new ArrayList<>();
                try {
                    while ((tmp = bufferedReader.readLine()) != null) {
                        fileContent.add(tmp);
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<String> servFileContent = new ArrayList<>();
                ArrayList<String> servFileHead = new ArrayList<>();
                for (Iterator iterator = fileContent.iterator(); iterator.hasNext(); ) {
                    String tmpStr = (String) iterator.next();
                    String string;
                    int index1, index2;
                    if (tmpStr.startsWith("<%@") && tmpStr.endsWith("%>")) {
                        index1 = tmpStr.indexOf("<%@");
                        index2 = tmpStr.indexOf("%>", index1 + 1);
                        servFileHead.add(tmpStr.substring(index1 + 9, index2));
//                    string = "out.println(\"\\r\\n\");";
                        string = "";
                    } else if (tmpStr.startsWith("<%") && tmpStr.endsWith("%>")) {
                        index1 = tmpStr.indexOf("<%");
                        index2 = tmpStr.indexOf("%>");
                        string = tmpStr.substring(index1 + 3, index2 - 1);
                    } else if (tmpStr.startsWith("<") && tmpStr.endsWith(">")) {
                        tmp = tmpStr.replace("\"", "\\\"");
//                    string = "out.println(\"" + tmp + "\\r\\n\");";
                        string = "out.println(\"" + tmp + "\");";
                    } else if (tmpStr.startsWith("<%=") && tmpStr.endsWith("%>")) {
                        index1 = tmpStr.indexOf("<%=");
                        index2 = tmpStr.indexOf("%>");
//                    string = "out.println(\"<p>" + tmpStr.substring(index1 + 1, index2 - 1) + "</p>\\r\\n\")";
                        string = "out.println(\"<p>" + tmpStr.substring(index1 + 1, index2 - 1) + "</p>\")";
                    } else {
                        string = tmpStr;
                    }
                    servFileContent.add(string);
                }

                int index0 = jspFileName.lastIndexOf(".");
                int index1 = jspFileName.lastIndexOf("\\");
//            String javaFileName = jspFileName.substring(0, index0) + "_jsp.java";
                String servletName = jspFileName.substring(index1 + 1, index0) + "_jsp";
                GenerateServlet generateServlet = new GenerateServlet(servFileHead, servFileContent, servletName);
                generateServlet.generateServlet();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(Request request, Response response) {
        String uri = request.getUri();
        int index0 = uri.indexOf('/');
        int index1 = uri.indexOf('.');
        String jspFileName = uri.substring(index0 + 1, index1);
        String servletName = jspFileName + "_jsp";
        Object myClass = null;
        try {
            myClass = Class.forName(servletName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Servlet servlet;
        try {
            servlet = (Servlet) myClass;
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList getFileName(String path) {
        ArrayList<String> fileName = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            fileName.add(files[i].toString());
        }
        return fileName;
    }
}
