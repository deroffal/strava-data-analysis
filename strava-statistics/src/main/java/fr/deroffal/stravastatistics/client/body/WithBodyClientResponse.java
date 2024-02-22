package fr.deroffal.stravastatistics.client.body;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNullApi;

public class WithBodyClientResponse implements ClientHttpResponse {

  private final ClientHttpResponse delegate;
  private final byte[] bodyBytes;

  public WithBodyClientResponse(ClientHttpResponse delegate) throws IOException {
    super();
    this.delegate = delegate;
    this.bodyBytes = getString(delegate);
  }

  private static byte[] getString(ClientHttpResponse delegate) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    delegate.getBody().transferTo(baos);
    return baos.toByteArray();

  }

  @Override
  public HttpStatusCode getStatusCode() throws IOException {
    return delegate.getStatusCode();
  }

  @Override
  public String getStatusText() throws IOException {
    return delegate.getStatusText();
  }

  @Override
  public void close() {
    delegate.close();
  }

  @Override
  public InputStream getBody() {
    return new ByteArrayInputStream(bodyBytes);
  }

  @Override
  public HttpHeaders getHeaders() {
    return delegate.getHeaders();
  }
}
