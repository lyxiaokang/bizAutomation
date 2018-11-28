package com.jxrt.httpclient;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class TrustAnyTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[] {};
	}
}

class TrustAnyHostnameVerifier implements HostnameVerifier {
	
	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
	
}