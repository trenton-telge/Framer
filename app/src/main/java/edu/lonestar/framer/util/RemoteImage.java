package edu.lonestar.framer.util;

/**
 * Created by LUONG LUONG on 1/27/2018.
 */

public class RemoteImage
{
    private int id;
    private int year;
    private int height;
    private int width;
    private String artist;
    private String url;
    private String title;

    RemoteImage(int id, int yr, int h,int w,String author,String u, String name)
    {
        id=id;
        year= yr;
        height= h;
        width= w;
        artist= author;
        url= u;
        title= name;
    }

    public RemoteImage(String json)    // Split Json string
    {
        json=json.substring(json.indexOf(":")+2);
        System.out.println(json); // just to test not necessary

    }
}
