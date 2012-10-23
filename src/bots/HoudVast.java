package bots;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/*
 *
 * Laat Bucket dingen vasthouden!
 *
 */

public class HoudVast extends AnswerBot
{
    
    String items;
    Boolean itemsWasLeeg;
    
    HoudVast(){
        items = "";
        itemsWasLeeg = true;
    }
    
    @override
    public String ask(String in, String who)
    {
        
        String msg = "/me ";
        if(in.toLowerCase().contains("geeft bucket "))
        {
            String item;
            String[] words = in.split(" ");
            if(words.length > 3)
            {
                item = words[3];
                for(int i = 4; i < words.length && i < 20; i++)
                {
                    item += " ";
                    item += words[i];
                }
            }
            else return "Sorry, wat geef je me?";
            
            if(itemsWasLeeg)
            {
                msg += "heeft ";
                msg += item;
                items = item;
                itemsWasLeeg = false;
            }
            else 
            {
                msg += "geeft ";
                msg += (who + " ");
                msg += (items + " ");
                msg += "in ruil voor ";
                msg += item;
                items = item;
            }

            return (msg + ".");
        } else{return void;}
    }
}