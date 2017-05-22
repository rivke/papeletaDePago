package payrollcasestudy.transactions;

import payrollcasestudy.boundaries.Repositoory;

/**
 * Listing 19-1
 */
public interface Transaction {
    public void execute(Repositoory repository);
}
