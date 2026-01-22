public class Event extends Task{
    private String startDate;
    private String endDate;

    public Event(String startDate, String endDate, String description){
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return "[E]" + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }
}
