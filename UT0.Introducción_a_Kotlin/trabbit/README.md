# Práctica final introducción a Kotlin

![TRabbit Game](https://github.com/pass1enator/TRabbit/blob/master/images/trrabit_play.png)

La práctica consiste en a partir del código que se facilita, crear un juego similar a [T-Rex](https://fivesjs.skipser.com/trex-game/) que aparece en Google Chrome cuando existe problema en la conexión de red.
# Práctica final.

## Enunciado.
A partir del código que se proporciona, implementar las clases siguientes clases:

Background.
* Representa el fondo del juego, se moverá a medida que el juego avance, en el caso de llegar al final, vuelve a comenzar. El fondo se representa con una matriz de caracteres.
Cactus.
* Representa un cactus en el juego, hereda de GameElement, se representa con una matriz de caracteres, cada captus puede tener un color
TRabbit.
* El conejo que se va movie por la pantalla (realmente no se mueve, sino que se desplaza tanto el fondo como los captus de la pantalla).
* Salta y se colisiona con los captus.
* Tiene n vidas.
* Hereda de GameElement.
GameElement.
* Clase abstracta, posee una coordenada (x,y), un tamaño (W,h) y un dibujo representado por una matriz de caracteres.

Se ha de definir además:

Scene.
* Clase abstracta que representa una escena, posee un estado(PLAY, DEAD, END....).
* Al menos los métodos abstractos update,paint y processInput.
Screen.
* Hereda de Scene, sirve como pantalla de introducción al juego, de Game Over, instrucciones...
* Se le pasa una matriz de caracteres a pintar.
Level.
* Hereda de Scene.
* Es el juego propiamente dicho.
* Se le pasa el TRabbit del juego, un vector de posiciones de los captus y un objeto de tipo fondo.
* Desarrolla el juego
## Funcionamiento.

El juego posee:
* Una lista de Scene, en la que se encuentran Screen introducción, un conjunto de niveles, Screen de fin.
* Un Screen de GameOver.
* Un TRabbit que se pasa a los niveles del juego.
* Un Screen actual, que irá cambiado al siguiente (se pusa una tecla, se finaliza un nivel, se terminan las vidas...)
* Un bucle principal que va llamando a los 3 métodos clásicos de un juego:
    - Procesamiento entrada.
    - Actualización juego.
    - Pintar juego

``` kotlin
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
  private fun process_input() {
         //la lectura es no bloqueante
        val keyStroke: KeyStroke? = screen.pollInput()
        if (keyStroke != null) {

            this.keyBoard.onKeyPressed(keyStroke)
            this.scenes[this.currentscene].processInput(this.keyBoard)
          
            //se borra el buffer
            this.clear_keyboard_input()
        }

    }
 private fun update() {
        this.scenes[this.currentscene].update()
        if(this.scenes[this.currentscene].isEnd )
            //si ha finalizado el juego se va la última escena
            if(this.scenes[this.currentscene].state==Scene.STATE.GAME_OVER){
                this.currentscene=this.scenes.size-1
            }
            //se pasa a la siguiente escena
            else if(this.currentscene<this.scenes.size-1) {
                this.currentscene++
                if (this.scenes[this.currentscene] is Level) {
                    (this.scenes[this.currentscene] as Level).reset()
                }
            }else{
                //se reinicia el juego
                this.currentscene=0
                this.trex.hardreset()
                this.scenes.forEach{
                    it.reset()
                }
        }
    }
   private fun paint(s: Screen) {
        this.clearScreen(s)
        this.scenes[this.currentscene].paint(s)
      
        screen.refresh()
    }
```
En el objeto teclado se puede consultar que teclas se encuentran pulsada o no (solo se han definido las importantes para el juego)
``` Kotlin
      keyBoard["key_left_pressed"] = 0
        keyBoard["key_right_pressed"] = 0
        keyBoard["key_exit"] = 0
        keyBoard["key_jump"] = 0
        keyBoard["space"]=0
        keyBoard["start"]=0
```
**La tecla de inicio es la s, para saltar es la tecla espacio**

Un posible diagrama UML (No es exacto, la herramienta utilizada no refleja la relación entre Game y lista de niveles, por ejemplo).

![UML TRabbit](https://github.com/pass1enator/TRabbit/blob/master/images/umlTRabbit.png)



