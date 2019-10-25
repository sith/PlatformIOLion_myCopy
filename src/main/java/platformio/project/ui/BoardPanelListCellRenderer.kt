package platformio.project.ui

import platformio.services.Board
import java.awt.Component
import javax.swing.JList
import javax.swing.ListCellRenderer

class BoardPanelListCellRenderer(private val boardPanelRemovalListener: BoardPanel.BoardPanelRemovalListener) : ListCellRenderer<Board> {
    override fun getListCellRendererComponent(list: JList<out Board>, value: Board?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        val boardPanel = BoardPanel(value, boardPanelRemovalListener)
        boardPanel.isEnabled = list.isEnabled
        boardPanel.font = list.font
        boardPanel.background = list.background
        boardPanel.foreground = list.foreground
        return boardPanel
    }
}
