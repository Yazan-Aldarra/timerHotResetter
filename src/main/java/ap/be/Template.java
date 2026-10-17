package ap.be;

public enum Template {
    HOME("Home");

    private final String fileName;

    public String getFileName() {
		return fileName;
	}

	private Template(final String fileName) {
        this.fileName = fileName;
    }
}
