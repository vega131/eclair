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

package ru.tinkoff.eclair.definition.method;

import ru.tinkoff.eclair.annotation.Mdc;
import ru.tinkoff.eclair.definition.ParameterMdc;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

/**
 * Composite DTO matches to set of @Mdc annotations defined on {@link Method} and its {@link Parameter}s.
 *
 * @author Vyacheslav Klapatnyuk
 * @see Mdc
 */
public class MethodMdc implements MethodDefinition {

    private final Method method;
    private final List<String> parameterNames;
    private final Set<ParameterMdc> methodDefinitions;
    private final List<Set<ParameterMdc>> parameterDefinitions;

    public MethodMdc(Method method,
                     List<String> parameterNames,
                     Set<ParameterMdc> methodDefinitions,
                     List<Set<ParameterMdc>> parameterDefinitions) {
        this.method = method;
        this.parameterNames = unmodifiableList(parameterNames);
        this.methodDefinitions = unmodifiableSet(methodDefinitions);
        this.parameterDefinitions = unmodifiableList(parameterDefinitions);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public Set<ParameterMdc> getMethodDefinitions() {
        return methodDefinitions;
    }

    public List<Set<ParameterMdc>> getParameterDefinitions() {
        return parameterDefinitions;
    }
}
