package make_a_connection;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {

    // instant variables
    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public static void main(String[] args) {

        SimpleChatClient client = new SimpleChatClient();
        client.go();

    }   // close main method

    public void go() {

        JFrame frame = new JFrame("Ludicrously Simple Chat Client");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 12);

        incoming = new JTextArea(15, 30);
        incoming.setFont(bigFont);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);

        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(BorderLayout.CENTER, qScroller);
        mainPanel.add(BorderLayout.SOUTH, outgoing);
        mainPanel.add(BorderLayout.SOUTH, sendButton);
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);


        //qScroller.update(frame.getGraphics());

    }   // close go method

    private void setUpNetworking() {

        try{

            sock = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");

        }   catch (IOException ex) {
            ex.printStackTrace();
        }

    }   // close setupnetworking method

    public class SendButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent ev) {

            try {
                writer.println(outgoing.getText());
                writer.flush();
            }   catch (Exception ex) {
                ex.printStackTrace();
            }

            outgoing.setText("");
            outgoing.requestFocus();

        }

    }   // close inner class SendButtonlistener

    public class IncomingReader implements Runnable {

        public void run() {

            String message;

            try {

                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    incoming.append(message + "\n");
                    System.out.println(incoming.getText());

                }  // close while

            }   catch (Exception ex) { ex.printStackTrace();    }



        }   // close run method

    }   // close inner class incomingReader

}













