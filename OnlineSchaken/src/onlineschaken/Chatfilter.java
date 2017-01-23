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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sander
 */
public class Chatfilter
{

    private final List<String> bannedWords = new ArrayList<>();

    /**
     *
     * @param message
     * @return
     */
    public String checkMessage(String message)
    {
        String replace = "";
        readBannedWords();
        for (String badword : getBannedWords())
        {
            if (message != null && badword != null)
            {
                if (message.contains(badword))
                {
                    replace = message.replace(badword, "****");
                }
            }
        }
        if (replace.equals(""))
        {
            return message;
        }
        return replace;
    }

    /**
     * read all banned words from text file and put in list
     */
    public void readBannedWords()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt")))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                getBannedWords().add(line);
            }
            br.close();
        } catch (Exception e)
        {
            Logger.getLogger(Chatfilter.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @return the bannedWords
     */
    public List<String> getBannedWords()
    {
        return bannedWords;
    }
}
