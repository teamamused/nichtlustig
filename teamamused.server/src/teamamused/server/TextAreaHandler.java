package teamamused.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import teamamused.common.ServiceLocator;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * A log-handler that writes to a TextArea. Platform.runLater is a means
 * of putting work onto the JavaFX application thread. Anything that
 * modifies with the GUI should be on this thread. In this case, the
 * critical line is textArea.setText(...).
 * 
 * Adapted from:
 * http://stackoverflow.com/questions/10785560/write-logger-message-to-file-and-textarea-while-maintaining-default-behaviour-in
 */
public class TextAreaHandler extends Handler {

	private static TextAreaHandler textAreaHandler;
    private TextArea textArea = new TextArea();

    public static TextAreaHandler getInstance() {
    	if (textAreaHandler == null) {
            // Logger intialisieren
            textAreaHandler = new TextAreaHandler();
            textAreaHandler.setLevel(Level.INFO);
            ServiceLocator.getInstance().getLogger().addHandler(textAreaHandler);
    	}
    	return textAreaHandler;
    }
    
    private TextAreaHandler() {
    	super();
    }
    
    @Override
    public void publish(LogRecord record) {
        Platform.runLater(new Runnable() {
            @Override public void run() {        
                StringWriter text = new StringWriter();
                PrintWriter out = new PrintWriter(text);
                out.println(textArea.getText());
                out.printf("[%s] [Thread-%d]: %s.%s -> %s", record.getLevel(),
                        record.getThreadID(), record.getSourceClassName(),
                        record.getSourceMethodName(), record.getMessage());
                textArea.setText(text.toString());
            }});
    }

    @Override
    public void flush() {
        // nothing to do here
    }

    @Override
    public void close() throws SecurityException {
        // nothing to do here
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
