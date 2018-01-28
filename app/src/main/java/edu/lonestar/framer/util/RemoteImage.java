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
        this.id = Integer.parseInt(json.substring(0, json.indexOf(",")));
        //artist
        json=json.substring(json.indexOf(":")+3);
        this.artist = (json.substring(0, json.indexOf("\"")));
        //height
        json=json.substring(json.indexOf(":")+2);
        this.year = Integer.parseInt(json.substring(0, json.indexOf(",")));
        //Width
        json=json.substring(json.indexOf(":")+2); // start to count after the colon
        this.height = Integer.parseInt(json.substring(0, json.indexOf(",")));// start at the prvious value, ini to 0 and count on
        //artist
        json=json.substring(json.indexOf(":")+2);
        this.width = Integer.parseInt(json.substring(0, json.indexOf(",")));
        //URL
        json=json.substring(json.indexOf(":")+3);
        this.url = (json.substring(0, json.indexOf("\"")));
        //title
        json=json.substring(json.indexOf(":")+3);
        this.title = (json.substring(0, json.indexOf("\"")));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
