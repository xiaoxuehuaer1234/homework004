import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lenovo on 2016/10/22.
 */
public class GenerateServlet {
    ArrayList head = null;
    ArrayList content = null;
    String fileName = null;

    public GenerateServlet(ArrayList head, ArrayList content, String fileName) {
        this.head = head;
        this.content = content;
        this.fileName = fileName;
    }

    public void generateServlet() {
        try {
            int index0 = fileName.indexOf(".");
            String javaFileName = fileName + ".java";
//            String javaFileName = fileName.substring(0, index0) + "_jsp.java";
            File javaFile = new File(System.getProperty("user.dir") + File.separator + "src/" + javaFileName);
            javaFile.createNewFile();
            System.out.print(javaFile);
            FileOutputStream fos = new FileOutputStream(javaFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));

            ArrayList fileHead = generateHead();
            for (Iterator iterator = fileHead.iterator(); iterator.hasNext(); ) {
                bufferedWriter.write(iterator.next().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.write("public class " + fileName + " implements Servlet{");
            bufferedWriter.newLine();
            ArrayList fileObject = generateObject();
            for (Iterator iterator = fileObject.iterator(); iterator.hasNext(); ) {
                bufferedWriter.write(iterator.next().toString());
                bufferedWriter.newLine();
            }
            for (Iterator iterator = content.iterator(); iterator.hasNext(); ) {
                bufferedWriter.write(iterator.next().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.write("}\r\n}");
            bufferedWriter.close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, System.getProperty("user.dir") + File.separator + "src/" + javaFileName);
            File source = new File(System.getProperty("user.dir") + File.separator + "src/" + fileName + ".class");
            File dest = new File(System.getProperty("user.dir") + File.separator + "out/production/Container/" + fileName + ".class");
            source.renameTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList generateObject() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("@Override\n" +
                "    public void init(ServletConfig servletConfig) throws ServletException {\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ServletConfig getServletConfig() {\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public String getServletInfo() {\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void destroy() {\n" +
                "\n" +
                "    }");
        objects.add("public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {");
        objects.add("request.setCharacterEncoding(\"utf-8\");");
        objects.add("PrintWriter out = response.getWriter();");

        return objects;
    }

    public ArrayList generateHead() {
        ArrayList<String> fileHead = new ArrayList<>();
        String str;
        fileHead.add("import java.io.IOException;");
        fileHead.add("import java.io.PrintWriter;");
        fileHead.add("import javax.servlet.*;");
        fileHead.add("import javax.servlet.http.*;");

        for (Iterator iterator = this.head.iterator(); iterator.hasNext(); ) {
            str = iterator.next().toString();
            if (str.startsWith("import")) {
                {
                    int index1 = str.indexOf("\"");
                    int index2 = str.indexOf("\"", index1 + 1);
                    fileHead.add("import " + str.substring(index1 + 1, index2) + ";");
                }
            }
        }
        return fileHead;
    }

}
