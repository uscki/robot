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
    //De variabele die opslaat wat er allemaal in Bucket zit.
    ArrayList<String> inhoud;
    //De variabele die opslaat wat Bucket vasthoudt.
    String item;
    
    HoudVast()
    {
        inhoud = new ArrayList<String>();
        item = "";
    }
    
    @Override
    public String ask(String in, String who)
    {   
        return "Ik ben nog niet volledig geïmplementeerd :-(";
        /*
        String msg;
        
        if(in.toLowerCase().contains("geeft bucket"))
        {
            String[] invoer;
            invoer = in.split(" ");
            
            String temp;
            
            if(invoer[1] == "geeft")
            {
                temp = invoer[3];
                for(int i = 4; i < invoer.length; i++)
                {
                    temp += " ";
                    temp += invoer[i];
                }
                msg = ("/me geeft " + who + " een " + item + " in ruil voor " + temp);
                item = temp;
                return msg;
            }
        }
        else if(in.toLowerCase().contains("stopt") && in.toLowerCase().contains("in bucket"))
        {
            String[] invoer;
            invoer = in.split(" ");
            
            String temp;
            
            if(invoer[1] == "stopt")
            {
                temp = invoer[2];
                for(int i = 3; i < invoer.length-2;i++)
                {
                    temp += " ";
                    temp += invoer[i];
                }
                inhoud.add(temp);
                
                msg = "/me heeft nu " + temp + " in zich";
                return msg;
            }
        }
        return null;*/
    }
}