package ies.trabbit

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import ies.trabbit.framework.KeyBoard

data class Point(var x: Float, var y: Float)
class Game {
    private val terminal: Terminal = DefaultTerminalFactory().createTerminal()
    private val screen: Screen = TerminalScreen(terminal)
    private val keyBoard: KeyBoard = KeyBoard()
    private var p:Point= Point(5f,5f)
    companion object {
        var frecuency: Int = 2
        const val BASE: Int = 19
        var BACKGROUND: TextColor = TextColor.Factory.fromString("#000000")
    }

    private fun paint(s: Screen) {
        this.clearScreen(s)
        screen.setCharacter(
            this.p.x.toInt(),
           this.p.y.toInt(),
            TextCharacter(
                '◎', TextColor.Factory.fromString("#00FF00"),
                 Game.BACKGROUND
            )
        )
      
        screen.refresh()
    }

    private fun clear_keyboard_input() {
        var keyStroke: KeyStroke?
        do {
            keyStroke = screen.pollInput()
        } while (keyStroke != null)
    }
    private fun clearScreen(screen: Screen){
        val terminalSize = screen.terminalSize
        for (column in 0 until terminalSize.columns) {
            for (row in 0 until terminalSize.rows) {
                screen.setCharacter(
                    column, row, TextCharacter(
                        ' ',
                        TextColor.ANSI.DEFAULT,
                        BACKGROUND
                    )
                )
            }
        }
    }
    private fun process_input() {
         //la lectura es no bloqueante
        val keyStroke: KeyStroke? = screen.pollInput()
        if (keyStroke != null) {

            this.keyBoard.onKeyPressed(keyStroke)

            //se borra el buffer
            this.clear_keyboard_input()
        }

    }

    private fun update() {
        val inc:Float=0.01f;
            if(this.keyBoard.getKeyValue("key_left_pressed")>0)
                this.p.x -= inc;
        if(this.keyBoard.getKeyValue("key_right_pressed")>0)
            this.p.x += inc;
        if(this.keyBoard.getKeyValue("key_up_pressed")>0)
            this.p.y -= inc;
        if(this.keyBoard.getKeyValue("key_down_pressed")>0)
            this.p.y += inc;
    }

    fun loop() {

        screen.startScreen()
        screen.clear()
        terminal.setBackgroundColor(TextColor.ANSI.CYAN)

        while (this.keyBoard.getKeyValue("key_exit") == 0) {

            /* //se procesa la entrada*/
            this.process_input()
            //se actualiza el juego
             this.update()
             //se pinta*/
             this.paint(this.screen)

            //1000 es un segundo, frecuenca de 10 Hz son 10 veces por segundo
            //frecuenca de 20 Hz son 20 veces por segundo, una vez cada 0,05 segundos
            Thread.sleep(((1 / frecuency) * 1000).toLong())

        }
        //fin del bucle
        screen.stopScreen()

    }
}