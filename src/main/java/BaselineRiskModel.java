/**
 * Created by ktao on 4/10/15.
 */
public class BaselineRiskModel {

    // the double returned is incidence of carcinoma per 10e5 person years of follow up.

    public static double getBaselineRisk(int age){
        return getCompositeIncidenceRate(age) * getNoRiskFactorAdjustment(age);
    }

    private static double getCompositeIncidenceRate(int age){
        if(age < 24) {
            return 2.7;
        } if (age < 29) {
            return 16.8;
        } if (age < 34) {
            return 60.3;
        } if (age < 39) {
            return 114.6;
        } if (age < 44) {
            return 203.7;
        } if (age < 49) {
            return 280.8;
        } if (age < 54) {
            return 320.9;
        } if (age < 59) {
            return 293.8;
        } if (age < 64) {
            return 369.4;
        } if (age < 69) {
            return 356.1;
        } if (age < 74) {
            return 307.8;
        } else {
            return 301.3;
        }
    }

    private static double getNoRiskFactorAdjustment(int age){
        if(age < 50){
            return 0.5529;
        } else {
            return 0.5264;
        }
    }

}
