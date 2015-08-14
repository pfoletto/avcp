package com.spark

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContrattiDataSetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContrattiDataSet.list(params), model:[contrattiDataSetCount: ContrattiDataSet.count()]
    }

    def show(ContrattiDataSet contrattiDataSet) {
        respond contrattiDataSet
    }

    def create() {
        respond new ContrattiDataSet(params)
    }

    @Transactional
    def save(ContrattiDataSet contrattiDataSet) {
        if (contrattiDataSet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contrattiDataSet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contrattiDataSet.errors, view:'create'
            return
        }

        contrattiDataSet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contrattiDataSet.label', default: 'ContrattiDataSet'), contrattiDataSet.id])
                redirect contrattiDataSet
            }
            '*' { respond contrattiDataSet, [status: CREATED] }
        }
    }

    def edit(ContrattiDataSet contrattiDataSet) {
        respond contrattiDataSet
    }

    @Transactional
    def update(ContrattiDataSet contrattiDataSet) {
        if (contrattiDataSet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contrattiDataSet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contrattiDataSet.errors, view:'edit'
            return
        }

        contrattiDataSet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contrattiDataSet.label', default: 'ContrattiDataSet'), contrattiDataSet.id])
                redirect contrattiDataSet
            }
            '*'{ respond contrattiDataSet, [status: OK] }
        }
    }

    @Transactional
    def delete(ContrattiDataSet contrattiDataSet) {

        if (contrattiDataSet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contrattiDataSet.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contrattiDataSet.label', default: 'ContrattiDataSet'), contrattiDataSet.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contrattiDataSet.label', default: 'ContrattiDataSet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
