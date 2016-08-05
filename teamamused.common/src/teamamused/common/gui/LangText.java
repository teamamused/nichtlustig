package teamamused.common.gui;

/**
 * Enumeration mit den Sprachresourcen
 * Um nicht überall die Schreibfehler anfälligen Einstellungs konstanten verwenden zu müssen, wurde diese Enumeratione eingefügt.
 * Sie dient als Verzeichnis sämtlicher im Programm vorhanden Sprachtexte
 * 
 * @author Daniel
 */
public enum LangText {
	
	// Generell gültige Texte
	Sprache("sprache"),
	
	// LogIn View
	LogInTitel ("login.titel"),
	LogInCopyright ("login.labelCopyright"),
	LogInConnect ("login.labelConnect"),
	LogInServer ("login.labelServer"),
	LogInPort ("login.labelPort"),
	LogInUser ("login.labelUser"),
	LogInPassword ("login.labelPassword"),
	LogInNeu ("login.labelNeu"),
	LogInButtonServer ("login.btnConnectServer"),
	
	// Register View
	RegisterTitel ("register.titel"),
	RegisterHello ("register.labelRegister"),
	RegisterCopyright ("register.labelCopyright"),
	RegisterHere ("register.labelRegisterHere"),
	RegisterUser ("register.labelRegUser"),
	RegisterPassword ("register.labelRegPassword"),
	RegisterPassword2 ("register.labelRegPassword2"),
	
	// Welcome View
	WelcomeTitel ("welcome.titel"),
	WelcomeText ("welcome.labelWelcome"),
	WelcomePlay ("welcome.labelPlay"),
	WelcomeFun ("welcome.labelFun"),
	
	// Ranking View
	RankingTitel ("ranking.titel"),
	RankingText ("ranking.labelRanking"),
	
	// Bye View
	ByeTitel ("bye.titel"),
	ByeText ("bye.labelTschuess"),
	
	// GameBoard View
	GameBoardTitle ("gameBoard.title"),
	GameBoardSpielfeld ("gameBoard.labelSpielfeld"),
	GameBoardBtnSenden ("gameBoard.btnSenden"),
	GameBoardChatTooltip ("gameBoard.chatInputTool"),
	GameBoardSelectedDices ("gameBoard.labelSelectedDices"),
	GameBoardBtnWuerfeln ("gameBoard.btnWuerfeln"),
	GameBoardRollDices ("gameBoard.labelRollDices"),
	
	// CardPopup View
	CardPopupTitle ("cardPopup.title"),
	CardPopupCardsOf ("gameBoard.labelTitle"),
	CardPopupValued ("gameBoard.labelText"),
	CardPopupTarget ("gameBoard.cardsRival"),
	CardPopupNoTarget ("gameBoard.noCardsRival"),
	CardPopupSpecial("gameBoard.specialCardsRival"),
	CardPopupNoSpecial ("gameBoard.noSpecialCardsRival"),
	CardPopupDeath ("gameBoard.deathCardsRival"),
	CardPopupNoDeath ("gameBoard.noDeathCardsRival"),
	CardPopupBtnClose ("gameBoard.btnClose"),
	
	// ChooseCards View
	ChooseCardsTitle ("chooseCards.titleLabel"),
	ChooseCardsExplain ("chooseCards.explainLabel"),
	ChooseCardsChoice ("chooseCards.choiceLabel"),
	
	// Game Over View
	GameOverTitle ("gameOver.title"),
	GameOverTxt ("gameOver.labelTxt"),
	GameOverWinner ("gameOver.labelWinner"),
	GameOverBtnStart("gameOver.btnNewStart"),
	GameOverBtnClose ("gameOver.btnClose"),
	
	// MovingPopup View
	MovingPopupTitle ("movingPopup.title"),
	MovingPopupQueue ("movingPopup.labelQueue"),
	MovingPopupDice ("movingPopup.labelDice"),
	MovingPopupCards ("movingPopup.labelCards"),
	MovingPopupNext ("movingPopup.labelNext");
	
	String resourceName;
	
	LangText(String rn) {
		this.resourceName = rn;
	}
	
	public String toString(){
		return this.resourceName;
	}
}