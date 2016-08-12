
/**
 * 
 * Die GameOver-Seite wird vom GameBoardController aufgerufen, sobald dieser via „onGameFinished()“ vom Server informiert wird.
 * Die Seite zeigt den Sieger des Spieles an und ermöglicht es, das Ranking des soeben beende-ten Spieles oder das Gesamtranking zu betrachten. 
 * Diese Benutzerinteraktion wird vom GameOverController entgegengenommen und entsprechend behandelt. 
 * Der Controller implementiert das Interface IClientListener und registriert das GUI, um das aktuellste Ranking vom Server erhalten zu können.
 * Im GameOverModel wird das erhaltene Ranking und der Sieger gespeichert.
 * 
 * @author Michelle
 *
 */
package teamamused.client.gui.gameOver;