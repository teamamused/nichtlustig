
/**
 * 
 * In den MVC-Klassen zum GameBoard sind die grafische Oberfläche und die GUI-Logik des Spielfeldes zu finden.
 * Das Spielfeld ist das Herzstück des Spieles. Es beinhaltet die Darstellung der Spielkarten (Ziel-, Spezial- und Todeskarten), der Spieler-Buttons, 
 * der Würfelfunktionalität, des Chats und der getätigten Spielzüge.
 * 
 * Sobald das Spiel gestartet wurde, wird das Spielfeld laufend durch vom Server erhaltene Events, welche vom Controller entgegengenommen werden, aktualisiert. 
 * Dazu implementiert die Klasse GameBoardController das Interface IClientListener und registriert das GUI bei diesem. 
 * So werden je nach Anzahl Spieler die Spieler-Buttons spezifisch für jeden Spieler gezeichnet.
 * Auch die Karten werden aktualisiert, sobald sich ein Spieler entsprechende Karten „erwürfelt“ hat.
 * 
 * Dem aktiven Spieler, farblich hervorgehoben, wird angezeigt, wie oft er noch würfeln darf. 
 * Die Gegenspieler sehen jeweils, was der aktive Spieler gewürfelt hat und können so das Spiel nachvollziehen. 
 * Ebenfalls können sie die Logeinträge zu den Spielzügen in der dafür vorgesehenen TextArea betrachten.
 * 
 * Im GameBoardModel ist die Datenhaltung für das GameBoard definiert. So findet sich beispielweise eine Instanz der IPlayer-Klasse im Model.
 * 
 * @author Michelle
 *
 */
package teamamused.client.gui.gameboard;