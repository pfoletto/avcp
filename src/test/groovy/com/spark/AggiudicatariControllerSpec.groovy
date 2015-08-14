package com.spark

import grails.test.mixin.*
import spock.lang.*

@TestFor(AggiudicatariController)
@Mock(Aggiudicatari)
class AggiudicatariControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.aggiudicatariList
            model.aggiudicatariCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.aggiudicatari!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def aggiudicatari = new Aggiudicatari()
            aggiudicatari.validate()
            controller.save(aggiudicatari)

        then:"The create view is rendered again with the correct model"
            model.aggiudicatari!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            aggiudicatari = new Aggiudicatari(params)

            controller.save(aggiudicatari)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/aggiudicatari/show/1'
            controller.flash.message != null
            Aggiudicatari.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def aggiudicatari = new Aggiudicatari(params)
            controller.show(aggiudicatari)

        then:"A model is populated containing the domain instance"
            model.aggiudicatari == aggiudicatari
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def aggiudicatari = new Aggiudicatari(params)
            controller.edit(aggiudicatari)

        then:"A model is populated containing the domain instance"
            model.aggiudicatari == aggiudicatari
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/aggiudicatari/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def aggiudicatari = new Aggiudicatari()
            aggiudicatari.validate()
            controller.update(aggiudicatari)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.aggiudicatari == aggiudicatari

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            aggiudicatari = new Aggiudicatari(params).save(flush: true)
            controller.update(aggiudicatari)

        then:"A redirect is issued to the show action"
            aggiudicatari != null
            response.redirectedUrl == "/aggiudicatari/show/$aggiudicatari.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/aggiudicatari/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def aggiudicatari = new Aggiudicatari(params).save(flush: true)

        then:"It exists"
            Aggiudicatari.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(aggiudicatari)

        then:"The instance is deleted"
            Aggiudicatari.count() == 0
            response.redirectedUrl == '/aggiudicatari/index'
            flash.message != null
    }
}
