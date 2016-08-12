
/**
 * 
 * Das CardPopup wird aufgerufen, sobald ein Spieler im GameBoard auf einen Spieler-Button klickt. 
 * Das CardPopup zeigt die Karten des Spielers, auf wessen Button geklickt wurde. So kann man sich jederzeit darüber informieren, 
 * welcher Spieler über welche Karten verfügt.
 * 
 * Wird auf eine Karte geklickt, erscheint das Kartenbild vergrössert, so dass auch der Text zu den Bildern gelesen werden kann.
 * 
 * Da es nicht notwendig war, für das CardPopup eine eigene Datenhaltung zu erstellen und da dieses sowieso auf dem GameBoard basiert, 
 * hat es sich angeboten, das Model des Game-Boards auch für das CardPopup zu verwenden. 
 * Das CardPopup greift via GameBoardModel lediglich auf den Player und dessen Karten zu.
 * 
 * @author Michelle
 *
 */
package teamamused.client.gui.bye;