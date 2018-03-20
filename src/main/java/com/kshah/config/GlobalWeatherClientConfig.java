package com.kshah.config;

import com.kshah.client.GlobalWeatherClient;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class GlobalWeatherClientConfig {

    // NOTE: Values for fields injected on setters so we can do additional validation

    /**
     * Global Weather default URL
     */
    private String url;

    @Value("${clients.global-weather.url}")
    protected void setUrl(String url) {

        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("'clients.global-weather.url' configuration cannot be null or empty");
        }

        this.url = url;
    }


    /**
     * Flag to indicate whether to enable 2-way SSL or not
     */
    private Boolean sslEnabled;

    @Value("${clients.global-weather.ssl.enabled:true}")
    protected void setSslEnabled(Boolean sslEnabled) {

        if (sslEnabled == null) {
            throw new IllegalArgumentException("'clients.global-weather.ssl.enabled' configuration cannot be null");
        }

        this.sslEnabled = sslEnabled;
    }


    /**
     * Keystore
     */
    private Resource keystore;

    @Value("${clients.global-weather.ssl.keystore:#{null}}")
    protected void setKeystore(Resource keystore) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.keystore = keystore;
    }


    /**
     * Password for keystore
     */
    private String keystorePassword;

    @Value("${clients.global-weather.ssl.keystore-password:#{null}}")
    protected void setKeystorePassword(String keystorePassword) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.keystorePassword = keystorePassword;
    }


    /**
     * Type of keystore
     */
    private String keystoreType;

    @Value("${clients.global-weather.ssl.keystore-type:#{null}}")
    protected void setKeystoreType(String keystoreType) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.keystoreType = keystoreType;
    }


    /**
     * The unique alias in the java keystore to identify the key entry to use
     */
    private String keyAlias;

    @Value("${clients.global-weather.ssl.key-alias:#{null}}")
    protected void setKeyAlias(String keyAlias) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.keyAlias = keyAlias;
    }


    /**
     * Private key password
     */
    private String keyPassword;

    @Value("${clients.global-weather.ssl.key-password:#{null}}")
    protected void setKeyPassword(String keyPassword) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.keyPassword = keyPassword;
    }


    /**
     * Trust store
     */
    private Resource truststore;

    @Value("${clients.global-weather.ssl.truststore:#{null}}")
    protected void setTruststore(Resource truststore) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.truststore = truststore;
    }


    /**
     * Password for trust store
     */
    private String truststorePassword;

    @Value("${clients.global-weather.ssl.truststore-password:#{null}}")
    protected void setTruststorePassword(String truststorePassword) {
        // NOTE: We don't do any validation here as we might have instances where SSL is disabled and we don't care about this value
        this.truststorePassword = truststorePassword;
    }


    /**
     * Flag to indicate whether to perform hostname verification
     */
    private Boolean verifyHostName;

    @Value("${clients.global-weather.ssl.verifyHostName:true}")
    protected void setVerifyHostName(Boolean verifyHostName) {

        if (verifyHostName == null) {
            this.verifyHostName = true;
        } else {
            this.verifyHostName = verifyHostName;
        }
    }


    private static final String MARSHALLER_CONTEXT_PATH = "com.kshah.model.globalweather.wsdl";


    @Bean
    public GlobalWeatherClient globalWeatherClient() {
        GlobalWeatherClient client = new GlobalWeatherClient(webServiceTemplate());
        return client;
    }


    @Bean
    protected WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(url);
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        return webServiceTemplate;
    }

    @Bean
    protected Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(MARSHALLER_CONTEXT_PATH);
        return marshaller;
    }

    @Bean
    protected HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setHttpClient(httpClient());
        return httpComponentsMessageSender;
    }

    protected HttpClient httpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create();

        if (sslEnabled) {
            builder = builder.setSSLSocketFactory(sslConnectionSocketFactory());
        }

        // Doing the following to fix the error complaining about Content-Length header already present
        builder = builder.addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> httpRequest.removeHeaders(HTTP.CONTENT_LEN));

        return builder.build();
    }

    protected SSLConnectionSocketFactory sslConnectionSocketFactory() {

        if (verifyHostName) {
            return new SSLConnectionSocketFactory(sslContext());
        } else {
            // NoopHostnameVerifier turns off hostname verification
            // NOTE: This should only be used in lower environments
            return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
        }

    }

    protected SSLContext sslContext() {

        if (keystore == null) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.keystore' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (keystorePassword == null) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.keystore-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (keystoreType == null || keystoreType.isEmpty() || !keystoreType.equals("JKS")) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.keystore-type' was found to be null, empty, or does not have a value of 'JKS'. Please ensure it is set correctly in the application configuration.");
        }

        if (keyAlias == null || keyAlias.isEmpty()) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.key-alias' was found to be null or empty. Please ensure it is set correctly in the application configuration.");
        }

        if (keyPassword == null) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.key-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (truststore == null) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.truststore' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        if (truststorePassword == null) {
            throw new IllegalArgumentException(" The value for 'clients.global-weather.ssl.truststore-password' was found to be null. Please ensure it is set correctly in the application configuration.");
        }

        try {
            return SSLContextBuilder.create()
                    .loadKeyMaterial(keystore.getFile(), keystorePassword.toCharArray(), keyPassword.toCharArray(), (map, socket) -> keyAlias)
                    .loadTrustMaterial(truststore.getFile(), truststorePassword.toCharArray()).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            throw new IllegalStateException("Could not load keystore and/or trust store for Global Weather Client. Please ensure all relevant application configurations are correctly set.", e);
        }
    }

}
