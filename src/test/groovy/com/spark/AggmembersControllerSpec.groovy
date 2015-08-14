package com.spark

import grails.test.mixin.*
import spock.lang.*

@TestFor(AggMembersController)
@Mock(AggMembers)
class AggMembersControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.aggMembersList
            model.aggMembersCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.aggMembers!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def aggMembers = new AggMembers()
            aggMembers.validate()
            controller.save(aggMembers)

        then:"The create view is rendered again with the correct model"
            model.aggMembers!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            aggMembers = new AggMembers(params)

            controller.save(aggMembers)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/aggMembers/show/1'
            controller.flash.message != null
            AggMembers.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def aggMembers = new AggMembers(params)
            controller.show(aggMembers)

        then:"A model is populated containing the domain instance"
            model.aggMembers == aggMembers
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def aggMembers = new AggMembers(params)
            controller.edit(aggMembers)

        then:"A model is populated containing the domain instance"
            model.aggMembers == aggMembers
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/aggMembers/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def aggMembers = new AggMembers()
            aggMembers.validate()
            controller.update(aggMembers)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.aggMembers == aggMembers

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            aggMembers = new AggMembers(params).save(flush: true)
            controller.update(aggMembers)

        then:"A redirect is issued to the show action"
            aggMembers != null
            response.redirectedUrl == "/aggMembers/show/$aggMembers.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/aggMembers/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def aggMembers = new AggMembers(params).save(flush: true)

        then:"It exists"
            AggMembers.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(aggMembers)

        then:"The instance is deleted"
            AggMembers.count() == 0
            response.redirectedUrl == '/aggMembers/index'
            flash.message != null
    }
}
