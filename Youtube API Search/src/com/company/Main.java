package com.company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) throws IOException, JSONException {
        JFrame frame= new JFrame("Tutorial");;
        frame.setSize(1000, 1000);
        JTextField jt=new JTextField(30);;






        JPanel p = new JPanel();

        try {

            UIManager.setLookAndFeel(UIManager.
                    getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
        }

        p.setBackground(Color.white);




        p.add(jt);

        frame.add(p);


        String url=JOptionPane.showInputDialog(null, "Enter Video ID");

        String videoURL= new String();
        videoURL="https://www.googleapis.com/youtube/v3/videos?id="+ url +"&key=YOUR API KEY HERE=snippet,contentDetails,statistics,status";
        URL myUrl= new URL(videoURL) ;
        URLConnection myUrlConnection = myUrl.openConnection();
        java.io.InputStream myInputStream = myUrlConnection.getInputStream();
        InputStreamReader myInputStreamReader;
        myInputStreamReader = new InputStreamReader(myInputStream);

        BufferedReader in = new BufferedReader(myInputStreamReader);

        String line, str;str = "";
        System.out.print("\n");
        while ((line = in.readLine()) != null){
            str += line;
            System.out.println(line);
        }


        JSONObject myJsonObject;
        myJsonObject = new JSONObject(str);


        JSONArray itemArray = myJsonObject.getJSONArray("items");

        JSONObject itemObject1 = itemArray.getJSONObject(0);

        JSONObject snipObject = itemObject1.getJSONObject("snippet");

        String date = snipObject.getString("publishedAt");

        String title = snipObject.getString("title");

        JSONObject thumbnailObject =snipObject.getJSONObject("thumbnails");

        String channelTitle = snipObject.getString("channelTitle");

        JSONObject thumbnailURLObject=thumbnailObject.getJSONObject("high");

        String thumbURL = thumbnailURLObject.getString("url");

        JSONObject statObject = itemObject1.getJSONObject("statistics");

        String views = statObject.getString("viewCount");

        String likes = statObject.getString("likeCount");

        String dislikes = statObject.getString("dislikeCount");


        Image image = null;
        try {
            URL thumbnailURL = new URL(thumbURL);
            image = ImageIO.read(thumbnailURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame newframe = new JFrame();
        newframe.setSize(1000, 1000);
        JLabel label = new JLabel(new ImageIcon(image));
        JPanel p1=new JPanel();
        p1.add(label);
        newframe.add(p1);



       String sArray[]=new String[7];

       sArray[0]=title;
        sArray[1]="uploaded by " + channelTitle;
        sArray[2]="uploaded on " + date;
        sArray[3]="View count " + views;
        sArray[4]="Likes " + likes;
        sArray[5]="Dislikes " + dislikes;
        sArray[6]="https://www.youtube.com/watch?v=" + url;

       JList list = new JList(sArray);

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);









        newframe.add(list);

        newframe.setVisible(true);

        Popup po;
        PopupFactory pf;
        pf = new PopupFactory();
        po = pf.getPopup(newframe, label, 250, 250);

        po.show();

    }
}
