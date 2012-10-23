package bots;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/*
 *
 * Laat Bucket dingen vasthouden!
 *
 */

public class HoudVast
{
    
    private List<String> items;
    
    HoudVast(){
        items = new ArrayList<String>();
    }
    
    //@override
    public String ask(String in, String who)
    {
        System.out.println("bericht ontvangen: " + in + " Van: " + who);
        String msg = "/me ";
        if(in.toLowerCase().contains("geeft bucket "))
        {
            String[] words = in.split(" ");
            if(words.length > 3)
            {
                String item = words[3];
                for(int i = 4; i < words.length && i < 8; i++)
                {
                    item += " ";
                    item += words[i];
                }
                items.add(item);
            }
            else return "Sorry, wat geef je me?";
            //voeg alles toe aan msg
            msg += "heeft ";
            for(int i = 0; i < items.size(); i++)
            {
                msg += items.get(i);
                if(!(i == items.size()-1)){msg += ", ";}
            }
        }
        return msg;
    }
}