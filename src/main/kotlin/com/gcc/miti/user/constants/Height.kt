package com.gcc.miti.user.constants

enum class Height(val min: Int, val max: Int) {
    A(120, 129),
    B(130, 139),
    C(140, 149),
    D(150, 159),
    E(160, 169),
    F(170, 179),
    G(180, 189),
    H(190, 199),
    I(200, 209),
    J(210, 219),
    HIDDEN(0,0);

    fun toMinMaxString(): String {
        return if(this.name != "HIDDEN"){
            "$min~$max"
        }else{
            "비공개"
        }
    }
}
