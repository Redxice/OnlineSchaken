/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author redxice
 */
public class Board{
    
    private int TILE_SIZE = 80;    
    private int WIDTH = 8;
    private int HEIGHT = 8;
    public Section[][] sections = new Section[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Pane root = new Pane();
    public Board() {
        
    }

    
    
    public Parent createContent() {
        root.setPrefSize(WIDTH * TILE_SIZE + 100, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Section section = new Section((x + y) % 2 == 0, x, y,this);
                sections[x][y] = section;

                tileGroup.getChildren().add(section);
              if(x==1 & y==1)
             {
             section.setFill(Color.BLUE);
             }
            }          
        }
        return root;
    }
    
    public Parent createContent2()
    {
        for (Section[] x: sections)
        {
            for(Section y: x)
            {              
                if (y.piece != null)
                {
                ImagePattern i = new ImagePattern(y.piece.img);
                y.setFill(i);
                pieceGroup.getChildren().add(y.piece); 
                }
            }
        }     
    return root;
    }
    
    public Parent drawSpecificPieces(Section p_section1, Section p_section2)
    {
                ImagePattern i = new ImagePattern(p_section1.piece.img);
                //p_section2.setFill(i);
                Piece piece = p_section1.piece;
                int counter = 0;
        for (Section[] x: sections)
        {
            for(Section y: x)
            {              
                if (y.id == p_section1.id)
                {
                    y.piece = null;
                    y.setFill(i);
                }
                if (y.id == p_section2.id)
                {
                    y.piece = piece;
                    y.setFill(i);
                    counter++;
                }
                if(counter == 1)
                {
                    i = null;
                }
            }
        }
    return root;
    }
    
    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Section getSections(int x, int y) {
       
        return sections[x][y];
    }


    public Group getTileGroup() {
        return tileGroup;
    }

    public Group getPieceGroup() {
        return pieceGroup;
    }

    public void setTILE_SIZE(int TILE_SIZE) {
        this.TILE_SIZE = TILE_SIZE;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setSections(Section[][] sections) {
        this.sections = sections;
    }

    

    public void setTileGroup(Group tileGroup) {
        this.tileGroup = tileGroup;
    }

    public void setPieceGroup(Group pieceGroup) {
        this.pieceGroup = pieceGroup;
    }

    public Pane getRoot() {
        return root;
    }
    
}
