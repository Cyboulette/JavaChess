package tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javachess.*;
import pieces.*;

public class DeplacementsTest {
    
    private Model modele;
    
    public DeplacementsTest() {
        this.modele = new Model();
    }
    
    @Test
    public void testMovePion() {
        Piece pion = this.modele.getPlateau().getCase(0, 1).getUnePiece();
        // Le joueur noir ne peut pas aller en arrière (0;0)
        assertFalse(pion.canPlay(this.modele.getPlateau().getCase(0, 0), 2));
        // Mais il peut aller en avant de 1 ou de 2
        assertTrue(pion.canPlay(this.modele.getPlateau().getCase(0, 2), 2));
        assertTrue(pion.canPlay(this.modele.getPlateau().getCase(0, 3), 2));
        
        // Mais pas ou il veut
        assertFalse(pion.canPlay(this.modele.getPlateau().getCase(5, 5), 2));
    }
    
    @Test
    public void testMange() {
        Piece pionNoir = this.modele.getPlateau().getCase(0, 1).getUnePiece();
        Piece pionBlanc = this.modele.getPlateau().getCase(1, 6).getUnePiece();
        
        // On force le déplacement (sans vérifier le canPlay)
        this.modele.play(pionNoir, this.modele.getPlateau().getCase(0, 3));
        this.modele.play(pionBlanc, this.modele.getPlateau().getCase(1, 4));
        
        // Il peut manger le pion à gauche diagonale
        assertTrue(pionBlanc.canPlay(this.modele.getPlateau().getCase(0, 3), 1));
        
        // Il peut aller tout droit s'il le veut
        assertTrue(pionBlanc.canPlay(this.modele.getPlateau().getCase(1, 3), 1));
    }
    
    @Test
    public void testDeplacements() {
        Piece pionBlanc = this.modele.getPlateau().getCase(1, 6).getUnePiece();
        
        // Il peut faire 2 déplacements ici, car c'est son premier move
        assertEquals(2, pionBlanc.getDeplacements(1).size());
        
        // On ne se déplace pas car on se déplace sur nous même, donc 0 déplacements possibles à ce niveau là
        this.modele.play(pionBlanc, this.modele.getPlateau().getCase(1, 6));
        assertEquals(0, pionBlanc.getDeplacements(1).size());
        
        // Si on se déplace, on ne peut faire plus qu'un déplacement sauf si on peut manger ou roquer, mais on ne peut plus aller 2 en haut
        this.modele.play(pionBlanc, this.modele.getPlateau().getCase(1, 5));
        assertEquals(1, pionBlanc.getDeplacements(1).size());
    }
    
    @Test 
    public void testEstEnEchec() {
        // On récupère le roi noir
        Roi roiNoir = (Roi) this.modele.getPlateau().getCase(4, 0).getUnePiece();
        Reine reineBlanche = (Reine) this.modele.getPlateau().getCase(3, 7).getUnePiece();
        
        // On FORCE le déplacement (sans aucune vérification) de la reine blanche sur la case vide : (7;3)
        this.modele.play(reineBlanche, this.modele.getPlateau().getCase(7, 3));
        // Le roi noir n'est pas en echec (après déplacement d'un blanc ou d'un noir)
        assertFalse(roiNoir.estEnEchec(1) || roiNoir.estEnEchec(2));
        
        // On FORCE le déplacement (sans aucune vérification) du pion noir (5;1) en (5;3)
        this.modele.play(this.modele.getPlateau().getCase(5, 1).getUnePiece(), this.modele.getPlateau().getCase(5, 3));
        // La reine blanche est en diagonale de notre roi noir et il n'est plus protégé par la pièce, il est donc en échec
        assertTrue(roiNoir.estEnEchec(1) || roiNoir.estEnEchec(2));
    }
    
}
