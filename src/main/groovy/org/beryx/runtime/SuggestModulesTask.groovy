/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beryx.runtime

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional

import groovy.transform.CompileStatic
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import org.beryx.runtime.util.SuggestedModulesBuilder

@CompileStatic
abstract class SuggestModulesTask extends DefaultTask {
    @Input
    @Optional
    abstract Property<String> getJavaHome()

    @InputFiles
    abstract ListProperty<File> getClassPathFiles();

    @InputFiles
    abstract RegularFileProperty getMainDistJarFile()

    @TaskAction
    void suggestMergedModuleInfoAction() {
        def modules = new SuggestedModulesBuilder(
                javaHome.get()
        ).getProjectModules(
                mainDistJarFile.get(),
                classPathFiles.get()
        )
        println "modules = [\n${modules.collect { '\'' + it + '\'' }.join( ',\n' )}]"
    }
}
