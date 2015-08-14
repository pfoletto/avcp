package com.spark

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LottiController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Lotti.list(params), model:[lottiCount: Lotti.count()]
    }

    def show(Lotti lotti) {
        respond lotti
    }

    def create() {
        respond new Lotti(params)
    }

    @Transactional
    def save(Lotti lotti) {
        if (lotti == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lotti.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lotti.errors, view:'create'
            return
        }

        lotti.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lotti.label', default: 'Lotti'), lotti.id])
                redirect lotti
            }
            '*' { respond lotti, [status: CREATED] }
        }
    }

    def edit(Lotti lotti) {
        respond lotti
    }

    @Transactional
    def update(Lotti lotti) {
        if (lotti == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lotti.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lotti.errors, view:'edit'
            return
        }

        lotti.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lotti.label', default: 'Lotti'), lotti.id])
                redirect lotti
            }
            '*'{ respond lotti, [status: OK] }
        }
    }

    @Transactional
    def delete(Lotti lotti) {

        if (lotti == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lotti.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lotti.label', default: 'Lotti'), lotti.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lotti.label', default: 'Lotti'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
