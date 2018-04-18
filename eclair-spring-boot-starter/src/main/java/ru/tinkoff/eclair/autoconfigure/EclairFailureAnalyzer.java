/*
 * Copyright 2018 Tinkoff Bank
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.tinkoff.eclair.autoconfigure;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import ru.tinkoff.eclair.exception.AnnotationUsageException;

import java.lang.annotation.Annotation;

import static java.util.Objects.nonNull;

/**
 * @author Vyacheslav Klapatnyuk
 */
public class EclairFailureAnalyzer extends AbstractFailureAnalyzer<AnnotationUsageException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, AnnotationUsageException cause) {
        return new FailureAnalysis(buildDescription(cause), buildAction(), cause);
    }

    private String buildDescription(AnnotationUsageException cause) {
        StringBuilder builder = new StringBuilder(cause.getMessage() + " | " + cause.getMethod());
        Annotation annotation = cause.getAnnotation();
        if (nonNull(annotation)) {
            builder.append(" | ").append(annotation);
        }
        return builder.toString();
    }

    private String buildAction() {
        return "Please correct the annotation definition";
    }
}