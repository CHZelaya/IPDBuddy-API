package oosd.sait.ipdbuddyapi.dto;

public class BillableItemsRequest {

    private String billableType;
    private int quantity;
    private String notes;

    public String getBillableType() {
        return billableType;
    }

    public void setBillableType(String billableType) {
        this.billableType = billableType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
