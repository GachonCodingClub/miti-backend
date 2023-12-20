package com.gcc.miti.module.constants

enum class Height(val min: Int, val max: Int) {
    A(130, 140),
    B(140, 150),
    C(150, 160),
    D(160, 170),
    E(170, 180),
    F(180, 190),
    HIDDEN(0,0);

    fun toMinMaxString(): String {
        return if(this.name != "HIDDEN"){
            "$min~$max"
        }else{
            "비공개"
        }
    }
}
