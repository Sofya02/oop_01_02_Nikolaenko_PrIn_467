package goats;

import org.jetbrains.annotations.NotNull;
import goats.model.event.GameActionEvent;
import goats.model.event.GameActionListener;
import goats.model.Game;
import goats.model.GameStatus;
import goats.model.labirints.SmallLabirint;
import goats.ui.FieldWidget;
import goats.ui.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;
        private WidgetFactory widgetFactory;

        public GamePanel() throws HeadlessException {
            setVisible(true);
            startGame();
            setResizable(false);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(createGameMenu());
            setJMenuBar(menuBar);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private JMenu createGameMenu() {
            JMenu gameMenu = new JMenu("Игра");
            JMenuItem newGameMenuItem = new JMenuItem(new NewGameAction());
            JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
            gameMenu.add(newGameMenuItem);
            gameMenu.add(exitMenuItem);
            return gameMenu;
        }

        private void startGame() {
            widgetFactory = new WidgetFactory();
            game = new Game(new SmallLabirint());

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.removeAll();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));
           // widgetFactory.getWidget(game.getGoat()).requestFocus();

            pack();
        }

        private class NewGameAction extends AbstractAction {

            public NewGameAction() {
                putValue(NAME, "Новая");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(GamePanel.this,
                        "Начать новую игру?", "Новая игра",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) startGame();
            }
        }

        private static class ExitAction extends AbstractAction {

            public ExitAction() {
                putValue(NAME, "Выход");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        private final class GameController implements GameActionListener {

            @Override
            public void goatIsMoved(@NotNull GameActionEvent event) {

            }


            @Override
            public void goatIsTeleported(@NotNull GameActionEvent event) {

            }

            @Override
            public void gameStatusChanged(@NotNull GameActionEvent event) {
                GameStatus status = event.getStatus();
                if(status != GameStatus.GAME_IS_ON) {
                    String message = "";
                    switch (status) {
                        case WINNER_FOUND:
                            message = "Коза победила!!!";
                            break;
                        case GAME_ABORTED:
                            message= "Игра завершена досрочно";
                            break;
                        case GOAT_HAVE_LOW_CHARGE:
                            message = "У козы закончились шаги!";
                            break;
                    }
                    String[] options = {"ok"};
                    int value = JOptionPane.showOptionDialog(GamePanel.this, message, "Игра окончена", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if(value == 0 || value == 1) {
                        startGame();
                        GamePanel.this.repaint();
                    }
                }
            }
        }
    }
}
