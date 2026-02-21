package com.blackrock.hackingindia.pojo;

import java.util.List;

public class Expenses {
    
    private List<Expense> expenses;

    public Expenses() {
    }

    public Expenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
