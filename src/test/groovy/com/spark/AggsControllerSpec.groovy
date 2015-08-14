package com.spark

import grails.test.mixin.*
import spock.lang.*

@TestFor(AggsController)
@Mock(Aggs)
class AggsControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.aggsList
            model.aggsCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.aggs!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def aggs = new Aggs()
            aggs.validate()
            controller.save(aggs)

        then:"The create view is rendered again with the correct model"
            model.aggs!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            aggs = new Aggs(params)

            controller.save(aggs)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/aggs/show/1'
            controller.flash.message != null
            Aggs.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def aggs = new Aggs(params)
            controller.show(aggs)

        then:"A model is populated containing the domain instance"
            model.aggs == aggs
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def aggs = new Aggs(params)
            controller.edit(aggs)

        then:"A model is populated containing the domain instance"
            model.aggs == aggs
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/aggs/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def aggs = new Aggs()
            aggs.validate()
            controller.update(aggs)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.aggs == aggs

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            aggs = new Aggs(params).save(flush: true)
            controller.update(aggs)

        then:"A redirect is issued to the show action"
            aggs != null
            response.redirectedUrl == "/aggs/show/$aggs.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/aggs/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def aggs = new Aggs(params).save(flush: true)

        then:"It exists"
            Aggs.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(aggs)

        then:"The instance is deleted"
            Aggs.count() == 0
            response.redirectedUrl == '/aggs/index'
            flash.message != null
    }
}
