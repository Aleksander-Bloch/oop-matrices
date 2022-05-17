package pl.edu.mimuw.operation;

public interface Operation {
    // Created to pass type of operation into methods to avoid duplication.
    double executeOperation(double x, double y);
}
