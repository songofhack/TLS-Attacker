/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS.
 *
 * Copyright (C) 2015 Juraj Somorovsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.rub.nds.tlsattacker.tls.protocol.handshake.handlers;

import de.rub.nds.tlsattacker.tls.protocol.ProtocolMessageHandler;
import de.rub.nds.tlsattacker.tls.protocol.handshake.constants.CipherSuite;
import de.rub.nds.tlsattacker.tls.protocol.handshake.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.protocol.handshake.constants.KeyExchangeAlgorithm;
import de.rub.nds.tlsattacker.tls.protocol.handshake.messages.ClientKeyExchangeMessage;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import de.rub.nds.tlsattacker.util.ArrayConverter;

/**
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 * @param <HandshakeMessage>
 */
public abstract class ClientKeyExchangeHandler<HandshakeMessage extends ClientKeyExchangeMessage> extends
	HandshakeMessageHandler<HandshakeMessage> {

    KeyExchangeAlgorithm keyExchangeAlgorithm;

    public ClientKeyExchangeHandler(TlsContext tlsContext) {
	super(tlsContext);
    }

    @Override
    public byte[] prepareMessageAction() {
	protocolMessage.setType(HandshakeMessageType.CLIENT_KEY_EXCHANGE.getValue());
	CipherSuite selectedCipherSuite = tlsContext.getSelectedCipherSuite();
	KeyExchangeAlgorithm keyExchange = KeyExchangeAlgorithm.getKeyExchangeAlgorithm(selectedCipherSuite);
	if (keyExchange != keyExchangeAlgorithm) {
	    throw new UnsupportedOperationException("The selected key exchange algorithm (" + keyExchange
		    + ") is not supported yet");
	}
	byte[] result = this.prepareKeyExchangeMessage();
	protocolMessage.setLength(result.length);
	long header = (protocolMessage.getType().getValue() << 24) + protocolMessage.getLength().getValue();
	protocolMessage.setCompleteResultingMessage(ArrayConverter.concatenate(
		ArrayConverter.longToUint32Bytes(header), result));

	return protocolMessage.getCompleteResultingMessage().getValue();
    }

    @Override
    public int parseMessageAction(byte[] message, int pointer) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    abstract byte[] prepareKeyExchangeMessage();

}