package make_a_connection;

import java.io.*;
import java.net.*;

public class DailyAdviceServer {

    String[] adviceList = {"Take smaller bites", "Go for the tight jeans. No they do Not make you look fat.",
            "one word: inappropriate", "Just for today, be honest. Tell Your boss what you *really* think", "You might want to rethink that haircut"};

    public void go() {

        try {

            ServerSocket serverSocket = new ServerSocket(4242);

            while(true) {

                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);

            }

        }   catch (IOException ex) {
            ex.printStackTrace();
        }

    }   // close of go method

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args){
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }

}
