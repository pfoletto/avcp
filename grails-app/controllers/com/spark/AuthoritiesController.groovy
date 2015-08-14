package com.spark

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AuthoritiesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Authorities.list(params), model:[authoritiesCount: Authorities.count()]
    }

    def show(Authorities authorities) {
        respond authorities
    }

    def create() {
        respond new Authorities(params)
    }

    @Transactional
    def save(Authorities authorities) {
        if (authorities == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (authorities.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authorities.errors, view:'create'
            return
        }

        authorities.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'authorities.label', default: 'Authorities'), authorities.id])
                redirect authorities
            }
            '*' { respond authorities, [status: CREATED] }
        }
    }

    def edit(Authorities authorities) {
        respond authorities
    }

    @Transactional
    def update(Authorities authorities) {
        if (authorities == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (authorities.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authorities.errors, view:'edit'
            return
        }

        authorities.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'authorities.label', default: 'Authorities'), authorities.id])
                redirect authorities
            }
            '*'{ respond authorities, [status: OK] }
        }
    }

    @Transactional
    def delete(Authorities authorities) {

        if (authorities == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        authorities.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'authorities.label', default: 'Authorities'), authorities.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'authorities.label', default: 'Authorities'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
