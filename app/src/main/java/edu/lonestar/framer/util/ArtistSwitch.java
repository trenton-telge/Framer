package edu.lonestar.framer.util;

import android.app.Activity;

/**
 * Created by husse on 1/27/2018.
 */

public class ArtistSwitch  extends Activity{
    boolean selected;
    String name;
    ArtistSwitch(String artist, boolean select)
{
    // assigning them
    this.selected = select;
    this.name = artist;

}


}
