package com.google.search.robotstxt.spec;

/**
 * Information about a specific test case
 */
public class TestInfo {
    private String robotxtxtContent;
    private String url;
    private String userAgent;
    private Outcome expectedOutcome;
    private String additionalExplanation;

    /**
     * Default constructor
     */
    public TestInfo() {

    }

    /**
     * Constructor with parameters (sets all the fields
     * @param robotxtxtContent The robots.txt file contents
     * @param url The URL
     * @param userAgent The user-agent
     * @param expectedOutcome The expected outcome
     * @param additionalExplanation The additional explanation
     */
    public TestInfo(String robotxtxtContent, String url, String userAgent,
                    Outcome expectedOutcome, String additionalExplanation) {
        this.robotxtxtContent = robotxtxtContent;
        this.url = url;
        this.userAgent = userAgent;
        this.expectedOutcome = expectedOutcome;
        this.additionalExplanation = additionalExplanation;
    }

    public String getRobotxtxtContent() {
        return robotxtxtContent;
    }

    public String getUrl() {
        return url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Outcome getExpectedOutcome() {
        return expectedOutcome;
    }

    public String getAdditionalExplanation() {
        return additionalExplanation;
    }

    public String toString() {
        return "The robots.txt content: \n\n" + this.robotxtxtContent + "\n\n"
                + "The URL: " + this.url + "\n"
                + "The user-agent: " + this.userAgent + "\n"
                + "The expected outcome: " + this.expectedOutcome + "\n"
                + "The additional explanation: " + this.additionalExplanation + "\n";
    }
}
