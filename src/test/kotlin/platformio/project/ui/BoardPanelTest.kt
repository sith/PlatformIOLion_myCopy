package platformio.project.ui

import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.fixture.FrameFixture
import org.hamcrest.Matchers.contains
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import javax.swing.JFrame

@RunWith(MockitoJUnitRunner::class)
class BoardPanelTest {
    private val frame = JFrame()
    private val window = FrameFixture(GuiActionRunner.execute<JFrame> { frame })

    @Mock
    private lateinit var boardPanelRemovalListener: BoardPanel.BoardPanelRemovalListener

    private lateinit var boardPanel: BoardPanel

    @Before
    fun setUp() {
        boardPanel = BoardPanel(boardA, boardPanelRemovalListener)
        frame.add(boardPanel)
        window.show()
    }

    @After
    fun tearDown() {
        window.cleanUp()
    }

    @Test
    fun boardPanelDisplaysData() {
        window.label(BoardPanel.NAME_LABEL + boardA.id).requireText(boardA.name)
        window.label(BoardPanel.NAME_LABEL + boardA.id).requireText(boardA.name)
        window.button(BoardPanel.REMOVE_BUTTON + boardA.id)
        val frameworksList = window.list(BoardPanel.FRAMEWORKS_LIST_NAME + boardA.id)
        frameworksList.requireItemCount(boardA.frameworks.size)
        frameworksList.item(boardA.frameworks[0])
        frameworksList.item(boardA.frameworks[1])
    }

    @Test
    fun selectFramework() {
        val frameworksList = window.list(BoardPanel.FRAMEWORKS_LIST_NAME + boardA.id)
        val expectedFramework = boardA.frameworks[1]
        frameworksList.item(expectedFramework).click()
        assertThat(boardPanel.selectedFrameworks, contains(expectedFramework))
    }

    @Test
    fun removeButtonClicked() {
        window.button(BoardPanel.REMOVE_BUTTON + boardA.id).click()
        verify(boardPanelRemovalListener).onRemove(boardPanel)
    }
}