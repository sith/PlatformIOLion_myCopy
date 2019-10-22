package platformio.project.ui

import platformio.services.Board
import java.awt.Component
import javax.swing.JList
import javax.swing.ListCellRenderer

class BoardPanelListCellRenderer(private val boardPanelRemovalListener: BoardPanel.BoardPanelRemovalListener) : ListCellRenderer<Board> {
    override fun getListCellRendererComponent(list: JList<out Board>?, value: Board?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        return BoardPanel(value, boardPanelRemovalListener)
    }
}
