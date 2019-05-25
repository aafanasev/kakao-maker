package dev.afanasev.kakaomaker.generator

import com.agoda.kakao.screen.Screen
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import dev.afanasev.kakaomaker.util.id
import dev.afanasev.kakaomaker.util.isIncludeTag
import dev.afanasev.kakaomaker.util.kakaoIgnore
import dev.afanasev.kakaomaker.util.kakaoScreenName
import dev.afanasev.kakaomaker.util.simpleLazy
import dev.afanasev.kakaomaker.util.viewIdToName
import org.w3c.dom.Element
import java.io.File
import java.util.*
import javax.annotation.Generated
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class KakaoScreenGenerator {

    private val generatedAnnotation by simpleLazy {
        AnnotationSpec.builder(Generated::class.java)
                .addMember("value = [%S]", "Kakao Maker")
                .addMember("comments = %S", "https://github.com/aafanasev/kakao-maker")
                .addMember("date = %S", Date())
                .build()
    }

    private val viewTypeProvider by simpleLazy { KakaoTypeProvider() }

    fun generate(params: ScreenParams, layoutFile: File, directories: SortedSet<String>): String {
        val xmlParser = createXmlParser()
        val document = xmlParser.parse(layoutFile)

        val classBuilder = TypeSpec
                .classBuilder(params.screenName)
                .addAnnotation(generatedAnnotation)
                .superclass(Screen::class.asClassName().parameterizedBy(ClassName.bestGuess(params.screenName)))

        addProperties(classBuilder, document.documentElement, directories)

        val file = FileSpec
                .builder(params.packageName, "${params.screenName}.kt")
                .addImport(params.applicationId, "R")
                .addType(classBuilder.build())
                .build()

        xmlParser.reset()

        return file.toString()
    }

    private fun addProperties(classBuilder: TypeSpec.Builder, element: Element, directories: SortedSet<String>) {
        if (element.kakaoIgnore) {
            return
        }

        if (element.isIncludeTag) {
            parseIncludedLayout(classBuilder, element, directories)
            return
        }

        val elementId = element.id
        if (elementId.isNotEmpty()) {
            val propertyName = viewIdToName(elementId)
            val propertyType = viewTypeProvider.getType(element.tagName)

            val property = PropertySpec
                    .builder(propertyName, propertyType)
                    .initializer("%L { withId(R.id.%L) } ", propertyType.simpleName, elementId)
                    .build()

            classBuilder.addProperty(property)
        }

        if (element.hasChildNodes()) {
            for (index in 0 until element.childNodes.length) {
                val node = element.childNodes.item(index)

                if (node.nodeType == Element.ELEMENT_NODE) {
                    addProperties(classBuilder, node as Element, directories)
                }
            }
        }
    }

    private fun parseIncludedLayout(classBuilder: TypeSpec.Builder, element: Element, directories: SortedSet<String>) {
        val parser = createXmlParser()

        val includedFile: String? = ""
        return

        val document = parser.parse(includedFile)

        val screenName = document.documentElement.kakaoScreenName
        if (screenName.isNotEmpty()) {
            val elementId = element.id
            val propertyName = if (elementId.isNotEmpty()) {
                viewIdToName(elementId)
            } else {
                screenName.decapitalize()
            }

            val property = PropertySpec
                    .builder(propertyName, ClassName.bestGuess(screenName))
                    .initializer("%L()", screenName)
                    .build()

            classBuilder.addProperty(property)
        } else {
            addProperties(classBuilder, document.documentElement, directories)
        }

        parser.reset()
    }

    private fun createXmlParser(): DocumentBuilder {
        return DocumentBuilderFactory
                .newInstance()
                .apply {
                    isNamespaceAware = true
                }
                .newDocumentBuilder()
    }

}