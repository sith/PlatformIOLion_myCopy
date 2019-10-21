package platformio.project.ui;

import org.jetbrains.annotations.NotNull;
import platformio.services.Board;
import platformio.services.BoardService;
import platformio.services.Framework;
import platformio.services.FrameworkService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPIOProjectSettingsForm {
    public static final String SELECT_BOARD_BUTTON_NAME = "selectBoardButton";
    private final BoardService boardService;
    private final FrameworkService frameworkService;
    private JPanel mainPanel;
    private JButton selectBoardButton;

    public NewPIOProjectSettingsForm(BoardService boardService, FrameworkService frameworkService) {
        this.boardService = boardService;
        this.frameworkService = frameworkService;
        selectBoardButton.addActionListener(e -> {
            BoardCatalogDialog boardCatalogDialog = new BoardCatalogDialog(boardService.loadAllBoards());
            boardCatalogDialog.setVisible(true);
        });
    }

    public Framework getFramework() {
        throw new UnsupportedOperationException();
    }

    public Board getBoard() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public JComponent getComponent() {
        return mainPanel;
    }

    private void createUIComponents() {
        selectBoardButton = new JButton();
        selectBoardButton.setName(SELECT_BOARD_BUTTON_NAME);
    }
}
