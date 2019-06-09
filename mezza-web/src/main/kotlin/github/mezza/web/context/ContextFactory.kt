package github.mezza.web.context

import github.mezza.core.Context
import io.ktor.application.ApplicationCall

interface ContextFactory {
    fun getContext(call: ApplicationCall): Context
}