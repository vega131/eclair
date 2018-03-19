package ru.tinkoff.eclair.definition.factory;

import ru.tinkoff.eclair.annotation.Mdc;
import ru.tinkoff.eclair.definition.MdcPack;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Viacheslav Klapatniuk
 */
public class MdcPackFactory {

    public static MdcPack newInstance(Method method,
                                      Set<Mdc> methodMdcs,
                                      List<Set<Mdc>> argumentMdcs) {
        if (methodMdcs.isEmpty() && argumentMdcs.stream().allMatch(Collection::isEmpty)) {
            return null;
        }
        return MdcPack.builder()
                .method(method)
                .methodDefinitions(methodMdcs.stream().map(MdcDefinitionFactory::newInstance).collect(toSet()))
                .parameterDefinitions(argumentMdcs.stream()
                        .map(mdcs -> unmodifiableSet(mdcs.stream().map(MdcDefinitionFactory::newInstance).collect(toSet())))
                        .collect(toList()))
                .build();
    }
}
