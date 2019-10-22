package platformio.project.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import platformio.services.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class BoardPanel extends JPanel {
    public static final String NAME_LABEL = "nameLabel_";
    public static final String PLATFORM_LABEL = "platformLabel_";
    public static final String REMOVE_BUTTON = "removeButton_";
    public static final String FRAMEWORKS_LIST_NAME = "frameworks_";
    private final Board board;
    private final BoardPanelRemovalListener boardPanelRemovalListener;
    private JLabel nameLabel;
    private JLabel platfromLabel;
    private JPanel mainPanel;
    private JButton removeButton;
    private JScrollPane frameworksScrollPane;
    private JBList<CheckboxListItem> frameworksList;
    private CheckboxListItemListModel checkboxListItemListModel;

    public BoardPanel(Board board, BoardPanelRemovalListener boardPanelRemovalListener) {
        this.board = board;
        this.boardPanelRemovalListener = boardPanelRemovalListener;

        nameLabel.setName(NAME_LABEL + board.getId());
        nameLabel.setText(board.getName());
        platfromLabel.setName(PLATFORM_LABEL + board.getId());
        platfromLabel.setText(board.getPlatform());

        add(mainPanel);
    }

    private void createUIComponents() {
        removeButton = new JButton("");
        removeButton.setName(REMOVE_BUTTON + board.getId());
        removeButton.setIcon(AllIcons.Actions.Cancel);
        removeButton.addActionListener(e -> boardPanelRemovalListener.onRemove(this));
        checkboxListItemListModel = new CheckboxListItemListModel(board.getFrameworks().stream().map(CheckboxListItem::new).collect(Collectors.toList()));
        frameworksList = new JBList<>(checkboxListItemListModel);
        frameworksList.setName(FRAMEWORKS_LIST_NAME + board.getId());
        frameworksList.setCellRenderer(new CheckboxListRenderer());
        frameworksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frameworksList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList<CheckboxListItem> list = (JList<CheckboxListItem>) event.getSource();
                int index = list.locationToIndex(event.getPoint());
                CheckboxListItem item = list.getModel().getElementAt(index);
                item.setSelected(!item.getSelected());
                list.repaint(list.getCellBounds(index, index));
            }
        });
        frameworksList.setVisibleRowCount(3);
        frameworksScrollPane = new JBScrollPane(frameworksList);
    }

    public List<String> getSelectedFrameworks() {
        return checkboxListItemListModel.getSelectedItems();
    }

    public interface BoardPanelRemovalListener extends ActionListener {
        void onRemove(BoardPanel boardPanel);
    }

    static class CheckboxListRenderer extends JCheckBox implements
            ListCellRenderer<CheckboxListItem> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends CheckboxListItem> list, CheckboxListItem value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(list.isEnabled());
            setSelected(value.getSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

}
