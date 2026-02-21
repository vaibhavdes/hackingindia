package com.blackrock.hackingindia.pojo;

import java.util.List;

public class Transactions {
    
    private List<Transaction> expenses;

    public Transactions() {
    }

    public Transactions(List<Transaction> expenses) {
        this.expenses = expenses;
    }

    public List<Transaction> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Transaction> expenses) {
        this.expenses = expenses;
    }
}
