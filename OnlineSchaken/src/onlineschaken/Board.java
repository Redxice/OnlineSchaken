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
import javafx.scene.paint.ImagePattern;
import javax.swing.JOptionPane;

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
    private Section firstSection;
    private Piece piece;
    //Moet nog een enum van worden gemaakt
    private String turn = "white";
    private Game game;

   

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
                            if (getTurn() == section.getPiece().getColor())
                            {
                                if (section.getPiece() != null)
                                {
                                    firstSection = sections[section.getID().x][section.getID().y];
                                    piece = firstSection.getPiece();
                                }
                            }
                        } else if (firstSection != null)
                        {
                            if (piece.move(section))
                            {
                                firstSection = null;
                                piece = null;
                                if (game.draw())
                                {
                                    JOptionPane.showMessageDialog(null, "draw");
                                }
                                if (game.checkMate())
                                {
                                    if (turn == "white")
                                    {
                                        game.setWinner(game.getPlayer1());
                                    } else
                                    {
                                        game.setWinner(game.getPlayer2());
                                    }
                                    game.setFinished(true);
                                }
                                if (turn == "white")
                                {
                                    turn = "black";
                                } else
                                {
                                    turn = "white";
                                }
                            } else
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
                    ImagePattern i = new ImagePattern(y.getPiece().getImg());
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
                if (y.getID() == p_section1.getID())
                {
                    Section section = new Section((y.getID().x + y.getID().y) % 2 == 0, y.getID().x, y.getID().y, this);
                    section.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t)
                        {
                            if (firstSection == null && section.getPiece() != null)
                            {
                                if (getTurn() == section.getPiece().getColor())
                                {
                                    if (section.getPiece() != null)
                                    {
                                        firstSection = sections[section.getID().x][section.getID().y];
                                        piece = firstSection.getPiece();
                                    }
                                }
                            } else if (firstSection != null)
                            {
                                if (piece.move(section))
                                {
                                    firstSection = null;
                                    piece = null;
                                    if (game.draw())
                                    {
                                        JOptionPane.showMessageDialog(null, "draw");
                                    }
                                    if (game.checkMate())
                                    {
                                        if (turn == "white")
                                        {
                                            game.setWinner(game.getPlayer1());
                                        } else
                                        {
                                            game.setWinner(game.getPlayer2());
                                        }
                                        game.setFinished(true);
                                    }
                                    if (getTurn() == "white")
                                    {
                                        turn = "black";
                                    } else
                                    {
                                        turn = "white";
                                    }
                                } else
                                {
                                    firstSection = null;
                                }

                            }
                        }
                    });
                    sections[y.getID().x][y.getID().y] = section;

                    tileGroup.getChildren().add(section);
                }
                if (y.getID() == p_section2.getID())
                {
                    ImagePattern i = new ImagePattern(p_section1.getPiece().getImg());

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
                if (y.getID() == p_section1.getID())
                {
                    Section section = new Section((y.getID().x + y.getID().y) % 2 == 0, y.getID().x, y.getID().y, this);
                    section.setOnMouseClicked(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent t)
                        {
                            if (firstSection == null && section.getPiece() != null)
                            {
                                if (getTurn() == section.getPiece().getColor())
                                {
                                    if (section.getPiece() != null)
                                    {
                                        firstSection = sections[section.getID().x][section.getID().y];
                                        piece = firstSection.getPiece();
                                    }
                                }
                            } else if (firstSection != null)
                            {
                                if (piece.move(section))
                                {
                                    firstSection = null;
                                    piece = null;
                                    if (game.draw())
                                    {
                                        JOptionPane.showMessageDialog(null, "draw");
                                    }
                                    if (game.checkMate())
                                    {
                                        if (turn == "white")
                                        {
                                            game.setWinner(game.getPlayer1());
                                        } else
                                        {
                                            game.setWinner(game.getPlayer2());
                                        }
                                        game.setFinished(true);

                                    }
                                    if (getTurn() == "white")
                                    {
                                        turn = "black";
                                    } else
                                    {
                                        turn = "white";
                                    }
                                } else
                                {
                                    firstSection = null;
                                }

                            }
                        }
                    });
                    sections[y.getID().x][y.getID().y] = section;

                    tileGroup.getChildren().add(section);
                }
                if (y.getID() == p_section1.getID())
                {//verwijdert de piece uit de lijst van de speler.
                    y.getPiece().getPlayer().getPieces().remove(y.getPiece());
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

    public void setGame(Game game)
    {
        this.game = game;
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
}
