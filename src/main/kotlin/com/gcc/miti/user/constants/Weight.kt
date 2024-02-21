package com.gcc.miti.user.constants

enum class Weight(val min: Int, val max: Int) {
    A(30, 39),
    B(40, 49),
    C(50, 59),
    D(60, 69),
    E(70, 79),
    F(80, 89),
    G(90, 99),
    H(100, 109),
    I(110, 119),
    J(120, 129),
    HIDDEN(0,0);
    fun toMinMaxString(): String {
        return if(this.name != "HIDDEN"){
            "$min~$max"
        }else{
            "비공개"
        }
    }
}
