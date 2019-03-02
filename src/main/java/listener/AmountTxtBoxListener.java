package listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountTxtBoxListener implements DocumentListener {

    private Logger LOG = LoggerFactory.getLogger(AmountTxtBoxListener.class);

    private MainFrame frame;

    public AmountTxtBoxListener(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String userInput = "";
        try {
            userInput = e.getDocument().getText(0, e.getDocument().getLength());
        } catch (BadLocationException e1) {
            LOG.error("AmountTxtBoxListener:insertUpdate", e1);
        }

        if (isNumeric(userInput) && Integer.valueOf(userInput) >= 1 && Integer.valueOf(userInput) <= 100) { // between 1 and 100
            frame.setValidAmountInput(true);
            frame.getPanelBulkMaps().getUpdateButton().setEnabled(true);
        } else {
            frame.setValidAmountInput(false);
            frame.getPanelBulkMaps().getUpdateButton().setEnabled(false);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

}
