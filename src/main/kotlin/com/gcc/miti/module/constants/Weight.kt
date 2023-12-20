package com.gcc.miti.module.constants

enum class Weight(val min: Int, val max: Int) {
    A(40, 50),
    B(50, 60),
    C(60, 70),
    D(70, 80),
    E(80, 90),
    F(90, 100),
    HIDDEN(0,0);
    fun toMinMaxString(): String {
        return if(this.name != "HIDDEN"){
            "$min~$max"
        }else{
            "비공개"
        }
    }
}
