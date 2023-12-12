package com.pcc.sgs.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author James Jasper D. Villamor
 */
@Setter
@Getter
@NoArgsConstructor
public class GradeCalculator {
    Double gradeEquivalent = 0.00;
    
    public Double calculatePercentage(Double score, Double total, int percentage) {
        return percentage * (score / total);
    }
    
    public Double calculateTotal(Double attPercentage, Double quiz_percentage, Double ass_percentage, Double exam_percentage) {
        Double total = attPercentage + quiz_percentage + ass_percentage + exam_percentage;
        return total;
    }
    
    public Double calculateGradeEquivalent(Double total) {

        if(total >= 99 && total <= 100) 
            gradeEquivalent = 1.00;
        else if(total >= 96 && total < 99)
            gradeEquivalent = 1.25;
        else if(total >= 93 && total < 96)
            gradeEquivalent = 1.5;
        else if(total >= 90 && total < 93)
            gradeEquivalent = 1.75;
        else if(total >= 87 && total < 90)
            gradeEquivalent = 2.00;
        else if(total >= 84 && total < 87)
            gradeEquivalent = 2.25;
        else if(total >= 81 && total < 84)
            gradeEquivalent = 2.50;
        else if(total >= 78 && total < 81)
            gradeEquivalent = 2.75;
        else if(total >= 75 && total < 78)
            gradeEquivalent = 3.00;
        else if (total < 75)
            gradeEquivalent = 5.00;
        
        return gradeEquivalent;
    }
    
    public Double calculateFinalGWA(Double prelimTotal, Double midtermTotal, Double semifinalTotal, Double finalTotal) {
        return (prelimTotal + midtermTotal + semifinalTotal + finalTotal) / 4;
    }

}
