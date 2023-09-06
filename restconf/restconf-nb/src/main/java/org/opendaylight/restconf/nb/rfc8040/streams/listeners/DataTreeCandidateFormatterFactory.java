/*
 * Copyright (c) 2020 PANTHEON.tech, s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.restconf.nb.rfc8040.streams.listeners;

import java.util.Collection;
import org.opendaylight.yangtools.yang.data.tree.api.DataTreeCandidate;

abstract class DataTreeCandidateFormatterFactory extends EventFormatterFactory<Collection<DataTreeCandidate>> {
    DataTreeCandidateFormatterFactory(final DataTreeCandidateFormatter emptyFormatter) {
        super(emptyFormatter);
    }
}
