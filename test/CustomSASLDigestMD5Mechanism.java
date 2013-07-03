


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;

public class CustomSASLDigestMD5Mechanism extends SASLMechanism {

	public CustomSASLDigestMD5Mechanism(SASLAuthentication saslAuthentication) {
		super(saslAuthentication);
	}

	@Override
	public void authenticate(String username, String host, String password)
			throws IOException, XMPPException {
		this.authenticationId = username;
		this.password = password;
		this.hostname = host;

		String[] mechanisms = { getName() };
		Map<String, String> props = new HashMap<String, String>();
		sc = Sasl.createSaslClient(mechanisms, null, "xmpp", host, props, this);
		super.authenticate();
	}

	@Override
	public void authenticate(String username, String host, CallbackHandler cbh)
			throws IOException, XMPPException {
		String[] mechanisms = { getName() };
		Map<String, String> props = new HashMap<String, String>();
		sc = Sasl.createSaslClient(mechanisms, null, "xmpp", host, props, cbh);
		super.authenticate();
	}

	protected String getName() {
		return "DIGEST-MD5";
	}
}
