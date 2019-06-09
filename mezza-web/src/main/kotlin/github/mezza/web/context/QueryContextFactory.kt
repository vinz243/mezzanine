package github.mezza.web.context

import github.mezza.core.Context
import github.mezza.web.MissingParamException
import io.ktor.application.ApplicationCall

class QueryContextFactory : ContextFactory {
    override fun getContext(call: ApplicationCall) = Context(call.parameters["language"]
            ?: throw MissingParamException("Missing language"))
}