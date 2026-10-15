package builder;

import java.util.Map;

/** Product: an HTTP request configured through its Builder. */
public class Problem {
    private final String url;
    private final String method;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;
    private final int timeout;

    private Problem(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = Map.copyOf(builder.headers);
        this.queryParams = Map.copyOf(builder.queryParams);
        this.body = builder.body;
        this.timeout = builder.timeout;
    }

    /** Builder: collects request settings, then creates the Problem product. */
    public static class Builder {
        private final String url;
        private String method = "GET";
        private Map<String, String> headers = Map.of();
        private Map<String, String> queryParams = Map.of();
        private String body;
        private int timeout = 30_000;

        public Builder(String url) {
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("url must not be blank");
            }
            this.url = url;
        }

        public Builder method(String method) {
            if (method == null || method.isBlank()) {
                throw new IllegalArgumentException("method must not be blank");
            }
            this.method = method;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers == null ? Map.of() : Map.copyOf(headers);
            return this;
        }

        public Builder queryParams(Map<String, String> queryParams) {
            this.queryParams = queryParams == null ? Map.of() : Map.copyOf(queryParams);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder timeout(int timeout) {
            if (timeout <= 0) {
                throw new IllegalArgumentException("timeout must be positive");
            }
            this.timeout = timeout;
            return this;
        }

        public Problem build() {
            return new Problem(this);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    public int getTimeout() {
        return timeout;
    }
}

class HttpAppTelescoping {
    public static void main(String[] args) {
        Problem req1 = new Problem.Builder("https://api.example.com/data")
                .build();

        Problem req2 = new Problem.Builder("https://api.example.com/submit")
                .method("POST")
                .body("{\"key\":\"value\"}")
                .timeout(10_000)
                .build();

        System.out.println(req1.getMethod()); // GET
        System.out.println(req2.getMethod()); // POST
    }
}
