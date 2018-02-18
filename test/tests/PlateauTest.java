/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javachess.*;
import pieces.*;

/**
 *
 * @author Quentin
 */
public class PlateauTest {
    
    private Plateau plateau;
    
    public PlateauTest() {
        this.plateau = new Plateau();
    }
    

    @Test
    // Permet de vérifier que les cases sont bien placées
    public void testInitBoarder() {
        this.plateau.initBoarder();
        // On vérifie les cases qui existent
        assertNotNull(this.plateau.getCase(0, 0));
        assertNotNull(this.plateau.getCase(1, 0));
        assertNotNull(this.plateau.getCase(2, 0));
        assertNotNull(this.plateau.getCase(3, 0));
        assertNotNull(this.plateau.getCase(4, 0));
        assertNotNull(this.plateau.getCase(5, 0));
        assertNotNull(this.plateau.getCase(6, 0));
        assertNotNull(this.plateau.getCase(7, 0));
        
        assertNotNull(this.plateau.getCase(0, 1));
        assertNotNull(this.plateau.getCase(0, 2));
        assertNotNull(this.plateau.getCase(0, 3));
        assertNotNull(this.plateau.getCase(0, 4));
        assertNotNull(this.plateau.getCase(0, 5));
        assertNotNull(this.plateau.getCase(0, 6));
        assertNotNull(this.plateau.getCase(0, 7));
        
        // On teste des cases qui n'existent pas
        assertNull(this.plateau.getCase(0, 8));
        assertNull(this.plateau.getCase(8, 0));
        assertNull(this.plateau.getCase(8, 8));
    }
    
    @Test
    // Permet de vérifier que chaque pièce est bien l'instance de ce qu'elle est censée être
    public void testGetUnePiece() {
        this.plateau.initBoarder();
        
        // On teste le haut : noir
        assertTrue(this.plateau.getCase(0, 0).getUnePiece() instanceof Tour);
        assertTrue(this.plateau.getCase(1, 0).getUnePiece() instanceof Cavalier);
        assertTrue(this.plateau.getCase(2, 0).getUnePiece() instanceof Fou);
        assertTrue(this.plateau.getCase(3, 0).getUnePiece() instanceof Reine);
        assertTrue(this.plateau.getCase(4, 0).getUnePiece() instanceof Roi);
        assertTrue(this.plateau.getCase(5, 0).getUnePiece() instanceof Fou);
        assertTrue(this.plateau.getCase(6, 0).getUnePiece() instanceof Cavalier);
        assertTrue(this.plateau.getCase(7, 0).getUnePiece() instanceof Tour);
        
        // On teste le bas : blanc
        assertTrue(this.plateau.getCase(0, 7).getUnePiece() instanceof Tour);
        assertTrue(this.plateau.getCase(1, 7).getUnePiece() instanceof Cavalier);
        assertTrue(this.plateau.getCase(2, 7).getUnePiece() instanceof Fou);
        assertTrue(this.plateau.getCase(3, 7).getUnePiece() instanceof Reine);
        assertTrue(this.plateau.getCase(4, 7).getUnePiece() instanceof Roi);
        assertTrue(this.plateau.getCase(5, 7).getUnePiece() instanceof Fou);
        assertTrue(this.plateau.getCase(6, 7).getUnePiece() instanceof Cavalier);
        assertTrue(this.plateau.getCase(7, 7).getUnePiece() instanceof Tour);
        
        // On essaye de récupérer une case n'ayant pas de pièce
        assertNull(this.plateau.getCase(5, 5).getUnePiece());
    }
    
    @Test
    public void testCouleur() {
        this.plateau.initBoarder();
        
        assertTrue(this.plateau.getCase(0, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(1, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(2, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(3, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(4, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(5, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(6, 0).getUnePiece().getCouleur() == 2);
        assertTrue(this.plateau.getCase(7, 0).getUnePiece().getCouleur() == 2);
        
        assertTrue(this.plateau.getCase(0, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(1, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(2, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(3, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(4, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(5, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(6, 7).getUnePiece().getCouleur() == 1);
        assertTrue(this.plateau.getCase(7, 7).getUnePiece().getCouleur() == 1);
    }
    
    @Test
    public void testGetPositionX() {
        this.plateau.initBoarder();
        
        assertEquals(0, this.plateau.getCase(0, 0).getPositionX());
        assertEquals(1, this.plateau.getCase(1, 0).getPositionX());
        assertEquals(2, this.plateau.getCase(2, 0).getPositionX());
        assertEquals(3, this.plateau.getCase(3, 0).getPositionX());
        assertEquals(4, this.plateau.getCase(4, 0).getPositionX());
        assertEquals(5, this.plateau.getCase(5, 0).getPositionX());
    }
    
    @Test
    public void testGetPositionY() {
        this.plateau.initBoarder();
        
        assertEquals(0, this.plateau.getCase(0, 0).getPositionY());
        assertEquals(1, this.plateau.getCase(0, 1).getPositionY());
        assertEquals(2, this.plateau.getCase(0, 2).getPositionY());
        assertEquals(3, this.plateau.getCase(0, 3).getPositionY());
        assertEquals(4, this.plateau.getCase(0, 4).getPositionY());
        assertEquals(5, this.plateau.getCase(0, 5).getPositionY());
    }
}
