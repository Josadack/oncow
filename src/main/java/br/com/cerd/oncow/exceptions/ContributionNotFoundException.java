package br.com.cerd.oncow.exceptions;

public class ContributionNotFoundException extends RuntimeException {

    public ContributionNotFoundException(String message) {
        super(message);
    }
}
