package platformio.project;

import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.NotNull;
import platformio.services.Board;
import platformio.services.BoardService;
import platformio.services.Framework;
import platformio.services.FrameworkService;

import javax.swing.*;
import java.util.Collection;

public class NewPIOProjectSettingsForm {
    public static final String BOARDS = "boards";
    public static final String FRAMEWORKS = "frameworks";
    public static final String NO_FRAMEWORKS_FOUND_LABEL = "No Frameworks found";
    public static final String NO_BOARDS_FOUND_LABEL = "No Boards found";
    private final BoardService boardService;
    private final FrameworkService frameworkService;
    private JPanel mainPanel;
    private JComboBox<Board> boardList;
    private JComboBox<Framework> frameworkList;
    private DefaultComboBoxModel<Board> boardListModel;
    private DefaultComboBoxModel<Framework> frameworkDefaultComboBoxModel;

    public NewPIOProjectSettingsForm(BoardService boardService, FrameworkService frameworkService) {
        this.boardService = boardService;
        this.frameworkService = frameworkService;
    }

    public Framework getFramework() {
        return frameworkList.getItemAt(frameworkList.getSelectedIndex());
    }

    public Board getBoard() {
        return boardList.getItemAt(boardList.getSelectedIndex());
    }

    @NotNull
    public JComponent getComponent() {
        return mainPanel;
    }

    private void createUIComponents() {
        initBoardList();
        initFrameworkList();
    }

    private void initFrameworkList() {
        frameworkDefaultComboBoxModel = new DefaultComboBoxModel<>();
        frameworkService.loadAllFrameworks().forEach(framework -> frameworkDefaultComboBoxModel.addElement(framework));
        frameworkList = new ComboBox<>(frameworkDefaultComboBoxModel);
        frameworkList.setName(FRAMEWORKS);
        frameworkList.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value != null ? value.getName() : NO_FRAMEWORKS_FOUND_LABEL));
    }

    private void initBoardList() {
        boardListModel = new DefaultComboBoxModel<>();
        final Collection<Board> boards = boardService.loadAllBoards();
        boards.forEach(board -> boardListModel.addElement(board));
        boardList = new ComboBox<>(boardListModel);
        boardList.setName(BOARDS);
        boardList.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value != null ? value.getName() : NO_BOARDS_FOUND_LABEL));
    }
}
