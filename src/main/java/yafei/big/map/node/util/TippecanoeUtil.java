package yafei.big.map.node.util;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TippecanoeUtil {
    private static String    DEFAULTCHART    = "UTF-8";
    private static String    GBK    = "GBK";
    private static Connection        conn;
    private static String            ip;
    private static  String            userName;
    private static String            userPwd;


    public static void execute(String inPath,String outPath) {
        //String test = "ipconfig";
        //Runtime.getRuntime().exec( "ifconfg");

        String sh = "tippecanoe -zg  -f -o "+outPath+" --coalesce-densest-as-needed --extend-zooms-if-still-dropping "+inPath;
        log.info("sh->{}",sh);
        Process pid = null;
        BufferedReader bufferedReader = null;
        try {
            pid = Runtime.getRuntime().exec(sh);
            log.info("{}",pid);
            bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream(),GBK));
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                log.info(line);
            }
            String s = processStream(pid.getErrorStream(), GBK);
            log.info("{}",s);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute(String url,String userName,String password,String inPath,String outPath) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        Session session = null;
        try {
            conn = new Connection("10.88.51.108");
            conn.connect();
            conn.authenticateWithPassword("root", "Kjy_1807");

            session = conn.openSession();
            session.execCommand("tippecanoe -zg  -f -o  /opt/all.mbtiles --coalesce-densest-as-needed --extend-zooms-if-still-dropping /opt/vpreport/all.csv");
            InputStream is = session.getStdout();
            stdOut = new StreamGobbler(session.getStdout());
            outStr = processStream(stdOut, DEFAULTCHART);
            stdErr = new StreamGobbler(session.getStderr());
            outErr = processStream(stdErr, DEFAULTCHART);
            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 1000 * 3600);
            System.out.println("outStr=" + outStr);
            System.out.println("outErr=" + outErr);


//            BufferedReader brs = new BufferedReader(new InputStreamReader(is, DEFAULTCHART));
//            List<String> result = new ArrayList<String>();
//            for (String line = brs.readLine(); line != null; line = brs.readLine()) {
//                result.add(line);
//                System.out.println(line);
//            }
//            if(result.size() ==0) {
//                System.out.println(result);
//            }
//            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 1000 * 3600);
            System.out.println("ExitCode: " + session.getExitStatus()); //得到脚本运行成功与否的标志 ：0 成功,非0 失败*/



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            session.close();
            conn.close();
        }
        //return result;
    }
    private static String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        execute("","");
       // RemoteExecuteCommand rec = new RemoteExecuteCommand();

    }
}
