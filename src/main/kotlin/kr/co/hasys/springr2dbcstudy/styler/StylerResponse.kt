package kr.co.hasys.springr2dbcstudy.styler

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class StylerResponse(val id: String, val name: String)