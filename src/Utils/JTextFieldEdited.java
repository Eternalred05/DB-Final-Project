package Utils;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JTextFieldEdited extends JTextField {

    private boolean numbers = true;
    private int limit = -1;

    public JTextFieldEdited() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();
                char c = e.getKeyChar();

                if (text.getText().length() == limit || (!isNumbers() && Character.isLetter(c))) {
                    e.consume();
                }
            }
        });
    }

    public boolean isNumbers() {
        return numbers;
    }

    public void setNumbers(boolean numbers) {
        this.numbers = numbers;
    }

    public int getLimite() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= -1) {
            this.limit = limit;
        }
    }
}
