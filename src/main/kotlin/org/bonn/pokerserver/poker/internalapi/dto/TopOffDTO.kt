package org.bonn.pokerserver.poker.internalapi.dto

import org.bonn.pokerserver.poker.interalapi.dto.BuyInDTO
import org.bonn.pokerserver.poker.interalapi.dto.PlayerDTO


data class UpdateDTO(val player: PlayerDTO, val buyIn: BuyInDTO, val updateType: UpdateType)