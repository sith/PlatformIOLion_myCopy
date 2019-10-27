package platformio.project.ui;

import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import platformio.services.Board;
import platformio.services.BoardService;
import platformio.services.Framework;
import platformio.services.FrameworkService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewPIOProjectSettingsForm {
    public static final String SELECT_BOARD_BUTTON_NAME = "selectBoardButton";
    public static final String SELECTED_BOARD_TABLE_NAME = "selectedBoardTable";
    public static final String NAME_COLUMN = "Name";
    public static final String PLATFORM_COLUMN = "Platform";
    public static final String FRAMEWORK_COLUMN = "Framework";
    private final BoardService boardService;
    private final FrameworkService frameworkService;
    private JPanel mainPanel;
    private JButton selectBoardButton;
    private JScrollPane selectedBoardsScrollPane;
    private DefaultTableModel selectedBoardsTableModel;
    private Set<Board> selectedBoards = new HashSet<>();

    public NewPIOProjectSettingsForm(BoardService boardService, FrameworkService frameworkService) {
        this.boardService = boardService;
        this.frameworkService = frameworkService;
        selectBoardButton.addActionListener(e -> {
            BoardCatalogDialog boardCatalogDialog = new BoardCatalogDialog(boardService.loadAllBoards(), selectedBoards);
            boardCatalogDialog.setVisible(true);

            List<Board> selectedBoards = boardCatalogDialog.getSelectedBoards();
            this.selectedBoards = new HashSet<>(selectedBoards);
            clearTable();
            selectedBoards.forEach(board -> selectedBoardsTableModel.addRow(
                    new Object[]{
                            board.getName(),
                            board.getPlatform(),
                            board.getFramework()
                    }));
        });
    }

    private void clearTable() {
        for (int i = 0; i < selectedBoardsTableModel.getRowCount(); i++) {
            selectedBoardsTableModel.removeRow(i);

        }
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

        selectedBoardsTableModel = new DefaultTableModel();

        addColumns();

        JTable selectedBoardTable = new JTable(selectedBoardsTableModel);
        selectedBoardTable.setName(SELECTED_BOARD_TABLE_NAME);
        selectedBoardsScrollPane = new JBScrollPane(selectedBoardTable);
    }

    private void addColumns() {
        selectedBoardsTableModel.addColumn(NAME_COLUMN);
        selectedBoardsTableModel.addColumn(PLATFORM_COLUMN);
        selectedBoardsTableModel.addColumn(FRAMEWORK_COLUMN);
    }
}
