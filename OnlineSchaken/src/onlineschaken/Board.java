/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.awt.Point;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
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
    private Section[][] sections = new Section[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Pane root = new Pane();
    private Point firstClick;
    private Section firstSection;
    private Piece piece;
    
    public Board() {
        
    }

    
    
    public Parent createContent() {
        root.setPrefSize(WIDTH * TILE_SIZE + 100, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Section section = new Section((x + y) % 2 == 0, x, y,this);
                section.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            
                if(firstSection == null)
                        {
                            if(section.getPiece() != null)
                            {
                                firstSection = sections[section.id.x][section.id.y];
                                piece = firstSection.getPiece();
                            }
                        }
                else
                {
                    piece.move(section);
                    firstSection = null;
                    piece = null;
                }
            }
        });
                sections[x][y] = section;

                tileGroup.getChildren().add(section);
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
                if (y.getPiece() != null)
                {
                ImagePattern i = new ImagePattern(y.getPiece().img);
                y.setFill(i);
                pieceGroup.getChildren().add(y.getPiece()); 
                }
            }
        }     
    return root;
    }
    
    public Parent drawSpecificPieces(Section p_section1, Section p_section2)
    {
        ImagePattern i = new ImagePattern(p_section1.getPiece().img);
        Piece piece2 = p_section1.getPiece();
        for (Section[] x: sections)
        {
            for(Section y: x)
            {              
                if (y.id == p_section1.id)
                {
                    Section section = new Section((y.id.x + y.id.y) % 2 == 0, y.id.x, y.id.y,this);
                    section.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        
                        if(firstSection == null)
                        {
                            if(section.getPiece() != null)
                            {
                                firstSection = sections[section.id.x][section.id.y];
                                piece = firstSection.getPiece();
                            }
                        }
                        else
                        {
                            piece.move(section);
                            firstSection = null;
                            piece = null;
                        }
                    }
                });
                    sections[y.id.x][y.id.y] = section;
                    

                tileGroup.getChildren().add(section);
                //pieceGroup.getChildren().add(section.getPiece());
                }
                if (y.id == p_section2.id)
                {
                    y.setPiece(piece2);
                    y.setFill(i);
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

    public Section[][] getSections() {
        return sections;
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
