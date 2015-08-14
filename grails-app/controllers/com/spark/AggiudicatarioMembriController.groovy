package com.spark

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AggiudicatarioMembriController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AggiudicatarioMembri.list(params), model:[aggiudicatarioMembriCount: AggiudicatarioMembri.count()]
    }

    def show(AggiudicatarioMembri aggiudicatarioMembri) {
        respond aggiudicatarioMembri
    }

    def create() {
        respond new AggiudicatarioMembri(params)
    }

    @Transactional
    def save(AggiudicatarioMembri aggiudicatarioMembri) {
        if (aggiudicatarioMembri == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggiudicatarioMembri.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggiudicatarioMembri.errors, view:'create'
            return
        }

        aggiudicatarioMembri.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'aggiudicatarioMembri.label', default: 'AggiudicatarioMembri'), aggiudicatarioMembri.id])
                redirect aggiudicatarioMembri
            }
            '*' { respond aggiudicatarioMembri, [status: CREATED] }
        }
    }

    def edit(AggiudicatarioMembri aggiudicatarioMembri) {
        respond aggiudicatarioMembri
    }

    @Transactional
    def update(AggiudicatarioMembri aggiudicatarioMembri) {
        if (aggiudicatarioMembri == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggiudicatarioMembri.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggiudicatarioMembri.errors, view:'edit'
            return
        }

        aggiudicatarioMembri.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'aggiudicatarioMembri.label', default: 'AggiudicatarioMembri'), aggiudicatarioMembri.id])
                redirect aggiudicatarioMembri
            }
            '*'{ respond aggiudicatarioMembri, [status: OK] }
        }
    }

    @Transactional
    def delete(AggiudicatarioMembri aggiudicatarioMembri) {

        if (aggiudicatarioMembri == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        aggiudicatarioMembri.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'aggiudicatarioMembri.label', default: 'AggiudicatarioMembri'), aggiudicatarioMembri.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'aggiudicatarioMembri.label', default: 'AggiudicatarioMembri'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
