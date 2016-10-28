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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 *
 * @author redxice
 */
public class Board
{

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
    private String turn = "white";
    
    public Board()
    {
        
    }

    public Parent createContent()
    {
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                Section section = new Section((x + y) % 2 == 0, x, y, this);
                section.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t)
                    {
                        if (firstSection == null && section.getPiece() != null)
                        {
                            if (getTurn() == section.getPiece().color)
                            {
                                if (section.getPiece() != null)
                                {
                                    firstSection = sections[section.id.x][section.id.y];
                                    piece = firstSection.getPiece();
                                }
                            }
                        } else if (firstSection != null)
                        {
                            if (piece.move(section))
                            {
                                firstSection = null;
                                piece = null;
                                if (getTurn() == "white")
                                {
                                    turn = "black";
                                } else
                                {
                                    turn = "white";
                                }
                            }
                            else
                            {
                               firstSection = null;
                            }

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
        for (Section[] x : sections)
        {
            for (Section y : x)
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
        
        Piece piece2 = p_section1.getPiece();
        for (Section[] x : sections)
        {
            for (Section y : x)
            {
                if (y.id == p_section1.id)
                {
                    Section section = new Section((y.id.x + y.id.y) % 2 == 0, y.id.x, y.id.y, this);
                    section.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t)
                    {
                        if (firstSection == null && section.getPiece() != null)
                        {
                            if (getTurn() == section.getPiece().color)
                            {
                                if (section.getPiece() != null)
                                {
                                    firstSection = sections[section.id.x][section.id.y];
                                    piece = firstSection.getPiece();
                                }
                            }
                        } else if (firstSection != null)
                        {
                            if (piece.move(section))
                            {
                                firstSection = null;
                                piece = null;
                                if (getTurn() == "white")
                                {
                                    turn = "black";
                                } else
                                {
                                    turn = "white";
                                }
                            }
                            else
                            {
                               firstSection = null;
                            }

                        }
                    }
                });
                    sections[y.id.x][y.id.y] = section;

                    tileGroup.getChildren().add(section);
                }
                if (y.id == p_section2.id)
                {
                    ImagePattern i = new ImagePattern(p_section1.getPiece().img);
                    
                    y.setPiece(piece2);
                    y.setFill(i);
                }
            }
        }
        return root;
    }
    /**
     * 
     * @param p_section1 de section waar de te verwijderen stuk staat.
     * @return 
     */
 public Parent ClearSection(Section p_section1)
    {
        
        for (Section[] x : sections)
        {
            for (Section y : x)
            {
                if (y.id == p_section1.id)
                {
                    Section section = new Section((y.id.x + y.id.y) % 2 == 0, y.id.x, y.id.y, this);
                    section.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t)
                    {
                        if (firstSection == null && section.getPiece() != null)
                        {
                            if (getTurn() == section.getPiece().color)
                            {
                                if (section.getPiece() != null)
                                {
                                    firstSection = sections[section.id.x][section.id.y];
                                    piece = firstSection.getPiece();
                                }
                            }
                        } else if (firstSection != null)
                        {
                            if (piece.move(section))
                            {
                                firstSection = null;
                                piece = null;
                                if (getTurn() == "white")
                                {
                                    turn = "black";
                                } else
                                {
                                    turn = "white";
                                }
                            }
                            else
                            {
                               firstSection = null;
                            }

                        }
                    }
                });
                    sections[y.id.x][y.id.y] = section;

                    tileGroup.getChildren().add(section);
                }
                if (y.id == p_section1.id)
                {//verwijdert de piece uit de lijst van de speler.
                    y.getPiece().player.getPieces().remove(y.getPiece());
                    y.setPiece(null);
                    y.setFill(null);
                }
            }
        }
        return root;
    }
    public int getTILE_SIZE()
    {
        return TILE_SIZE;
    }

    public int getWIDTH()
    {
        return WIDTH;
    }

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public Section getSections(int x, int y)
    {

        return sections[x][y];
    }

    public Section[][] getSections()
    {
        return sections;
    }

    public Group getTileGroup()
    {
        return tileGroup;
    }

    public Group getPieceGroup()
    {
        return pieceGroup;
    }

    public void setTILE_SIZE(int TILE_SIZE)
    {
        this.TILE_SIZE = TILE_SIZE;
    }

    public void setWIDTH(int WIDTH)
    {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT)
    {
        this.HEIGHT = HEIGHT;
    }

    public void setSections(Section[][] sections)
    {
        this.sections = sections;
    }

    public void setTileGroup(Group tileGroup)
    {
        this.tileGroup = tileGroup;
    }

    public void setPieceGroup(Group pieceGroup)
    {
        this.pieceGroup = pieceGroup;
    }

    public Pane getRoot()
    {
        return root;
    }

    /**
     * @return the turn
     */
    public String getTurn()
    {
        return turn;
    }
     public Popup getCheckmatePopUp(){
       Popup popUp = new Popup();
       Text text = new Text("schaakmat");
        HBox box = new HBox(5);
        Button ok = new Button("ok");
        box.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        box.getChildren().addAll(text,ok);
        popUp.getContent().add(box); 
        return popUp;
    }

}
