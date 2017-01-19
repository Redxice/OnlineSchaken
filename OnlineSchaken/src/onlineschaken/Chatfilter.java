/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sander
 */
public class Chatfilter
{

    private List<String> bannedWords = new ArrayList<>();

    public String checkMessage(String message)
    {
        String replace = "";
        readBannedWords();
        for (String badword : bannedWords)
        {
            if (message != null && badword != null)
            {
                if (message.contains(badword))
                {
                    replace = message.replace(badword, "****");
                    //return message;
                }
            }
        }
        if (replace.equals(""))
        {
            return message;
        }
        return replace;
    }

    public void readBannedWords()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Sander\\Documents\\Fontys\\OnlineSchaken\\OnlineSchaken\\src\\onlineschaken\\words.txt")))
        {
            String line = null;
            while ((line = br.readLine()) != null)
            {
                bannedWords.add(line);
            }
            br.close();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
