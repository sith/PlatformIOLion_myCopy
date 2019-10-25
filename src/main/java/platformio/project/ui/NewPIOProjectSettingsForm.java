package platformio.project.ui;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import platformio.services.Board;
import platformio.services.BoardService;
import platformio.services.Framework;
import platformio.services.FrameworkService;

import javax.swing.*;

public class NewPIOProjectSettingsForm {
    public static final String SELECT_BOARD_BUTTON_NAME = "selectBoardButton";
    public static final String SELECTED_BOARD_TABLE_NAME = "selectedBoardTable";
    private final BoardService boardService;
    private final FrameworkService frameworkService;
    private JPanel mainPanel;
    private JButton selectBoardButton;
    private JScrollPane selectedBoardsScrollPane;
    private DefaultListModel<Board> selectedBoardListModel;

    public NewPIOProjectSettingsForm(BoardService boardService, FrameworkService frameworkService) {
        this.boardService = boardService;
        this.frameworkService = frameworkService;
        selectBoardButton.addActionListener(e -> {
            BoardCatalogDialog boardCatalogDialog = new BoardCatalogDialog(boardService.loadAllBoards());
            boardCatalogDialog.setVisible(true);

            selectedBoardsScrollPane.add(new BoardTable(boardCatalogDialog.getSelectedBoards()).getTable());
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

        selectedBoardListModel = new DefaultListModel<>();
        final JBList<Board> selectedBoardList = new JBList<>(selectedBoardListModel);
        selectedBoardList.setName(SELECTED_BOARD_TABLE_NAME);
        selectedBoardList.setCellRenderer(new BoardPanelListCellRenderer(boardPanel -> selectedBoardListModel.removeElement(boardPanel)));
        selectedBoardsScrollPane = new JBScrollPane(selectedBoardList);
    }
}
