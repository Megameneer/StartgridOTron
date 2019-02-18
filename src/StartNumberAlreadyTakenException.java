class StartNumberAlreadyTakenException extends Exception {
    StartNumberAlreadyTakenException(Racer otherRacerWithSameStartNumber) {
        super(
                "Het startnummer " +
                        otherRacerWithSameStartNumber.getStartNumber() +
                        " is al bezet door " +
                        otherRacerWithSameStartNumber.getFirstName() +
                        " " +
                        otherRacerWithSameStartNumber.getLastName()
        );
    }
}
