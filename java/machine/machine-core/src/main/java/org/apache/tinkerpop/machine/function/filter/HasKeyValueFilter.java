/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tinkerpop.machine.function.filter;

import org.apache.tinkerpop.machine.bytecode.Instruction;
import org.apache.tinkerpop.machine.bytecode.compiler.Argument;
import org.apache.tinkerpop.machine.coefficient.Coefficient;
import org.apache.tinkerpop.machine.function.AbstractFunction;
import org.apache.tinkerpop.machine.function.FilterFunction;
import org.apache.tinkerpop.machine.traverser.Traverser;
import org.apache.tinkerpop.machine.util.StringFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class HasKeyValueFilter<C, K, V> extends AbstractFunction<C> implements FilterFunction<C, Map<K, V>> {

    private final Argument<K> key;
    private final Argument<V> value;

    private HasKeyValueFilter(final Coefficient<C> coefficient, final Set<String> labels, final Argument<K> key, final Argument<V> value) {
        super(coefficient, labels);
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean test(final Traverser<C, Map<K, V>> traverser) {
        final Map<K, V> object = traverser.object();
        return this.value.mapArg(traverser).equals(object.get(this.key.mapArg(traverser)));
    }

    @Override
    public String toString() {
        return StringFactory.makeFunctionString(this, this.key, this.value);
    }

    public static <C, K, V> HasKeyValueFilter<C, K, V> compile(final Instruction<C> instruction) {
        return new HasKeyValueFilter<>(instruction.coefficient(), instruction.labels(), Argument.create(instruction.args()[0]), Argument.create(instruction.args()[1]));
    }
}