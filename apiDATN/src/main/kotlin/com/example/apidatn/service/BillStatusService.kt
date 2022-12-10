package com.example.apidatn.service

import com.example.apidatn.dto.BillStatusDto

interface BillStatusService {
    fun getBillStatus() : MutableList<BillStatusDto>
}