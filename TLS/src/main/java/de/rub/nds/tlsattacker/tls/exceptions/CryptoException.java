/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.exceptions;

/**
 * Crypto exception
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class CryptoException extends RuntimeException {

    public CryptoException() {
	super();
    }

    public CryptoException(String message) {
	super(message);
    }

    public CryptoException(Throwable t) {
	super(t);
    }

    public CryptoException(String message, Throwable t) {
	super(message, t);
    }

}
