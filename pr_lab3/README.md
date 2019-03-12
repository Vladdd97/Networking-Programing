## Network Programming Laboratory Work Nr.3

### Prerequisites:

-   C#/Java
-   HTTP - Protocol

### Objectives:

-   Understand the HTTP protocol and the basic methods.
-   GET, POST, DELETE, PUT.
-   Semnification of status codes.
-   Types of HTTP conections.
-   Basic concepts of HTTP method : headers, servers , clients, methods.

### Task: Create an application in which you will perform different HTTP requests to [this](https://httpbin.org/) site.


### Task Implementation

In this laboratory work I have implemented 5 different types of HTTP requests.
- POST
- GET
- DELETE
- PATCH
- PUT

My application has one base method **sendRequest(requestType)** which will receive the type of HTTP request you want to perform and base of its type the request will be send to the server.

```java
private static void sendRequest(String requestType) throws IOException {
    URL obj = new URL(REQUEST_URL + "/" + requestType.toLowerCase());
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod(requestType.toUpperCase());
    con.setRequestProperty("User-Agent", USER_AGENT);


    if (requestType.toUpperCase().equals("POST")) {
        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END
    }

    if (requestType.toUpperCase().equals("GET")) {
        sendGet();
        return;
    }

    int responseCode = con.getResponseCode();
    System.out.println(requestType + " Response Code :: " + responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) { //success
        BufferedReader in = new BufferedReader(new InputStreamReader(
            con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in .readLine()) != null) {
            response.append(inputLine);
        } in .close();

        // print result
        System.out.println(response.toString());
    } else {
        System.out.println(requestType + " request not worked");
    }
}
```

