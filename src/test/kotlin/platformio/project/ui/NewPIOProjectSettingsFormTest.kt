package platformio.project.ui

import org.assertj.swing.data.TableCell
import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.fixture.DialogFixture
import org.assertj.swing.fixture.FrameFixture
import org.assertj.swing.timing.Timeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import platformio.project.ui.BoardCatalogDialog.BOARD_CATALOG_DIALOG_NAME
import platformio.project.ui.NewPIOProjectSettingsForm.SELECT_BOARD_BUTTON_NAME
import platformio.services.Board
import platformio.services.BoardService
import platformio.services.FrameworkService
import javax.swing.JFrame

@RunWith(MockitoJUnitRunner::class)
class NewPIOProjectSettingsFormTest {

    @Mock
    private lateinit var frameworkService: FrameworkService
    @Mock
    private lateinit var boardService: BoardService
    @Mock
    private lateinit var form: NewPIOProjectSettingsForm

    private val frame = JFrame()
    private val window = FrameFixture(GuiActionRunner.execute<JFrame> { frame })

    private val boards = listOf(boardA, boardB, boardC)

    @Before
    fun setUp() {
        `when`(boardService.loadAllBoards()).thenReturn(boards)
    }

    private fun showWindow() {
        form = NewPIOProjectSettingsForm(boardService, frameworkService)
        frame.add(form.component)
        window.show()
    }

    @After
    fun tearDown() {
        window.cleanUp()
    }

    @Test
    fun picksBoardAndFramework() {
        showWindow()
        window.button(SELECT_BOARD_BUTTON_NAME).click()

        val dialog = window.dialog(BOARD_CATALOG_DIALOG_NAME, Timeout.timeout(500))

        dialog.requireModal()
        dialog.selectBoard(boardA)
        dialog.selectBoard(boardC)

        dialog.button(BoardCatalogDialog.OK_BUTTON_NAME).click()

        window.panel("board_" + boardA.id)

        window.panel("board_" + boardB.id)

    }

    private fun DialogFixture.selectBoard(rowIndex: Board) {
        table(BoardsTableModel.BOARD_TABLE_NAME).cell(TableCell.row(boards.indexOf(rowIndex)).column(0)).click()
    }
}