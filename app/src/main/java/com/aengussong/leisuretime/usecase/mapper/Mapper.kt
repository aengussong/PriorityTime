package com.aengussong.leisuretime.usecase.mapper

import com.aengussong.leisuretime.data.local.entity.LeisureEntity
import com.aengussong.leisuretime.model.Leisure

open class Mapper {

    protected fun LeisureEntity.toLeisure() = Leisure(id, name, counter, updated)
}