package com.bajingxiaozi.plugindemo

import com.bajingxiaozi.plugindemo.utils.JavaUtils2
import org.gradle.api.Plugin
import org.gradle.api.Project

class DemoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // 功能1，创建一个名称为hello的gradle task
        project.task('hello') {
            doLast {
                println 'Hello from the GreetingPlugin'
                project.android.applicationVariants.all { variant ->
                    println 'variant: '+variant.name
                    variant.outputs.each { output ->
                        println 'outputFile: '+output.outputFile
                    }
                }
            }
        }

        // 功能2，对编译后的apk进行一些后处理操作
        ResignPluginExtension extension = project.getExtensions().create("resign", ResignPluginExtension.class)

        project.android.applicationVariants.all { variant ->
            variant.assemble.doLast {
                println("resign path: " + extension.signDirectory)
                if (extension.signDirectory == null) {
                    return
                }

                final File signDirectory = new File(extension.signDirectory)
                println("resign directory: " + signDirectory.getAbsolutePath())
                println("resign: "+signDirectory.list())
                if (!signDirectory.exists()) {
                    return
                }
                if (!signDirectory.isDirectory()) {
                    return
                }

//                println 'variant1: '+variant.name
                variant.outputs.each { output ->
//                    println 'outputFile1: '+output.outputFile
                    println('resign ' + output.outputFile + " start")
                    JavaUtils2.resign(signDirectory, output.outputFile)
                    println('resign ' + output.outputFile + " end")
                }
            }
        }
    }

}
