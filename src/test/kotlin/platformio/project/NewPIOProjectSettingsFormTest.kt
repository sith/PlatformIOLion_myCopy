package platformio.project

import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.fixture.FrameFixture
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import platformio.services.Board
import platformio.services.BoardService
import platformio.services.Framework
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

    private val boardA = Board("A")
    private val boardB = Board("B")

    private val frameworkA = Framework("A")
    private val frameworkB = Framework("B")

    @Before
    fun setUp() {
        `when`(boardService.loadAllBoards()).thenReturn(listOf(boardA, boardB))
        `when`(frameworkService.loadAllFrameworks()).thenReturn(listOf(frameworkA, frameworkB))
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

        window.comboBox(NewPIOProjectSettingsForm.BOARDS).selectItem(boardA.name);
        window.comboBox(NewPIOProjectSettingsForm.FRAMEWORKS).selectItem(frameworkB.name);

        assertThat(form.board, `is`(boardA))
        assertThat(form.framework, `is`(frameworkB))
    }

    @Test
    fun returnsNullIfNoBoardsLoaded() {
        `when`(boardService.loadAllBoards()).thenReturn(emptyList())
        showWindow()
        assertThat(form.board, nullValue())
    }

    @Test
    fun returnsNullIfFrameworkNotSelected() {
        `when`(frameworkService.loadAllFrameworks()).thenReturn(emptyList())
        showWindow()
        assertThat(form.framework, nullValue())
    }
}