package spano.unica.it.plain.Nearables;
import java.util.ArrayList;
/**
 * Created by giovy on 14/07/2017.
 */

public class StickerList extends ArrayList<String> {

    public boolean equals(StickerList skl){
        //due gruppi di stickers sono uguali se uno dei due è sottoinsieme stretto dell'altro
        return (this.containsAll(skl) || skl.containsAll(this));
    }

    @Override
    public String toString(){
        String string = "";
        for(String s : this)
            string += s + ", ";

        return string;
    }

}
