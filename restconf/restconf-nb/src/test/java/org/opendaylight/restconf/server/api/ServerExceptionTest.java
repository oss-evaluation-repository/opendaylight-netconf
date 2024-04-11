/*
 * Copyright (c) 2024 PANTHEON.tech, s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.restconf.server.api;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.opendaylight.yangtools.yang.common.ErrorTag;
import org.opendaylight.yangtools.yang.common.ErrorType;

class ServerExceptionTest {
    @Test
    void stringConstructor() {
        final var ex = new ServerException("some message");
        assertEquals("some message", ex.getMessage());
        assertNull(ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_FAILED, "some message"), ex.error());
    }

    @Test
    void causeConstructor() {
        final var cause = new Throwable("cause message");
        final var ex = new ServerException(cause);
        assertEquals("java.lang.Throwable: cause message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_FAILED, "cause message"), ex.error());
    }

    @Test
    void causeConstructorIAE() {
        final var cause = new IllegalArgumentException("cause message");
        final var ex = new ServerException(cause);
        assertEquals("java.lang.IllegalArgumentException: cause message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.INVALID_VALUE, "cause message"), ex.error());
    }

    @Test
    void causeConstructorUOE() {
        final var cause = new UnsupportedOperationException("cause message");
        final var ex = new ServerException(cause);
        assertEquals("java.lang.UnsupportedOperationException: cause message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_NOT_SUPPORTED, "cause message"),
            ex.error());
    }

    @Test
    void messageCauseConstructor() {
        final var cause = new Throwable("cause message");
        final var ex = new ServerException("some message", cause);
        assertEquals("some message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_FAILED, "some message"), ex.error());
    }

    @Test
    void messageCauseConstructorIAE() {
        final var cause = new IllegalArgumentException("cause message");
        final var ex = new ServerException("some message", cause);
        assertEquals("some message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.INVALID_VALUE, "some message"), ex.error());
    }

    @Test
    void messageCauseConstructorUOE() {
        final var cause = new UnsupportedOperationException("cause message");
        final var ex = new ServerException("some message", cause);
        assertEquals("some message", ex.getMessage());
        assertSame(cause, ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_NOT_SUPPORTED, "some message"),
            ex.error());
    }

    @Test
    void formatConstructor() {
        final var ex = new ServerException("huh %s: %s", 1, "hah");
        assertEquals("huh 1: hah", ex.getMessage());
        assertNull(ex.getCause());
        assertEquals(new ServerError(ErrorType.APPLICATION, ErrorTag.OPERATION_FAILED, "huh 1: hah"), ex.error());
    }
}
