import java.util.Scanner;

/**
 * Created by ktao on 4/10/15.
 */
public class Main {

    private final int ageOfMenarche;
    private final int numBiopsies;
    private final int ageOfFirstLiveBirth;
    private final int numRelatives;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your age (integer value) :");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter your age of menarche :");
        int ageOfMenarche = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of biopsies you've had :");
        int numBiopsies = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter your age when you had your first child (enter '25' if you've never had a child):");
        int ageOfFirstLiveBirth = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of 1st degree relatives (mother or sisters) who have been diagnosed with breast cancer");
        int numRelatives = Integer.parseInt(scanner.nextLine());

        Main main = new Main(ageOfMenarche, numBiopsies, ageOfFirstLiveBirth, numRelatives);

        double relativeRisk = main.calculateRelativeRisk(age);
        double baselineRisk = main.calculateBaselineRisk(age);

        System.out.println("Your current relative risk : " + relativeRisk);
        System.out.println("Your current baseline risk : " + baselineRisk);

        System.out.println("Probability you will get breast cancer in next year : " + relativeRisk * baselineRisk / 10e3 + "%");
        double currentProbabilityOfBreastCancer = relativeRisk * baselineRisk / 10e5;
        for(int i = 1; i < 30; i ++){
            double probabilityToGetThisYear = main.calculateRelativeRisk(age + i) * main.calculateBaselineRisk(age + i) / 10e5;
            double newProbability = currentProbabilityOfBreastCancer + (1 - currentProbabilityOfBreastCancer) * probabilityToGetThisYear;
            System.out.println("Probability you will get breast cancer in " + (i+1) + " years : " + newProbability * 100 + "%");
            currentProbabilityOfBreastCancer = newProbability;
        }


    }

    public Main(int ageOfMenarche, int numBiopsies, int ageOfFirstLiveBirth, int numRelatives) {
        this.ageOfMenarche = ageOfMenarche;
        this.numBiopsies = numBiopsies;
        this.ageOfFirstLiveBirth = ageOfFirstLiveBirth;
        this.numRelatives = numRelatives;
    }

    private double calculateBaselineRisk(int age) {
        return BaselineRiskModel.getBaselineRisk(age);
    }

    private double calculateRelativeRisk(int age) {
        return calculate(age, ageOfMenarche, numBiopsies, ageOfFirstLiveBirth, numRelatives);
    }

    private double calculate(int age, int ageOfMenarche, int numBiopsies, int ageOfFirstLiveBirth, int numRelatives) {
        int ageCategory = getAgeCategory(age);
        int ageMenarcheCategory = getAgeMenarcheCategory(ageOfMenarche);
        int biopsiesCategory = getBiopsiesCategory(numBiopsies);
        int firstLiveBirthCategory = getFirstLiveBirthCategory(ageOfFirstLiveBirth);
        int numRelativesCategory = getNumRelativesCategory(numRelatives);

        double logOfOddsRatio = -0.74948
                + .09401 * ageMenarcheCategory
                + 0.52926 * biopsiesCategory
                + 0.21863 * firstLiveBirthCategory
                + 0.9583 * numRelativesCategory
                + 0.01081 * ageCategory
                - 0.28804 * biopsiesCategory * ageCategory
                - 0.19081 * firstLiveBirthCategory * numRelativesCategory;
        return Math.exp(logOfOddsRatio);
    }


    private static int getAgeCategory(int age) {
        if (age >= 50) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int getAgeMenarcheCategory(int ageOfMenarche) {
        if (ageOfMenarche >= 14) {
            return 0;
        }
        if (ageOfMenarche == 12 || ageOfMenarche == 13) {
            return 1;
        } else {
            return 2;
        }
    }

    private static int getBiopsiesCategory(int numBiopsies) {
        return Math.min(2, numBiopsies);
    }

    private static int getFirstLiveBirthCategory(int ageOfFirstLiveBirth) {
        if (ageOfFirstLiveBirth < 20) {
            return 0;
        } else if (ageOfFirstLiveBirth <= 24) {
            return 1;
        } else if (ageOfFirstLiveBirth <= 29) {
            return 2;
        } else {
            return 3;
        }
    }

    private static int getNumRelativesCategory(int numRelatives) {
        return Math.min(2, numRelatives);
    }
}
