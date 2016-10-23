/**
 * Created by lenovo on 2016/10/7.
 */
import java.io.*;
public class StaticResourceProcessor {
    public void process(Request requst, Response response){
        try {
            response.sendStaticResource();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
