package ies.trabbit.framework

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType


class KeyBoard {
    private var keyBoard = mutableMapOf<String,Int>()
    //private var last:KeyStroke?=null
    init{
        keyBoard["key_left_pressed"] = 0
        keyBoard["key_right_pressed"] = 0
        keyBoard["key_exit"] = 0
        keyBoard["key_jump"] = 0
        keyBoard["key_up_pressed"] = 0
        keyBoard["key_down_pressed"] = 0

        keyBoard["space"]=0
        keyBoard["start"]=0
    }
    fun reset(){
        keyBoard.forEach{
            keyBoard[it.key] = 0
        }
    }
    fun onKeyPressed(key: KeyStroke){

            this.reset()
        if (key.getKeyType() === KeyType.Escape) {
            this.keyBoard["key_exit"] = this.keyBoard["key_exit"]!! + 1
        } else if (key.getKeyType() === KeyType.ArrowLeft) {
            this.keyBoard["key_left_pressed"] = this.keyBoard["key_left_pressed"]!! + 1
        } else if (key.getKeyType() === KeyType.ArrowRight) {
            this.keyBoard["key_right_pressed"] = this.keyBoard["key_right_pressed"]!! + 1
        } else if (key.getKeyType() === KeyType.ArrowUp) {
            this.keyBoard["key_up_pressed"] = this.keyBoard["key_up_pressed"]!! + 1
        } else if (key.getKeyType() === KeyType.ArrowDown) {
            this.keyBoard["key_down_pressed"] = this.keyBoard["key_down_pressed"]!! + 1
        } //else if (key.getKeyType() === KeyType.F1) {

        //}
        else if (key.character != null && key.character.code == 32) {
            this.keyBoard["key_jump"] = this.keyBoard["key_jump"]!! + 1

        } else if ((key.character != null && key.character.lowercase()
                .get(0) == 's')
        ) {
            this.keyBoard["start"] = this.keyBoard["start"]!! + 1

        }
    }
    fun getPressedKeys():Map<String,Int>{
        return this.keyBoard.filter { it.value>0 }
    }
    fun getKeyValue(key:String):Int{
        return if(this.keyBoard.containsKey(key))
            this.keyBoard[key]!!
        else
            -1
    }

}