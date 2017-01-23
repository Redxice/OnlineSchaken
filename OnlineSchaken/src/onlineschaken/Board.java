/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;


import Shared.IrmiClient;
import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
public class Board //implements IrmiClient
{

    private int tileSize = 80;
    private int width = 8;
    private int height = 8;
    private Section[][] sections = new Section[width][height];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private final Pane root = new Pane();
    private Section firstSection;
    private Piece piece;
    //Moet nog een enum van worden gemaakt
    private String turn = "white";
    private Game game;
    private final IrmiClient client;

    /**
     *
     * @param client
     */
    public Board(IrmiClient client)
    {
        this.client = client;
    }

    /**
     *
     * @return
     */
    public Parent createContent()
    {
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Section section = new Section((x + y) % 2 == 0, x, y, this);
                section.setOnMouseClicked((MouseEvent t) ->
                {
                    if (firstSection == null && section.getPiece() != null)
                    {
                        try {
                            if(client.GetGameController().isWhite() == true && section.getPiece().getColor().equals("white")  && client.GetGameController().isSpectator() == false|| client.GetGameController().isWhite() == false && section.getPiece().getColor().equals("black") && client.GetGameController().isSpectator() == false)
                            {
                                if (section.getPiece() != null)
                                {
                                    firstSection = sections[section.getID().x][section.getID().y];
                                    piece = firstSection.getPiece();
                                }
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (firstSection != null)
                    {
                        Point point = new Point(firstSection.getID());
                        try
                        {
                            
                            if (client.GetGameController().getMyTurn())
                            {
                                
                                if (piece.move(section))
                                {
                                    try
                                    {
                                        
                                        client.sendTurn(point, section.getID(), game.getTime());
                                        client.GetGameController().setLocalLastMove(point, section.getID());
                                        client.GetGameController().addToMoveHistory(point, section.getID(), piece);
                                        
                                    } catch (Exception e)
                                    {
                                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, e);
                                    }
                                    firstSection = null;
                                    piece = null;
                                    if (game.draw())
                                    {
                                        JOptionPane.showMessageDialog(null, "draw");
                                    }
                                    if (game.checkMate())
                                    {
                                        if ("white".equals(turn))
                                        {
                                            game.setWinner(game.getPlayer1());
                                        } else
                                        {
                                            game.setWinner(game.getPlayer2());
                                        }
                                        client.GetGameController().gameover();
                                        game.setFinished(true);
                                    }
                                    if ("white".equals(turn))
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
                        } catch (RemoteException ex)
                        {
                            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                sections[x][y] = section;

                tileGroup.getChildren().add(section);
            }

        }
        return root;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param p_section1
     * @param p_section2
     * @return
     */
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
                    section.setOnMouseClicked((MouseEvent t) ->
                    {
                        if (firstSection == null && section.getPiece() != null)
                        {
                            try {
                                if(client.GetGameController().isWhite() == true && section.getPiece().getColor().equals("white") && client.GetGameController().isSpectator() == false || client.GetGameController().isWhite() == false && section.getPiece().getColor().equals("black") && client.GetGameController().isSpectator() == false)
                                {
                                    if (section.getPiece() != null)
                                    {
                                        firstSection = sections[section.getID().x][section.getID().y];
                                        piece = firstSection.getPiece();
                                    }
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (firstSection != null)
                        {
                            Point point = new Point(firstSection.getID());
                            try
                            {
                                if (client.GetGameController().getMyTurn()||client.GetGameController().isPromoting())
                                {
                                    if (piece.move(section))
                                    {
                                        try
                                        {
                                            System.out.println("voor send turn");
                                            client.sendTurn(point, section.getID(), game.getTime());
                                            client.GetGameController().setLocalLastMove(point, section.getID());
                                            
                                        } catch (Exception e)
                                        {
                                            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                        firstSection = null;
                                        piece = null;
                                        if (game.draw())
                                        {
                                            JOptionPane.showMessageDialog(null, "draw");
                                        }
                                        if (game.checkMate())
                                        {
                                            if ("white".equals(turn))
                                            {
                                                game.setWinner(game.getPlayer1());
                                            } else
                                            {
                                                game.setWinner(game.getPlayer2());
                                            }
                                            client.GetGameController().gameover();
                                            game.setFinished(true);
                                            
                                        }
                                        
                                        if ("white".equals(getTurn()) && !client.GetGameController().isPromoting())
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
                            } catch (RemoteException ex)
                            {
                                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    });
                    sections[y.getID().x][y.getID().y] = section;

                   Platform.runLater(()->tileGroup.getChildren().add(section));
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
                    section.setOnMouseClicked((MouseEvent t) ->
                    {
                        if (firstSection == null && section.getPiece() != null)
                        {
                            try {
                                if(client.GetGameController().isWhite() == true && section.getPiece().getColor().equals("white") && client.GetGameController().isSpectator() == false || client.GetGameController().isWhite() == false && section.getPiece().getColor().equals("black") && client.GetGameController().isSpectator() == false)
                                {
                                    if (section.getPiece() != null)
                                    {
                                        firstSection = sections[section.getID().x][section.getID().y];
                                        piece = firstSection.getPiece();
                                    }
                                }
                            } catch (RemoteException ex) {
                                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (firstSection != null)
                        {
                            Point point = new Point(firstSection.getID());
                            try
                            {
                                if (client.GetGameController().getMyTurn())
                                {

                                    if (piece.move(section))
                                    {
                                        
                                        try
                                        {
                                            
                                            client.sendTurn(point, section.getID(), game.getTime());
                                            client.GetGameController().setLocalLastMove(point, section.getID());
                                        } catch (Exception e)
                                        {
                                            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                        firstSection = null;
                                        piece = null;
                                        if (game.draw())
                                        {
                                            JOptionPane.showMessageDialog(null, "draw");
                                        }
                                        if (game.checkMate())
                                        {
                                            if ("white".equals(turn))
                                            {
                                                game.setWinner(game.getPlayer1());
                                            } else
                                            {
                                                game.setWinner(game.getPlayer2());
                                            }
                                            client.GetGameController().gameover();
                                            game.setFinished(true);
                                            
                                        }
                                        if ("white".equals(getTurn()))
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
                            } catch (RemoteException ex)
                            {
                                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     *
     * @return
     */
    public int getTileSize()
    {
        return tileSize;
    }

    /**
     *
     * @return
     */
    public int getWidth()
    {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight()
    {
        return height;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Section getSections(int x, int y)
    {
        return sections[x][y];
    }

    /**
     *
     * @return
     */
    public Section[][] getSections()
    {
        return sections;
    }

    /**
     *
     * @return
     */
    public Group getTileGroup()
    {
        return tileGroup;
    }

    /**
     *
     * @return
     */
    public Group getPieceGroup()
    {
        return pieceGroup;
    }

    /**
     *
     * @param tileSize
     */
    public void setTileSize(int tileSize)
    {
        this.tileSize = tileSize;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     *
     * @param sections
     */
    public void setSections(Section[][] sections)
    {
        this.sections = sections;
    }

    /**
     *
     * @param game
     */
    public void setGame(Game game)
    {
        this.game = game;
    }

    /**
     *
     * @param tileGroup
     */
    public void setTileGroup(Group tileGroup)
    {
        this.tileGroup = tileGroup;
    }

    /**
     *
     * @param pieceGroup
     */
    public void setPieceGroup(Group pieceGroup)
    {
        this.pieceGroup = pieceGroup;
    }

    /**
     *
     * @return
     */
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
    
    /**
     *
     * @return
     */
    public IrmiClient getClient(){
        return this.client;
    }
    
    /**
     *
     * @param turn
     */
    public void setTurn(String turn)
    {
        this.turn = turn;
    }
}
