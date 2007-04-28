package org.codehaus.groovy.grails.web.servlet.mvc

import org.codehaus.groovy.grails.commons.test.*
import org.codehaus.groovy.grails.commons.*
import org.codehaus.groovy.grails.commons.spring.*
import org.codehaus.groovy.grails.plugins.*
import org.springframework.web.context.request.*
import org.codehaus.groovy.grails.web.servlet.mvc.*
import org.codehaus.groovy.grails.web.servlet.*
import org.springframework.mock.web.*
import org.springframework.validation.*
import org.springframework.web.servlet.*

class RenderDynamicMethodTests extends AbstractGrailsControllerTests {
	
	
	void onSetUp() {
				gcl.parseClass(
		"""
		class TestController {
		   def renderText = {
		        render "text"
		   }

		   def renderTextWithContentType = {
		        render(text:"<foo>bar</foo>",contentType:"text/xml")
           }

           def renderXml = {
                render(contentType:"text/xml") {
                    foo {
                        bar("hello")
                    }
                }
            }
		}
		""")
	}


    void testRenderText() {
        def testCtrl = ga.getControllerClass("TestController").newInstance()

        testCtrl.renderText()
        assertEquals "text", response.delegate.contentAsString
    }

    void testRenderTextWithContentType() {
        def testCtrl = ga.getControllerClass("TestController").newInstance()

        testCtrl.renderTextWithContentType()
        assertEquals "text/xml", response.delegate.contentType
        assertEquals "<foo>bar</foo>", response.delegate.contentAsString

    }

    void testRenderXml() {
        def testCtrl = ga.getControllerClass("TestController").newInstance()

        testCtrl.renderXml()
        assertEquals "text/xml", response.delegate.contentType
        assertEquals "<foo><bar>hello</bar></foo>", response.delegate.contentAsString

    }
}