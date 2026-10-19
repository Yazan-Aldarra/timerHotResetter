package ap.be;

public enum Template {
    TIMER("Timer"),
    HOME("Home");

    private final String fileName;

    public String getFileName() {
		return fileName;
	}

	private Template(final String fileName) {
        this.fileName = fileName;
    }
}
