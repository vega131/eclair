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

package ru.tinkoff.eclair.validate.log.group;

import ru.tinkoff.eclair.core.AnnotationAttribute;
import ru.tinkoff.eclair.exception.AnnotationUsageException;
import ru.tinkoff.eclair.validate.AnnotationUsageValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Vyacheslav Klapatnyuk
 */
abstract class GroupLogValidator<T extends Annotation> implements AnnotationUsageValidator<Set<T>> {

    private final Map<String, Set<String>> loggerNames;

    GroupLogValidator(Map<String, Set<String>> loggerNames) {
        this.loggerNames = loggerNames;
    }

    /**
     * TODO: add validation of several aliases usage within one method
     */
    @Override
    public void validate(Method method, Set<T> target) throws AnnotationUsageException {
        groupAnnotationsByLogger(method, target).entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .findFirst()
                .ifPresent(entry -> {
                    throw new AnnotationUsageException(
                            format("Annotations duplicated for logger '%s': %s", entry.getKey(), entry.getValue()),
                            method);
                });
    }

    Map<String, List<T>> groupAnnotationsByLogger(Method method, Set<T> annotations) {
        return annotations.stream()
                .collect(groupingBy(annotation -> {
                    String loggerName = AnnotationAttribute.LOGGER.extract(annotation);
                    return loggerNames.entrySet().stream()
                            .filter(entry -> entry.getValue().contains(loggerName))
                            .findFirst()
                            .map(Map.Entry::getKey)
                            .orElseThrow(() -> new AnnotationUsageException(format("Unknown logger '%s'", loggerName), method));
                }));
    }
}