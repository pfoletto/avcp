package com.spark

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AggiudicatariController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Aggiudicatari.list(params), model:[aggiudicatariCount: Aggiudicatari.count()]
    }

    def show(Aggiudicatari aggiudicatari) {
        respond aggiudicatari
    }

    def create() {
        respond new Aggiudicatari(params)
    }

    @Transactional
    def save(Aggiudicatari aggiudicatari) {
        if (aggiudicatari == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggiudicatari.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggiudicatari.errors, view:'create'
            return
        }

        aggiudicatari.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'aggiudicatari.label', default: 'Aggiudicatari'), aggiudicatari.id])
                redirect aggiudicatari
            }
            '*' { respond aggiudicatari, [status: CREATED] }
        }
    }

    def edit(Aggiudicatari aggiudicatari) {
        respond aggiudicatari
    }

    @Transactional
    def update(Aggiudicatari aggiudicatari) {
        if (aggiudicatari == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggiudicatari.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggiudicatari.errors, view:'edit'
            return
        }

        aggiudicatari.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'aggiudicatari.label', default: 'Aggiudicatari'), aggiudicatari.id])
                redirect aggiudicatari
            }
            '*'{ respond aggiudicatari, [status: OK] }
        }
    }

    @Transactional
    def delete(Aggiudicatari aggiudicatari) {

        if (aggiudicatari == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        aggiudicatari.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'aggiudicatari.label', default: 'Aggiudicatari'), aggiudicatari.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'aggiudicatari.label', default: 'Aggiudicatari'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
