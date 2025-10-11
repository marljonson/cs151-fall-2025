package project.models;

import java.time.Instant;

import javax.swing.ProgressMonitor;

public class PromoWindow {

    private final double discountFraction;
    private final Instant startTime;
    private final Instant endTime; 

    //constructor
    public PromoWindow(double discountFraction, Instant startTime, Instant endTime){

        //only allows [0.0, 1.0] (inclusive)
        if(discountFraction < 0.0 || discountFraction > 1.0 ){ throw new IllegalArgumentException("discount fraction must be between 0 and 1 (inclusive)!"); }
        if(startTime == null) { throw new IllegalArgumentException("startTime cannot be null"); }
        if(endTime == null) { throw new IllegalArgumentException("endTime cannot be null"); }

        if(!startTime.isBefore(endTime)) { throw new IllegalArgumentException("startTime must be before endTime"); }

        this.discountFraction = discountFraction;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean activeNow(Instant now){

        if(now == null) throw new IllegalArgumentException("now cannot be null");

        //promoWindow is only active if the customer buys at a time("now") that is between start and end
        return !now.isBefore(startTime) && now.isBefore(endTime);
    }

    //getters
    public double getDiscountFraction() { return this.discountFraction; }
    public Instant getStartTime() { return this.startTime; }
    public Instant getEndTime() { return this.endTime; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PromoWindow{discountFraction=").append(discountFraction)
          .append(", startTime=").append(startTime)
          .append(", endTime=").append(endTime).append("}");
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        PromoWindow other = (PromoWindow) obj;
        return  this.discountFraction == other.discountFraction &&
                this.startTime.equals(other.startTime) &&
                this.endTime.equals(other.endTime);
    }
}
