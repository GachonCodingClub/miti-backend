package com.gcc.miti.module.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("participant")
class Participant() : WaitingList()
