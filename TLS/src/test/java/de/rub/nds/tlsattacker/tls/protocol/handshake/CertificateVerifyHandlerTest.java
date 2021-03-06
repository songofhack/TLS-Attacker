/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handshake;

import de.rub.nds.tlsattacker.tls.protocol.handshake.CertificateVerifyHandler;
import de.rub.nds.tlsattacker.tls.constants.ClientCertificateType;
import de.rub.nds.tlsattacker.tls.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.constants.HashAlgorithm;
import de.rub.nds.tlsattacker.tls.constants.SignatureAlgorithm;
import de.rub.nds.tlsattacker.tls.constants.SignatureAndHashAlgorithm;
import de.rub.nds.tlsattacker.tls.protocol.handshake.CertificateRequestMessage;
import de.rub.nds.tlsattacker.tls.protocol.handshake.CertificateVerifyMessage;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import de.rub.nds.tlsattacker.util.ArrayConverter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 * @author Philip Riese <philip.riese@rub.de>
 */
public class CertificateVerifyHandlerTest {

    CertificateVerifyHandler handler;

    TlsContext tlsContext;

    public CertificateVerifyHandlerTest() {
	tlsContext = new TlsContext();
	handler = new CertificateVerifyHandler(tlsContext);
    }

    /**
     * Test of prepareMessageAction method, of class CertificateVerifyHandler.
     */
    @Test
    public void testPrepareMessageAction() {
	// todo
    }

    /**
     * Test of parseMessageAction method, of class CertificateVerifyHandler.
     */
    @Test
    public void testParseMessageAction() {

	handler.initializeProtocolMessage();

	byte[] inputBytes = { HandshakeMessageType.CERTIFICATE_VERIFY.getValue(), 0x00, 0x00, 0x09 };
	byte[] sigHashAlg = { HashAlgorithm.SHA512.getValue(), SignatureAlgorithm.RSA.getValue() };
	inputBytes = ArrayConverter.concatenate(inputBytes, sigHashAlg, new byte[] { 0x00, 0x05 }, new byte[] { 0x25,
		0x26, 0x27, 0x28, 0x29 });
	int endPointer = handler.parseMessageAction(inputBytes, 0);
	CertificateVerifyMessage message = (CertificateVerifyMessage) handler.getProtocolMessage();

	assertNotNull("Confirm endPointer is not 'NULL'", endPointer);
	assertEquals("Confirm actual message length", endPointer, 13);
	assertEquals("Confirm message type", HandshakeMessageType.CERTIFICATE_VERIFY, message.getHandshakeMessageType());
	assertArrayEquals("Confirm SignatureAndHashAlgorithm type", sigHashAlg, message.getSignatureHashAlgorithm()
		.getValue());
	assertTrue("Confirm Signature Length", message.getSignatureLength().getValue() == 5);
	assertTrue("Confirm Signature",
		Arrays.equals(message.getSignature().getValue(), new byte[] { 0x25, 0x26, 0x27, 0x28, 0x29 }));

    }

}
