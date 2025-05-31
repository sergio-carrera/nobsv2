package com.example.nobsv2.transaction;

import com.example.nobsv2.Command;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TransferService implements Command<TransferDTO, String> {

    private final BankAccountRepository bankAccountRepository;

    public TransferService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public ResponseEntity<String> execute(TransferDTO transfer) {
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transfer.getFromUser());
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transfer.getToUser());

        if (fromAccount.isEmpty() || toAccount.isEmpty()) {
            throw  new RuntimeException("User not found"); //this can be a custom exception
        }

        BankAccount from = fromAccount.get();
        BankAccount to = toAccount.get();

        //add & deduct
        add(to, transfer.getAmount());
        //At this point -> money has been added to the account but not checked if there's enough money to transfer
        System.out.println("After adding before deducting");
        System.out.println(bankAccountRepository.findById(to.getName())); //this would be better as a logging statement
        deduct(from, transfer.getAmount());

        //never called Repository.save() -> we are gonna rely on the Transactional annotation to handle that for us

        return ResponseEntity.ok("Success");
    }

    private void deduct(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() < amount) {
            throw new RuntimeException("Not enough money");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
    }

    private void add(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
    }
}
